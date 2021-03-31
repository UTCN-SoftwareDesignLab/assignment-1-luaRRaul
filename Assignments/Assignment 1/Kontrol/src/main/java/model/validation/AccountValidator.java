package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static model.validation.ValidatorConstants.IBAN_VALIDATOR_REGEX;

public class AccountValidator extends Validator{

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Account account;
    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateIBAN(account.getIban());
        validateCurrency(account.getCurrency());
        return errors.isEmpty();
    }

    private void validateIBAN(String iban) {
        if (!Pattern.compile(IBAN_VALIDATOR_REGEX).matcher(iban).matches()) {
           errors.add("Invalid IBAN");
        }
    }

    private void validateCurrency(String currency) {
        if(!currency.equals("RON") | currency.equals("EUR")){
           errors.add("Invalid currency");
        }
    }

}
