package com.marcosvcg.handsonhorizon.repository;

import com.marcosvcg.handsonhorizon.model.entities.LogAuditoriaTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoriaTransferencia, UUID> {

    List<LogAuditoriaTransferencia> findByContaOrigemIdOrderByDataHoraDesc(UUID contaOrigemId);

    List<LogAuditoriaTransferencia> findByContaDestinoIdOrderByDataHoraDesc(UUID contaDestinoId);

    List<LogAuditoriaTransferencia> findByPessoaOrigemIdOrderByDataHoraDesc(UUID pessoaOrigemId);
}
