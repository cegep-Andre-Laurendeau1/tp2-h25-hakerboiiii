package ca.cal.tp2.exception;

public class DocumentNotAvailable extends RuntimeException {
    public DocumentNotAvailable(String message) {
        super(message);
    }
}
