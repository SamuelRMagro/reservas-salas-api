package br.com.samuelmagro.reserva_salas_api.repository;

import br.com.samuelmagro.reserva_salas_api.model.Reserva;
import br.com.samuelmagro.reserva_salas_api.model.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>, JpaSpecificationExecutor<Reserva> {

    @Query(value = """
        SELECT COUNT(r) > 0
        FROM tb_reservas r
        WHERE r.sala_id = :salaId
        AND r.status = 'ATIVA'
        AND r.inicio < :fim
        AND r.fim > :inicio
        AND (:reservaId IS NULL OR r.id <> :reservaId)
    """, nativeQuery = true)
    Boolean conflitoDeHorario(@Param("salaId") Long salaId, @Param("inicio") LocalDateTime inicio,
                              @Param("fim") LocalDateTime fim, @Param("reservaId") Long reservaId);

    Optional<Reserva> findByIdAndStatusEquals(Long id, StatusReserva status);
}
