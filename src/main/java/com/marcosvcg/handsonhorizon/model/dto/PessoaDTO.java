package com.marcosvcg.handsonhorizon.model.dto;

import com.marcosvcg.handsonhorizon.model.entities.Pessoa;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PessoaDTO(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY,
                description = "ID gerado automaticamente")
        UUID id,

        @Schema(example = "Pedro")
        String nome,

        @Schema(example = "71987654321")
        String telefone,

        @Schema(example = "01234567890")
        String cpf,

        @Schema(example = "pedro@email.com",
                description = "E-mail usado no login")
        String email
) {
    public static PessoaDTO toDTO(Pessoa pessoa) {
        return PessoaDTO.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome().trim())
                .telefone(pessoa.getTelefone())
                .cpf(pessoa.getCpf())
                .email(pessoa.getEmail())
                .build();
    }

    public static Pessoa toEntity(PessoaDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(dto.id());
        pessoa.setNome(dto.nome() != null ? dto.nome().trim() : null);
        pessoa.setTelefone(dto.telefone());
        pessoa.setCpf(dto.cpf());
        pessoa.setEmail(dto.email());
        return pessoa;
    }
}
