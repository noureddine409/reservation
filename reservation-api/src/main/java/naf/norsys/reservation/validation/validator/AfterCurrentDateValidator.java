package naf.norsys.reservation.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import naf.norsys.reservation.dto.PeriodDto;
import naf.norsys.reservation.validation.AfterCurrentDate;

import java.time.LocalDateTime;


@Slf4j
public class AfterCurrentDateValidator implements ConstraintValidator<AfterCurrentDate, PeriodDto> {

    @Override
    public void initialize(AfterCurrentDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PeriodDto value, ConstraintValidatorContext context) {
        return value.getStartDate().isAfter(LocalDateTime.now());
    }
}
