package controller.util.validator;

public class TimeValidator extends RegexValidator {

    private static final int MAX_LENGTH = 8;
    private static final String TIME_REGEX = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
    private static final String INVALID_TIME = "Invalid time format";

    public TimeValidator() {
        super(TIME_REGEX, MAX_LENGTH, INVALID_TIME);
    }
}
