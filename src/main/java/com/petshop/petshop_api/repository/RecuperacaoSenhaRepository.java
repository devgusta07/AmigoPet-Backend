package com.petshop.petshop_api.repository;

import com.petshop.petshop_api.model.RecuperacaoSenha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecuperacaoSenhaRepository extends JpaRepository<RecuperacaoSenha, Integer> {

    Optional<RecuperacaoSenha> findByTokenHash(String token);
}
