package br.com.samuelmagro.reserva_salas_api.dto.reserva;

import br.com.samuelmagro.reserva_salas_api.dto.sala.SalaReservaResponseDTO;
import br.com.samuelmagro.reserva_salas_api.dto.usuario.UsuarioReservaResponseDTO;
import br.com.samuelmagro.reserva_salas_api.model.StatusReserva;

import java.time.Instant;
import java.time.LocalDateTime;

public record ReservaResponseDTO(
        Long id,
        UsuarioReservaResponseDTO usuario,
        SalaReservaResponseDTO sala,
        LocalDateTime inicio,
        LocalDateTime fim,
        Integer quantidadeParticipantes,
        StatusReserva status,
        Instant criadaEm,
        Instant atualizadaEm,
        Instant canceladaEm
) {
}
