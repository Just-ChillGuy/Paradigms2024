package expression.generic;

public class ExtraneousErrors extends Exception {
    private final String message;

    public ExtraneousErrors(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
