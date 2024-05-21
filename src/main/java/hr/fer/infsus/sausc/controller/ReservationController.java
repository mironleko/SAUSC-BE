package hr.fer.infsus.sausc.controller;

import hr.fer.infsus.sausc.rest.api.ReservationApi;
import hr.fer.infsus.sausc.rest.model.ReservationDto;
import hr.fer.infsus.sausc.rest.model.ReservationForm;
import hr.fer.infsus.sausc.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReservationController implements ReservationApi {
    private final ReservationService reservationService;
    @Override
    public ResponseEntity<ReservationDto> apiV1ReservationPost(ReservationForm reservationForm) {
        return ResponseEntity.ok(reservationService.createReservation(reservationForm));
    }
}
