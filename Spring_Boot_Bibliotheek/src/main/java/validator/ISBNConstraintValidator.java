package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNConstraintValidator implements ConstraintValidator<ISBNValidator, String> {

    @Override
    public void initialize(ISBNValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
	if (isbn == null) {
	    context.buildConstraintViolationWithTemplate("{validation.boekISBN.NotNull.message}")
		    .addConstraintViolation().disableDefaultConstraintViolation();

	    return false;
	}

	String cleanIsbn = isbn.replaceAll("[\\s\\p{P}\\p{L}]", "");

	if (cleanIsbn.length() != 13) {
	    context.buildConstraintViolationWithTemplate("{validation.boekISBN.FormaatNietOk.message}")
		    .addConstraintViolation().disableDefaultConstraintViolation();

	    return false;
	}

	int sum = 0;
	for (int i = 0; i < 12; i++) {
	    int digit = Character.getNumericValue(cleanIsbn.charAt(i));
	    sum += (i % 2 == 0) ? digit : digit * 3;
	}
	int checksum = 10 - (sum % 10);
	if (checksum == 10) {
	    checksum = 0;
	}

	int lastDigit = Character.getNumericValue(cleanIsbn.charAt(12));
	if (lastDigit != checksum) {
	    context.buildConstraintViolationWithTemplate("{validation.boekISBN.ChecksumNietOk.message}")
		    .addConstraintViolation().disableDefaultConstraintViolation();

	    return false;
	}
	return true;
    }
}
