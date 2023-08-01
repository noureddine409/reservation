package naf.norsys.reservation.validation.validator;

import lombok.extern.slf4j.Slf4j;
import naf.norsys.reservation.dto.PeriodDto;
import naf.norsys.reservation.validation.ValidPeriod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PeriodValidator implements ConstraintValidator<ValidPeriod, PeriodDto> {

    @Override
    public void initialize(ValidPeriod constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PeriodDto value, ConstraintValidatorContext context) {
        return value.getStartDate().isBefore(value.getEndDate());
    }
}
