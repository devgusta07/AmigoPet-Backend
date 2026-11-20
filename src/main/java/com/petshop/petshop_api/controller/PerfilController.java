package com.petshop.petshop_api.controller;

import com.petshop.petshop_api.dto.request.tutor.TutorPerfilRequest;
import com.petshop.petshop_api.dto.request.tutor.AlterarSenhaRequest;
import com.petshop.petshop_api.dto.response.tutor.TutorResponseDTO;
import com.petshop.petshop_api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meuPerfil")
public class PerfilController {

    private final TutorService tutorService;

    public PerfilController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @GetMapping("/meuPerfil")
    private ResponseEntity<TutorResponseDTO> meuPerfil(@AuthenticationPrincipal Jwt jwt) {

        Integer idTutor = Integer.parseInt(jwt.getSubject());

        return ResponseEntity.ok(tutorService.meuPerfil(idTutor));

    }

    @PutMapping("/meuPerfil")
    private ResponseEntity<TutorResponseDTO> updatePerfil(@Valid @RequestBody TutorPerfilRequest request,
                                                          @AuthenticationPrincipal Jwt jwt) {

        Integer idTutor = Integer.parseInt(jwt.getSubject());

        return ResponseEntity.ok(tutorService.updatePerfil(idTutor, request));
    }

    @PutMapping("/alterarSenha")
    private ResponseEntity<Void> alterarSenha(@Valid @RequestBody AlterarSenhaRequest request,
                                              @AuthenticationPrincipal Jwt jwt) {

        Integer idTutor = Integer.parseInt(jwt.getSubject());

        tutorService.alterarSenha(idTutor, request);

        return ResponseEntity.noContent().build();
    }
}
