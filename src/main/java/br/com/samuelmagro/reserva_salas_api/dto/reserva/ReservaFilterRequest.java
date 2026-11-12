package br.com.samuelmagro.reserva_salas_api.dto.reserva;

import br.com.samuelmagro.reserva_salas_api.customValidation.anotacaoCustomizada.DataDentroIntervalo;
import br.com.samuelmagro.reserva_salas_api.customValidation.anotacaoCustomizada.DataPosterior;
import br.com.samuelmagro.reserva_salas_api.model.StatusReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DataPosterior(campoInicio = "criadaDepoisDe", campoFim = "criadaAte")
public record ReservaFilterRequest(

        @Schema(description = "Id do usuário.")
        String usuario,

        @Schema(description = "Id da sala.")
        String sala,

        @Schema(description = "Data e hora de início da reserva a partir de - YYYY-MM-DD HH:mm.")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime inicio,

        @Schema(description = "Data e hora de fim da reserva até - YYYY-MM-DD HH:mm.")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime fim,

        @Schema(description = "Status da reserva.")
        StatusReserva status,

        @Schema(description = "Data de cadastro a partir de YYYY-MM-DD.")
        @DataDentroIntervalo
        LocalDate criadaDepoisDe,

        @Schema(description = "Data de cadastro até de YYYY-MM-DD.")
        @DataDentroIntervalo
        LocalDate criadaAte
) {
}
