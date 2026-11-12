package br.com.samuelmagro.reserva_salas_api.exception.custom;

public class RegistroInativoCustomException extends RuntimeException {
    public RegistroInativoCustomException(String message) {
        super(message);
    }
}
