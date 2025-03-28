package ca.cal.tp2.exception;

public class EmprunteurDoesNotExists extends RuntimeException {
    public EmprunteurDoesNotExists(String message) {
        super(message);
    }
}
