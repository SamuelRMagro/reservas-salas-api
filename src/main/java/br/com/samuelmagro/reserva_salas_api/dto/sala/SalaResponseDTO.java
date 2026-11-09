package br.com.samuelmagro.reserva_salas_api.dto.sala;

import java.time.Instant;

public record SalaResponseDTO(
        Long id,
        String nome,
        Integer capacidade,
        String localizacao,
        Boolean ativa,
        Instant criadaEm,
        Instant atualizadaEm
) {
}
