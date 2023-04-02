package validator;

import domain.Account;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

public class AccountValidation implements Validator{

    @Override
    public boolean supports(Class<?> klass) {
        return Account.class.isAssignableFrom(klass);
    }
      
     @Override
    public void validate(Object target, Errors errors) {
        
        Account account = (Account) target;
        
        double percent = account.getPercent();
        
        if ((int) (percent * 100) % 2 != 0) {
        
            errors.rejectValue("percent",
                    "",
                    "percentage must have an even number.");
        }
    }
}
