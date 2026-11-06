package br.com.samuelmagro.reserva_salas_api.dto.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErroDTO {

    int status;
    String error;
    String message;
    Long timestamp;
    List<ErroCampo> erros = new ArrayList<>();

    public ErroDTO(int status, String error, String message, Long timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErroDTO(int status, String error, String message, Long timestamp, List<ErroCampo> erros) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.erros = erros;
    }
}
