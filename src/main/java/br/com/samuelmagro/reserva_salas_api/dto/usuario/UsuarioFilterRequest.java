package br.com.samuelmagro.reserva_salas_api.dto.usuario;

import br.com.samuelmagro.reserva_salas_api.customValidation.anotacaoCustomizada.DataDentroIntervalo;
import br.com.samuelmagro.reserva_salas_api.customValidation.anotacaoCustomizada.DataPosterior;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@DataPosterior(campoInicio = "criadoDepoisDe", campoFim = "criadoAte")
public record UsuarioFilterRequest(

        @Schema(description = "Nome do usuario.")
        String nome,

        @Schema(description = "Email do usuario.")
        String email,

        @Schema(description = "Status do usuario.")
        Boolean ativo,

        @Schema(description = "Data de cadastro a partir de YYYY-MM-DD.")
        @DataDentroIntervalo
        LocalDate criadoDepoisDe,

        @Schema(description = "Data de cadastro até de YYYY-MM-DD.")
        @DataDentroIntervalo
        LocalDate criadoAte
) {
}
