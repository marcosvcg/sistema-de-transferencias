package com.marcosvcg.handsonhorizon.repository;

import com.marcosvcg.handsonhorizon.model.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {
    @Query("SELECT p FROM Pessoa p WHERE LOWER(p.nome) = LOWER(:nome)")
    Optional<Pessoa> findByNomeIgnoreCase(@Param("nome") String nome);

    @Query("SELECT p FROM Pessoa p WHERE p.cpf = ?1")
    Optional<Pessoa> findByCpf(String cpf);

    Optional<Pessoa> findByEmail(String email);
}
