package br.com.samuelmagro.reserva_salas_api.dto.sala;

import br.com.samuelmagro.reserva_salas_api.customValidation.anotacaoCustomizada.DataDentroIntervalo;
import br.com.samuelmagro.reserva_salas_api.customValidation.anotacaoCustomizada.DataPosterior;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@DataPosterior(campoInicio = "criadaDepoisDe", campoFim = "criadaAte")
public record SalaFilterRequest(

        @Schema(description = "Nome da sala.")
        String nome,

        @Schema(description = "Capacidade máxima da sala.")
        Integer capacidade,

        @Schema(description = "Localização da sala.")
        String localizacao,

        @Schema(description = "Status da sala.")
        Boolean ativa,

        @Schema(description = "Data de cadastro a partir de YYYY-MM-DD.")
        @DataDentroIntervalo
        LocalDate criadaDepoisDe,

        @Schema(description = "Data de cadastro até de YYYY-MM-DD.")
        @DataDentroIntervalo
        LocalDate criadaAte
) {
}
