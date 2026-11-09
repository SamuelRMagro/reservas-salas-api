package br.com.samuelmagro.reserva_salas_api.dto.sala;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SalaUpdateDTO(

        @Schema(description = "Nome da sala")
        @NotBlank(message = "O campo nome é obrigatório.")
        @Size(min = 5, max = 100, message = "O campo nome deve ter entre {min} e {max} caracteres.")
        String nome,

        @Schema(description = "Capacidade máxima da sala")
        @NotNull(message = "O campo capacidade é obrigatório.")
        @Min(value = 1, message = "O campo capacidade deve ser maior que zero.")
        Integer capacidade,

        @Schema(description = "Localização da sala")
        @Size(min = 5, max = 150, message = "O campo localização deve ter entre {min} e {max} caracteres.")
        String localizacao
) {
}
