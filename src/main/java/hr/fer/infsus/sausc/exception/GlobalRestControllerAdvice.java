package hr.fer.infsus.sausc.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(ReservationOverlapException.class)
    public ResponseEntity<String> handleReservationOverlapException(ReservationOverlapException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
