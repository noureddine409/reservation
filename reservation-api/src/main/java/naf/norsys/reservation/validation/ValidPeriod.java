package naf.norsys.reservation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import naf.norsys.reservation.validation.validator.PeriodValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PeriodValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeriod {
    String message() default "Start date must be before end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
