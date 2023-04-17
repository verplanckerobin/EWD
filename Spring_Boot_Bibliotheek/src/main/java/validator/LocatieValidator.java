package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Locatie;

public class LocatieValidator implements Validator {

    public boolean supports(Class<?> klass) {
	return Locatie.class.isAssignableFrom(klass);
    }

    public void validate(Object target, Errors errors) {
	Locatie locatie = (Locatie) target;

	if (Math.abs(locatie.getPlaatscode1() - locatie.getPlaatscode2()) < 50) {
	    errors.rejectValue("plaatscode", "validation.locatie.Verschil.message");
	}
    }
}
