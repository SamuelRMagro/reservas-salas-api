package br.com.samuelmagro.reserva_salas_api.exception.custom;

public class AtualizacaoInvalidaCustomException extends RuntimeException {
    public AtualizacaoInvalidaCustomException(String message) {
        super(message);
    }
}
