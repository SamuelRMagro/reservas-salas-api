package br.com.samuelmagro.reserva_salas_api.exception.custom;

public class RegistroNaoEncontradoCustomException extends RuntimeException {
    public RegistroNaoEncontradoCustomException(String message) {
        super(message);
    }
}
