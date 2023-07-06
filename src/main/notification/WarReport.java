package notification;

public abstract class WarReport {
    private final String message;

    protected WarReport(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
