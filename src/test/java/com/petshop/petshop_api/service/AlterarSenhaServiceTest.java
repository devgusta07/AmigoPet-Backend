package com.petshop.petshop_api.service;

import com.petshop.petshop_api.dto.request.tutor.AlterarSenhaRequest;
import com.petshop.petshop_api.exception.CredencialInvalidaException;
import com.petshop.petshop_api.exception.SenhasNaoConferemException;
import com.petshop.petshop_api.model.Tutor;
import com.petshop.petshop_api.repository.AgendamentoRepository;
import com.petshop.petshop_api.repository.AnimalRepository;
import com.petshop.petshop_api.repository.TutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlterarSenhaServiceTest {

    @InjectMocks
    private TutorService tutorService;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Tutor tutor;

    @BeforeEach
    void setUp() {
        tutor = new Tutor();
        tutor.setId(1);
        tutor.setSenha("senha-atual-criptografada");
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        AlterarSenhaRequest request =
                new AlterarSenhaRequest("senha-atual", "nova-senha", "nova-senha");

        when(tutorRepository.findById(tutor.getId())).thenReturn(Optional.of(tutor));
        when(passwordEncoder.matches(request.senhaAtual(), tutor.getSenha())).thenReturn(true);
        when(passwordEncoder.encode(request.novaSenha())).thenReturn("nova-senha-criptografada");

        tutorService.alterarSenha(tutor.getId(), request);

        assertEquals("nova-senha-criptografada", tutor.getSenha());
        verify(tutorRepository).save(tutor);
    }

    @Test
    void deveRecusarSenhaAtualIncorreta() {
        AlterarSenhaRequest request =
                new AlterarSenhaRequest("senha-incorreta", "nova-senha", "nova-senha");

        when(tutorRepository.findById(tutor.getId())).thenReturn(Optional.of(tutor));
        when(passwordEncoder.matches(request.senhaAtual(), tutor.getSenha())).thenReturn(false);

        CredencialInvalidaException exception = assertThrows(
                CredencialInvalidaException.class,
                () -> tutorService.alterarSenha(tutor.getId(), request)
        );

        assertEquals("Senha atual incorreta!", exception.getMessage());
        verify(tutorRepository, never()).save(any(Tutor.class));
    }

    @Test
    void deveRecusarConfirmacaoDeSenhaDiferente() {
        AlterarSenhaRequest request =
                new AlterarSenhaRequest("senha-atual", "nova-senha", "outra-senha");

        when(tutorRepository.findById(tutor.getId())).thenReturn(Optional.of(tutor));
        when(passwordEncoder.matches(request.senhaAtual(), tutor.getSenha())).thenReturn(true);

        SenhasNaoConferemException exception = assertThrows(
                SenhasNaoConferemException.class,
                () -> tutorService.alterarSenha(tutor.getId(), request)
        );

        assertEquals("A nova senha e sua confirmação devem ser iguais!", exception.getMessage());
        verify(passwordEncoder, never()).encode(any(String.class));
        verify(tutorRepository, never()).save(any(Tutor.class));
    }
}
