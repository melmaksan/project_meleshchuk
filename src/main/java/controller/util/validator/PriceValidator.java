package controller.util.validator;

public class PriceValidator extends RegexValidator {

    private final static int MAX_LENGTH = 10;
    private final static String AMOUNT_REGEX = "^\\d+(\\.\\d{1,2})?$";
    private final static String INVALID_AMOUNT = "invalid.amount.format";

    public PriceValidator() {
        super(AMOUNT_REGEX, MAX_LENGTH, INVALID_AMOUNT);
    }
}
