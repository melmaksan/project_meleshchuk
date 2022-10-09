package controller.util.validator;

public class PhoneValidator extends RegexValidator {

    private final static String INVALID_NUMBER = "invalid.phone.number";

    private final static int MAX_LENGTH = 50;
    /**
     * Regex used to perform validation of data.
     */
    private static final String EMAIL_REGEX = "^(\\+\\d{1,3}[-]?)?\\d{10}$";

    public PhoneValidator() {
        super(EMAIL_REGEX, MAX_LENGTH, INVALID_NUMBER);
    }
}
