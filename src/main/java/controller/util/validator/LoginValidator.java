package controller.util.validator;

public class LoginValidator extends RegexValidator {

    private final static String INVALID_EMAIL_KEY = "invalid.email";

    private final static int MAX_LENGTH = 50;
    /**
     * Regex used to perform validation of data.
     */
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,6}$";

    public LoginValidator() {
        super(EMAIL_REGEX, MAX_LENGTH, INVALID_EMAIL_KEY);
    }
}
