package com.petshop.petshop_api.service;

import com.petshop.petshop_api.exception.RecursoNaoEncontradoException;
import com.petshop.petshop_api.model.RecuperacaoSenha;
import com.petshop.petshop_api.model.Tutor;
import com.petshop.petshop_api.repository.RecuperacaoSenhaRepository;
import com.petshop.petshop_api.repository.TutorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class RecuperacaoSenhaService {

    private RecuperacaoSenhaRepository passwordResetTokenRepository;
    private final EmailService emailService;
    private final TutorRepository tutorRepository;
    private final PasswordEncoder passwordEncoder;

    public RecuperacaoSenhaService(RecuperacaoSenhaRepository passwordResetTokenRepository,
                                   EmailService emailService,
                                   TutorRepository tutorRepository,
                                   PasswordEncoder passwordEncoder) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
        this.tutorRepository = tutorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private String gerarToken(){

        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(salt);
    }

    private String gerarHash(String token) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Erro ao gerar hash do token", e);
        }
    }

    private void salvarToken(Tutor tutor, String tokenHash) {

       com.petshop.petshop_api.model.RecuperacaoSenha token = passwordResetTokenRepository
               .findByTutorId(tutor.getId())
                       .orElseGet(() -> {
                                   var novoToken = new RecuperacaoSenha();
                                   novoToken.setTutor(tutor);
                                   return novoToken;
                               });

        token.setTokenHash(tokenHash);
        token.setDataExpiracao(LocalDateTime.now().plusMinutes(30));

        passwordResetTokenRepository.save(token);
    }

    public void solicitarRecuperacao(Tutor tutor) {

        String token = gerarToken();

        String tokenHash = gerarHash(token);

        salvarToken(tutor, tokenHash);

        emailService.enviarEmail(
                tutor.getEmail(),
                "Recuperação de senha - AmigoPet",
                "Seu token de recuperação é: " + token
        );
    }

    public void solicitarRecuperacaoPorEmail(String email) {

        System.out.println("EMAIL RECEBIDO: [" + email + "]");

        Tutor tutor = tutorRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tutor não encontrado!"
                ));

        solicitarRecuperacao(tutor);
    }

    public void validarToken(String token) {

        String tokenHash = gerarHash(token);

        RecuperacaoSenha tokenSalvo = passwordResetTokenRepository
                .findByTokenHash(tokenHash)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Token inválido!"
                ));

        if (tokenSalvo.getDataExpiracao().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expirado!");
        }
    }

    public void redefinirSenha(String token, String novaSenha) {

        String tokenHash = gerarHash(token);

        RecuperacaoSenha tokenSalvo = passwordResetTokenRepository
                .findByTokenHash(tokenHash)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Token inválido!"
                ));

        if (tokenSalvo.getDataExpiracao().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expirado!");
        }

        Tutor tutor = tokenSalvo.getTutor();

        tutor.setSenha(passwordEncoder.encode(novaSenha));

        tutorRepository.save(tutor);

        passwordResetTokenRepository.delete(tokenSalvo);
    }

}
