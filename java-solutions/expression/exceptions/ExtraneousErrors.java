package expression.exceptions;

public class ExtraneousErrors extends RuntimeException {
    private final String message;

    public ExtraneousErrors(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
