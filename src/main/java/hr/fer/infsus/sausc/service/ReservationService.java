package hr.fer.infsus.sausc.service;

import hr.fer.infsus.sausc.rest.model.*;

import java.util.List;

public interface ReservationService {

    ReservationDto createReservation(ReservationForm reservationForm);

    List<ReservationDto> getReservationsForUser(Long userId);

    List<ReservationDto> getReservationsFromInterval(ReservationsRequestDto reservationsRequestDto);

    List<String> getAvailableStartTimes(ReservationAvailableStartTimesRequestDto reservationAvailableStartTimesRequestDto);

    List<String> getAvailableEndTimes(ReservationAvailableEndTimesRequestDto reservationAvailableEndTimesRequestDto);
}
