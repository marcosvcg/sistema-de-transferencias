package com.marcosvcg.handsonhorizon.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequestDTO(
        @Schema(example = "carlos@email.com", description = "E-mail cadastrado")
        String email,

        @Schema(example = "senha", description = "Senha do usuário")
        String senha
) {}
