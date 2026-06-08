package com.marcosvcg.handsonhorizon.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcosvcg.handsonhorizon.model.entities.Conta;
import com.marcosvcg.handsonhorizon.model.entities.Transferencia;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TransferenciaDTO (
        @Schema(accessMode = Schema.AccessMode.READ_ONLY,
                description = "ID único da transferência (gerado automaticamente)")
        UUID id,

        @Schema(description = "ID da conta de origem (débito)",
                example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        UUID contaOrigemId,

        @Schema(description = "ID da conta de destino (crédito)",
                example = "f0e9d8c7-b6a5-4321-fedc-ba0987654321")
        UUID contaDestinoId,

        @Schema(description = "Valor a ser transferido em reais",
                example = "150.00")
        BigDecimal valor,

        @Schema(type = "string",
                description = "Data e hora da transferência",
                example = "15/09/2024 20:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime data
) {
    public static TransferenciaDTO toDTO(Transferencia transferencia) {
        return TransferenciaDTO.builder()
                .id(transferencia.getId())
                .contaOrigemId(transferencia.getContaOrigem().getId())
                .contaDestinoId(transferencia.getContaDestino().getId())
                .valor(transferencia.getValor())
                .data(transferencia.getData())
                .build();
    }

    public static Transferencia toEntity(TransferenciaDTO dto, Conta contaOrigem, Conta contaDestino) {
        return new Transferencia(
                dto.id(),
                contaOrigem,
                contaDestino,
                dto.valor(),
                dto.data()
        );
    }
}
