package controller.util.validator;

public class PriceValidator extends RegexValidator {

    private static final int MAX_LENGTH = 10;
    private static final String AMOUNT_REGEX = "^\\d+(\\.\\d{1,2})?$";
    private static final String INVALID_AMOUNT = "Invalid amount format";

    public PriceValidator() {
        super(AMOUNT_REGEX, MAX_LENGTH, INVALID_AMOUNT);
    }
}
