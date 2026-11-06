package br.com.samuelmagro.reserva_salas_api.customValidation.validator;

import br.com.samuelmagro.reserva_salas_api.customValidation.anotacaoCustomizada.DataDentroIntervalo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DataDentroIntervaloValidator implements ConstraintValidator<DataDentroIntervalo, LocalDate> {

    private static final LocalDate MIN = LocalDate.of(2000, 1, 1);
    private static final LocalDate MAX = LocalDate.of(2100, 12, 31);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return !value.isBefore(MIN) && !value.isAfter(MAX);
    }
}
