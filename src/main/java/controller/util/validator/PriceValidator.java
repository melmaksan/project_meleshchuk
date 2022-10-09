package controller.util.validator;

public class PriceValidator extends RegexValidator {

    private final static int MAX_LENGTH = 14;
    private final static String PRICE_REGEX = "^[1-9]\\d*(\\.\\d+)?$";
    private final static String INVALID_AMOUNT = "invalid.price.format";

    public PriceValidator() {
        super(PRICE_REGEX, MAX_LENGTH, INVALID_AMOUNT);
    }
}
