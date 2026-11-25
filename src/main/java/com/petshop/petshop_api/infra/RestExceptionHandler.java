package com.petshop.petshop_api.infra;

import com.petshop.petshop_api.exception.CredencialInvalidaException;
import com.petshop.petshop_api.exception.RecursoEmUsoException;
import com.petshop.petshop_api.exception.RecursoJaExistenteException;
import com.petshop.petshop_api.exception.RecursoNaoEncontradoException;
import com.petshop.petshop_api.exception.SenhasNaoConferemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<RestError> recursoNaoEncontrado(RecursoNaoEncontradoException e) {

        var restError = new RestError(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restError);
    }

    @ExceptionHandler(RecursoJaExistenteException.class)
    public ResponseEntity<RestError> recursoJaExistente(RecursoJaExistenteException e) {

        var restError = new RestError(HttpStatus.CONFLICT, e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(restError);
    }

    @ExceptionHandler(RecursoEmUsoException.class)
    public ResponseEntity<RestError> recursoEmUso (RecursoEmUsoException e) {

        var restError = new RestError(HttpStatus.CONFLICT, e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(restError);
    }

    @ExceptionHandler(CredencialInvalidaException.class)
    public ResponseEntity<RestError> credencialInvalida(CredencialInvalidaException e) {

        var restError = new RestError(HttpStatus.UNAUTHORIZED, e.getMessage());

        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restError);
    }

    @ExceptionHandler(SenhasNaoConferemException.class)
    public ResponseEntity<RestError> senhasNaoConferem(SenhasNaoConferemException e) {

        var restError = new RestError(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.badRequest().body(restError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestError> argumentoInvalido(MethodArgumentTypeMismatchException e) {

        var restError = new RestError(
                HttpStatus.BAD_REQUEST,
                "Parâmetro inválido: " + e.getName()
        );

        return ResponseEntity.badRequest().body(restError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestError> corpoInvalido(HttpMessageNotReadableException e) {

        var restError = new RestError(
                HttpStatus.BAD_REQUEST,
                "Corpo da requisição inválido. Verifique os campos e formatos enviados."
        );

        return ResponseEntity.badRequest().body(restError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestError> argumentoNaoValidado(MethodArgumentNotValidException e) {

        var restError = new RestError(
                HttpStatus.BAD_REQUEST,
                "Um ou mais campos possuem valores inválidos."
        );

        return ResponseEntity.badRequest().body(restError);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<RestError> parametroAusente(MissingServletRequestParameterException e) {

        var restError = new RestError(
                HttpStatus.BAD_REQUEST,
                "Parâmetro obrigatório ausente: " + e.getParameterName()
        );

        return ResponseEntity.badRequest().body(restError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestError> erroInterno(Exception e) {

        var restError = new RestError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno no servidor."
        );

        return ResponseEntity.internalServerError().body(restError);
   }

}
