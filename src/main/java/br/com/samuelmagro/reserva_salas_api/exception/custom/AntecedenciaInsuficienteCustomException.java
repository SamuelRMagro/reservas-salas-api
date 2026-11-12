package br.com.samuelmagro.reserva_salas_api.exception.custom;

public class AntecedenciaInsuficienteCustomException extends RuntimeException {
    public AntecedenciaInsuficienteCustomException(String message) {
        super(message);
    }
}
