package controller.util.validator;

public class PasswordValidator extends AbstractValidator<String> {

    private static final String INVALID_PASSWORD = "Invalid password, please try again";
    private static final int PASSWORD_MIN_LENGTH = 5;
    private static final int PASSWORD_MAX_LENGTH = 45;

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
