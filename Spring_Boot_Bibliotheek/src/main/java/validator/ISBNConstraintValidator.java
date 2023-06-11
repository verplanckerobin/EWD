package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNConstraintValidator implements ConstraintValidator<ISBNValidator, String> {

    @Override
    public void initialize(ISBNValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
	// Check op null
	if (isbn == null) {
	    context.buildConstraintViolationWithTemplate("{validation.boekISBN.NotNull.message}")
		    .addConstraintViolation().disableDefaultConstraintViolation();

	    return false;
	}

	// Formateer de ISBN
	String cleanIsbn = isbn.replaceAll("[\\s\\p{P}\\p{L}]", "");

	// Check de lengte
	if (cleanIsbn.length() != 13) {
	    context.buildConstraintViolationWithTemplate("{validation.boekISBN.FormaatNietOk.message}")
		    .addConstraintViolation().disableDefaultConstraintViolation();

	    return false;
	}

	// We gebruiken module 10 met gewichten 1 en 3 op de eerste 12 cijfers
	// Elk oneven nummer vermenigvuldigen we met 1 en elk even nummer met 3 en
	// tellen we op
	int sum = 0;
	for (int i = 0; i < 12; i++) {
	    int digit = Character.getNumericValue(cleanIsbn.charAt(i));
	    sum += (i % 2 == 0) ? digit : digit * 3;
	}
	// Deze som delen we door 10 en we houden we checksum bij (getal nodig om bij
	// rest te tellen om aan 10 te komen)
	int checksum = 10 - (sum % 10);
	if (checksum == 10) {
	    checksum = 0;
	}

	// Haal 13e getal op, deze moet gelijk zijn aan de checksum
	int lastDigit = Character.getNumericValue(cleanIsbn.charAt(12));
	if (lastDigit != checksum) {
	    context.buildConstraintViolationWithTemplate("{validation.boekISBN.ChecksumNietOk.message}")
		    .addConstraintViolation().disableDefaultConstraintViolation();

	    return false;
	}
	return true;
    }
}
