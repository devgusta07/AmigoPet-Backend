package com.petshop.petshop_api.controller;

import com.petshop.petshop_api.dto.request.tutor.RedefinirSenhaDto;
import com.petshop.petshop_api.dto.request.tutor.SolicitarRecuperacaoDTO;
import com.petshop.petshop_api.repository.RecuperacaoSenhaRepository;
import com.petshop.petshop_api.service.RecuperacaoSenhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recuperacao-senha")
public class RecuperacaoSenhaController {

    private RecuperacaoSenhaService recuperacaoSenhaService;

    public RecuperacaoSenhaController(RecuperacaoSenhaService recuperacaoSenhaService) {
        this.recuperacaoSenhaService = recuperacaoSenhaService;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<Void> solicitarRecuperacao(
            @RequestBody SolicitarRecuperacaoDTO dto) {

        recuperacaoSenhaService.solicitarRecuperacaoPorEmail(dto.email());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/redefinir")
    public ResponseEntity<Void> redefinirSenha(
            @RequestBody RedefinirSenhaDto dto) {

        recuperacaoSenhaService.redefinirSenha(
                dto.token(),
                dto.senha()
        );

        return ResponseEntity.noContent().build();
    }
}
