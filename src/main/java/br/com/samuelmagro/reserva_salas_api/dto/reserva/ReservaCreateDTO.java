package br.com.samuelmagro.reserva_salas_api.dto.reserva;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservaCreateDTO(

        @Schema(description = "Id da sala.")
        @NotNull(message = "O campo salaId é obrigatório.")
        @Min(value = 1, message = "O id da sala deve ser maior que zero.")
        Long salaId,

        @Schema(description = "Id do usuário.")
        @NotNull(message = "O campo usuarioId é obrigatório.")
        @Min(value = 1, message = "O id do usuário deve ser maior que zero.")
        Long usuarioId,

        @Schema(description = "Data e hora de início da reserva - YYYY-MM-DD HH:mm.")
        @FutureOrPresent(message = "O campo inicio deve ser uma data futura ou presente.")
        @NotNull(message = "O campo inicio é obrigatório.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime inicio,

        @Schema(description = "Data e hora de fim da reserva - YYYY-MM-DD HH:mm.")
        @FutureOrPresent(message = "O campo fim deve ser uma data futura ou presente.")
        @NotNull(message = "O campo fim é obrigatório.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime fim,

        @Schema(description = "Quantidade de participantes da reserva.")
        @NotNull(message = "O campo quantidadeParticipantes é obrigatório.")
        @Min(value = 1, message = "A quantidade de participantes deve ser maior que zero.")
        Integer quantidadeParticipantes
) {
}
