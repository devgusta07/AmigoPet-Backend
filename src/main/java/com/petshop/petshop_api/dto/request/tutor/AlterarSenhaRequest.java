package com.petshop.petshop_api.dto.request.tutor;

import jakarta.validation.constraints.NotBlank;

public record AlterarSenhaRequest(
        @NotBlank(message = "Informe a senha atual")
        String senhaAtual,
        @NotBlank(message = "Informe a nova senha")
        String novaSenha,
        @NotBlank(message = "Confirme a nova senha")
        String confirmacaoNovaSenha
) {
}
