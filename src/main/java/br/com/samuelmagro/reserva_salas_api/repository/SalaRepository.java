package br.com.samuelmagro.reserva_salas_api.repository;

import br.com.samuelmagro.reserva_salas_api.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long>, JpaSpecificationExecutor<Sala> {
    Boolean existsByNome(String nome);

    Boolean existsByNomeAndIdNot(String nome, Long id);
}
