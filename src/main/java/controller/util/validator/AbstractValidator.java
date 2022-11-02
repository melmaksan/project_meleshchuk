package controller.util.validator;

public abstract class AbstractValidator<T> implements Validator<T> {

    /**
     * Default error message, that will shown in case
     * when {@link #isError} equals {@code true}
     */
    private final String errorMessage;
    /**
     * Indicates if error occurs in validation process
     */
    private boolean isError;

    protected AbstractValidator(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorKey() {
        return isError ? errorMessage : null;
    }

    @Override
    public void resetErrorStatus() {
        isError = false;
    }

    public void setErrorStatus(boolean status) {
        isError = status;
    }
}
