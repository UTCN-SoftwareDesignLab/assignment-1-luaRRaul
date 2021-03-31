package model.validation;

public class ValidatorConstants {
    public static final String IBAN_VALIDATOR_REGEX = "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}";
    public static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final int MIN_PASSWORD_LENGTH = 8;
}
