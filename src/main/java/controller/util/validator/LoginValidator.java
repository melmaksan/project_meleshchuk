package controller.util.validator;

public class LoginValidator extends RegexValidator {

    private static final String INVALID_EMAIL_KEY = "Invalid email, please try again";

    private static final int MAX_LENGTH = 30;
    /**
     * Regex used to perform validation of data.
     */
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,6}$";

    public LoginValidator() {
        super(EMAIL_REGEX, MAX_LENGTH, INVALID_EMAIL_KEY);
    }
}
