package br.com.samuelmagro.reserva_salas_api.exception;

import br.com.samuelmagro.reserva_salas_api.dto.exception.ErroCampo;
import br.com.samuelmagro.reserva_salas_api.dto.exception.ErroDTO;
import br.com.samuelmagro.reserva_salas_api.exception.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ErroCampo> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ErroDTO err = new ErroDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação.",
                "Existem campos inválidos.",
                System.currentTimeMillis(),
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(RegistroNaoEncontradoCustomException.class)
    public ResponseEntity<ErroDTO> registroNaoEncontradoCustomException(RegistroNaoEncontradoCustomException ex) {
        ErroDTO erro = new ErroDTO(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado.",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(RegistroDuplicadoCustomException.class)
    public ResponseEntity<ErroDTO> registroDuplicadoCustomException(RegistroDuplicadoCustomException ex) {
        ErroDTO erro = new ErroDTO(
                HttpStatus.CONFLICT.value(),
                "Há campos já cadastrados na requisição.",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(ConflitoHorarioCustomException.class)
    public ResponseEntity<ErroDTO> conflitoHorarioCustomException(ConflitoHorarioCustomException ex) {
        ErroDTO erro = new ErroDTO(
                HttpStatus.CONFLICT.value(),
                "Conflito de reserva.",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(ForaHorarioPermitidoCustomException.class)
    public ResponseEntity<ErroDTO> foraHorarioPermitidoCustomException(ForaHorarioPermitidoCustomException ex) {
        ErroDTO erro = new ErroDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Fora do horário permitido.",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(PermanenciaMinimaCustomExcepion.class)
    public ResponseEntity<ErroDTO> permanenciaMinimaCustomExcepion(PermanenciaMinimaCustomExcepion ex) {
        ErroDTO erro = new ErroDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Permanência mínima insuficiente.",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(AntecedenciaInsuficienteCustomException.class)
    public ResponseEntity<ErroDTO> antecedenciaInsuficienteCustomException(AntecedenciaInsuficienteCustomException ex) {
        ErroDTO erro = new ErroDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Antecedência insuficiente.",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(CapacidadeExcedidaCustomException.class)
    public ResponseEntity<ErroDTO> capacidadeExcedidaCustomException(CapacidadeExcedidaCustomException ex) {
        ErroDTO erro = new ErroDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Capacidade excedida.",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(RegistroInativoCustomException.class)
    public ResponseEntity<ErroDTO> registroInativoCustomException(RegistroInativoCustomException ex) {
        ErroDTO erro = new ErroDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Registro inativo.",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(AtualizacaoInvalidaCustomException.class)
    public ResponseEntity<ErroDTO> atualizacaoInvalidaCustomException(AtualizacaoInvalidaCustomException ex) {
        ErroDTO erro = new ErroDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Atualização inválida.",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}