package naf.norsys.reservation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import naf.norsys.reservation.validation.validator.AfterCurrentDateValidator;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = AfterCurrentDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterCurrentDate {
    String message() default "Date must be after the current date.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
