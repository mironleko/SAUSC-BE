package hr.fer.infsus.sausc.service;

import hr.fer.infsus.sausc.rest.model.ReservationDto;
import hr.fer.infsus.sausc.rest.model.ReservationForm;

public interface ReservationService {

    ReservationDto createReservation(ReservationForm reservationForm);
}
