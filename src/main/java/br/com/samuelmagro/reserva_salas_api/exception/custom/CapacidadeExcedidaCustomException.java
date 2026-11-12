package br.com.samuelmagro.reserva_salas_api.exception.custom;

public class CapacidadeExcedidaCustomException extends RuntimeException {
    public CapacidadeExcedidaCustomException(String message) {
        super(message);
    }
}
