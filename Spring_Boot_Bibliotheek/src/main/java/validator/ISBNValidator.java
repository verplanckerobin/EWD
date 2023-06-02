package validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ISBNConstraintValidator.class)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface ISBNValidator {
    String message() default "{validation.boekISBN.FormaatNietOk.message}";

    String checksumMessage() default "{validation.boekISBN.ChecksumNietOk.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
