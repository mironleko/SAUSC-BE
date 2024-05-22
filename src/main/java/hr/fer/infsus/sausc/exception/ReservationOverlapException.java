package hr.fer.infsus.sausc.exception;

public class ReservationOverlapException extends RuntimeException {
    public ReservationOverlapException(String message) {
        super(message);
    }
}
