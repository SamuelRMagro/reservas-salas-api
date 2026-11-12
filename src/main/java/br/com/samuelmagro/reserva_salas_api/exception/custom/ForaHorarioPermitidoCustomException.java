package br.com.samuelmagro.reserva_salas_api.exception.custom;

public class ForaHorarioPermitidoCustomException extends RuntimeException {
    public ForaHorarioPermitidoCustomException(String message) {
        super(message);
    }
}
