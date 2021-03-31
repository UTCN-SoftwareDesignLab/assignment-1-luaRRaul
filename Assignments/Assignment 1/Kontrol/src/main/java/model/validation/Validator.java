package model.validation;

import java.util.regex.Pattern;

import static model.validation.ValidatorConstants.EMAIL_VALIDATION_REGEX;

//I have used this class to validate only some fields ( eg. only the new username when changing an account username)
public class Validator {

    public boolean usernameIsValid(String username){
       return Pattern.compile(EMAIL_VALIDATION_REGEX).matcher(username).matches();
    }

    public boolean validateBalance(String money) {
        //regex for numbers
        return true;
    }

}
