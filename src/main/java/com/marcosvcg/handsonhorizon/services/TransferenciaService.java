package com.marcosvcg.handsonhorizon.services;

import com.marcosvcg.handsonhorizon.model.dto.ContaDTO;
import com.marcosvcg.handsonhorizon.model.dto.PessoaDTO;
import com.marcosvcg.handsonhorizon.model.dto.TransferenciaDTO;
import com.marcosvcg.handsonhorizon.model.entities.Transferencia;
import com.marcosvcg.handsonhorizon.repository.TransferenciaRepository;
import com.marcosvcg.handsonhorizon.validator.ValidateTransferenciaDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;
    private final ContaService contaService;
    private final PessoaService pessoaService;
    private final AuditoriaTransferenciaService auditoriaService;
    private final ValidateTransferenciaDTO validator = new ValidateTransferenciaDTO();

    @Autowired
    public TransferenciaService(
            TransferenciaRepository transferenciaRepository,
            ContaService contaService,
            PessoaService pessoaService,
            AuditoriaTransferenciaService auditoriaService
    ) {
        this.transferenciaRepository = transferenciaRepository;
        this.contaService = contaService;
        this.pessoaService = pessoaService;
        this.auditoriaService = auditoriaService;
    }

    public List<TransferenciaDTO> getTransferencias() {
        return transferenciaRepository.findAll().stream()
                .map(TransferenciaDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackOn = Exception.class)
    public void transferir(TransferenciaDTO transferenciaDto) {

        // ── 1. Busca os dados das contas e valida a operação ──────────────────
        ContaDTO contaOrigemDto  = contaService.getContaById(transferenciaDto.contaOrigemId());
        ContaDTO contaDestinoDto = contaService.getContaById(transferenciaDto.contaDestinoId());

        validator.validateTransferenciaDTO(transferenciaDto, contaOrigemDto, contaDestinoDto);

        // ── 2. Calcula os novos saldos ─────────────────────────────────────────
        ContaDTO contaOrigemAtualizada  = contaService.subtrairSaldo(contaOrigemDto,  transferenciaDto.valor());
        ContaDTO contaDestinoAtualizada = contaService.adicionarSaldo(contaDestinoDto, transferenciaDto.valor());

        // ── 3. Persiste os saldos atualizados ─────────────────────────────────
        contaService.updateConta(contaOrigemAtualizada);
        contaService.updateConta(contaDestinoAtualizada);

        // ── 4. Persiste a transferência e obtém o ID gerado ───────────────────
        PessoaDTO pessoaOrigem  = pessoaService.getPessoaByID(contaOrigemDto.pessoaId());
        PessoaDTO pessoaDestino = pessoaService.getPessoaByID(contaDestinoDto.pessoaId());

        Transferencia transferencia = TransferenciaDTO.toEntity(
                transferenciaDto,
                ContaDTO.toEntity(contaOrigemDto,  pessoaOrigem),
                ContaDTO.toEntity(contaDestinoDto, pessoaDestino)
        );
        Transferencia transferenciaSalva = transferenciaRepository.save(transferencia);

        // ── 5. Registra a auditoria (banco + arquivo de log) ──────────────────
        // Usa os snapshots pós-atualização para refletir o estado final das contas
        auditoriaService.registrar(
                TransferenciaDTO.toDTO(transferenciaSalva),
                pessoaOrigem,
                contaOrigemAtualizada,
                pessoaDestino,
                contaDestinoAtualizada
        );
    }
}
