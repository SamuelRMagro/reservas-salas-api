package br.com.samuelmagro.reserva_salas_api.customValidation.anotacaoCustomizada;

import br.com.samuelmagro.reserva_salas_api.customValidation.validator.DataDentroIntervaloValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataDentroIntervaloValidator.class)
public @interface DataDentroIntervalo {
    String message() default "Data deve estar entre 01/01/2000 e 31/12/2100";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
