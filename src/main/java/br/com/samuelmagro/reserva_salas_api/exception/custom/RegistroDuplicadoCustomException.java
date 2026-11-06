package br.com.samuelmagro.reserva_salas_api.exception.custom;

public class RegistroDuplicadoCustomException extends RuntimeException {
    public RegistroDuplicadoCustomException(String message) {
        super(message);
    }
}
