package com.marcosvcg.handsonhorizon.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "log_auditoria_transferencia")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogAuditoriaTransferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "transferencia_id", nullable = false)
    private UUID transferenciaId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_hora", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime dataHora;

    @Column(name = "conta_origem_id", nullable = false)
    private UUID contaOrigemId;

    @Column(name = "conta_origem_numero", nullable = false, length = 20)
    private String contaOrigemNumero;

    @Column(name = "conta_origem_tipo", nullable = false, length = 10)
    private String contaOrigemTipo;

    @Column(name = "conta_origem_saldo_apos", nullable = false, precision = 19, scale = 2)
    private BigDecimal contaOrigemSaldoApos;

    @Column(name = "pessoa_origem_id", nullable = false)
    private UUID pessoaOrigemId;

    @Column(name = "pessoa_origem_nome", nullable = false, length = 150)
    private String pessoaOrigemNome;

    @Column(name = "pessoa_origem_cpf", nullable = false, length = 11)
    private String pessoaOrigemCpf;

    @Column(name = "conta_destino_id", nullable = false)
    private UUID contaDestinoId;

    @Column(name = "conta_destino_numero", nullable = false, length = 20)
    private String contaDestinoNumero;

    @Column(name = "conta_destino_tipo", nullable = false, length = 10)
    private String contaDestinoTipo;

    @Column(name = "conta_destino_saldo_apos", nullable = false, precision = 19, scale = 2)
    private BigDecimal contaDestinoSaldoApos;

    @Column(name = "pessoa_destino_id", nullable = false)
    private UUID pessoaDestinoId;

    @Column(name = "pessoa_destino_nome", nullable = false, length = 150)
    private String pessoaDestinoNome;

    @Column(name = "pessoa_destino_cpf", nullable = false, length = 11)
    private String pessoaDestinoCpf;

    @Column(name = "registrado_em", nullable = false, insertable = false, updatable = false,
            columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private OffsetDateTime registradoEm;
}
