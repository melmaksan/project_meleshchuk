package controller.util.validator;

public class PasswordValidator extends AbstractValidator<String> {

    private final static String INVALID_PASSWORD = "Invalid password, please try again";
    private final static int PASSWORD_MIN_LENGTH = 5;
    private final static int PASSWORD_MAX_LENGTH = 45;

    public PasswordValidator() {
        super(INVALID_PASSWORD);
    }

    @Override
    public boolean isValid(String password) {
        resetErrorStatus();
        if(password == null || password.length() < PASSWORD_MIN_LENGTH ||
                password.length() > PASSWORD_MAX_LENGTH) {
            setErrorStatus(true);
            return false;
        }
        return true;
    }
}
