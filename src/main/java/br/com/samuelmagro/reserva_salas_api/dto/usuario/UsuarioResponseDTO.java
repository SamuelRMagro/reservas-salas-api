package br.com.samuelmagro.reserva_salas_api.dto.usuario;

import java.time.Instant;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Boolean ativo,
        Instant criadoEm,
        Instant atualizadoEm
) {
}
