package validator;

import java.math.BigDecimal;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrijsConstraintValidator implements ConstraintValidator<PrijsValidator, BigDecimal> {

    private static final BigDecimal LOWER_BOUND = new BigDecimal("0.00");
    private static final BigDecimal UPPER_BOUND = new BigDecimal("100.00");

    @Override
    public void initialize(PrijsValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
	context.unwrap(HibernateConstraintValidatorContext.class).addMessageParameter("min", LOWER_BOUND);
	context.unwrap(HibernateConstraintValidatorContext.class).addMessageParameter("max", UPPER_BOUND);

	if (value == null) {
	    return true;
	}
	return value.compareTo(LOWER_BOUND) > 0 && value.compareTo(UPPER_BOUND) < 0;
    }
}
