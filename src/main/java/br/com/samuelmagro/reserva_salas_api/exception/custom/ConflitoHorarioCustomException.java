package br.com.samuelmagro.reserva_salas_api.exception.custom;

public class ConflitoHorarioCustomException extends RuntimeException {
    public ConflitoHorarioCustomException(String message) {
        super(message);
    }
}
