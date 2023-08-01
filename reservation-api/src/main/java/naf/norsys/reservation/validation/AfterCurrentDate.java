package naf.norsys.reservation.validation;

import naf.norsys.reservation.validation.validator.AfterCurrentDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
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
