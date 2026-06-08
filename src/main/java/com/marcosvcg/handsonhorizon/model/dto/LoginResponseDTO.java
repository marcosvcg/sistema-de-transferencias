package com.marcosvcg.handsonhorizon.model.dto;

import com.marcosvcg.handsonhorizon.model.entities.Pessoa;

import java.util.UUID;

/**
 * Resposta do login bem-sucedido.
 * Nunca inclui a senha ou o hash BCrypt.
 */
public record LoginResponseDTO(
        UUID id,
        String nome,
        String email,
        String role,
        String mensagem
) {
    public static LoginResponseDTO from(Pessoa pessoa) {
        return new LoginResponseDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getRole(),
                "Login realizado com sucesso!"
        );
    }
}
