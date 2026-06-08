package com.marcosvcg.handsonhorizon.services;

import com.marcosvcg.handsonhorizon.model.dto.ContaDTO;
import com.marcosvcg.handsonhorizon.model.dto.PessoaDTO;
import com.marcosvcg.handsonhorizon.model.dto.TransferenciaDTO;
import com.marcosvcg.handsonhorizon.model.entities.LogAuditoriaTransferencia;
import com.marcosvcg.handsonhorizon.repository.LogAuditoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class AuditoriaTransferenciaService {

    private static final Logger log = LoggerFactory.getLogger(AuditoriaTransferenciaService.class);

    private final LogAuditoriaRepository logAuditoriaRepository;

    @Autowired
    public AuditoriaTransferenciaService(LogAuditoriaRepository logAuditoriaRepository) {
        this.logAuditoriaRepository = logAuditoriaRepository;
    }

    public void registrar(
            TransferenciaDTO transferencia,
            PessoaDTO pessoaOrigem,
            ContaDTO contaOrigemApos,
            PessoaDTO pessoaDestino,
            ContaDTO contaDestinoApos
    ) {
        OffsetDateTime dataHora = transferencia.data() != null
                ? transferencia.data().atOffset(ZoneOffset.UTC)
                : OffsetDateTime.now(ZoneOffset.UTC);

        LogAuditoriaTransferencia registro = LogAuditoriaTransferencia.builder()
                .transferenciaId(transferencia.id())
                .valor(transferencia.valor())
                .dataHora(dataHora)
                .contaOrigemId(contaOrigemApos.id())
                .contaOrigemNumero(contaOrigemApos.numero())
                .contaOrigemTipo(contaOrigemApos.tipoConta().name())
                .contaOrigemSaldoApos(contaOrigemApos.saldo())
                .pessoaOrigemId(pessoaOrigem.id())
                .pessoaOrigemNome(pessoaOrigem.nome())
                .pessoaOrigemCpf(pessoaOrigem.cpf())
                .contaDestinoId(contaDestinoApos.id())
                .contaDestinoNumero(contaDestinoApos.numero())
                .contaDestinoTipo(contaDestinoApos.tipoConta().name())
                .contaDestinoSaldoApos(contaDestinoApos.saldo())
                .pessoaDestinoId(pessoaDestino.id())
                .pessoaDestinoNome(pessoaDestino.nome())
                .pessoaDestinoCpf(pessoaDestino.cpf())
                .build();

        logAuditoriaRepository.save(registro);

        emitirLog(transferencia, pessoaOrigem, contaOrigemApos, pessoaDestino, contaDestinoApos);
    }

    private void emitirLog(
            TransferenciaDTO transferencia,
            PessoaDTO pessoaOrigem,
            ContaDTO contaOrigemApos,
            PessoaDTO pessoaDestino,
            ContaDTO contaDestinoApos
    ) {
        String json = """
                {
                  "evento": "TRANSFERENCIA_REALIZADA",
                  "transacao_id": "%s",
                  "valor": %s,
                  "data_hora": "%s",
                  "origem": {
                    "pessoa_nome": "%s",
                    "pessoa_cpf": "%s",
                    "conta_numero": "%s",
                    "conta_tipo": "%s",
                    "saldo_apos": %s
                  },
                  "destino": {
                    "pessoa_nome": "%s",
                    "pessoa_cpf": "%s",
                    "conta_numero": "%s",
                    "conta_tipo": "%s",
                    "saldo_apos": %s
                  }
                }""".formatted(
                transferencia.id(),
                transferencia.valor().toPlainString(),
                transferencia.data(),
                pessoaOrigem.nome(), mascaraCpf(pessoaOrigem.cpf()),
                contaOrigemApos.numero(), contaOrigemApos.tipoConta(),
                contaOrigemApos.saldo().toPlainString(),
                pessoaDestino.nome(), mascaraCpf(pessoaDestino.cpf()),
                contaDestinoApos.numero(), contaDestinoApos.tipoConta(),
                contaDestinoApos.saldo().toPlainString()
        );

        log.info(json);
    }

    private String mascaraCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) return "***";
        return cpf.substring(0, 3) + ".***.**" + cpf.substring(9);
    }
}
