package controller.util.validator;

public class TimeValidator extends RegexValidator {

    private final static int MAX_LENGTH = 8;
    private final static String TIME_REGEX = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
    private final static String INVALID_TIME = "Invalid time format";

    public TimeValidator() {
        super(TIME_REGEX, MAX_LENGTH, INVALID_TIME);
    }
}
