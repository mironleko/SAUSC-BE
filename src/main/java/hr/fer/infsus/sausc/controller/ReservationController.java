package hr.fer.infsus.sausc.controller;

import hr.fer.infsus.sausc.rest.api.ReservationApi;
import hr.fer.infsus.sausc.rest.model.*;
import hr.fer.infsus.sausc.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReservationController implements ReservationApi {
    private final ReservationService reservationService;

    @Override
    public ResponseEntity<ReservationAvailableTimesResponseDto> apiV1ReservationAvailableEndTimesPost(ReservationAvailableEndTimesRequestDto reservationAvailableEndTimesRequestDto) {
        List<String> endTimes = reservationService.getAvailableEndTimes(reservationAvailableEndTimesRequestDto);

        return ResponseEntity.ok(new ReservationAvailableTimesResponseDto().possibleTimes(endTimes));
    }

    @Override
    public ResponseEntity<ReservationAvailableTimesResponseDto> apiV1ReservationAvailableStartTimesPost(ReservationAvailableStartTimesRequestDto reservationAvailableStartTimesRequestDto) {
        List<String> startTimes = reservationService.getAvailableStartTimes(reservationAvailableStartTimesRequestDto);

        return ResponseEntity.ok(new ReservationAvailableTimesResponseDto().possibleTimes(startTimes));
    }

    @Override
    public ResponseEntity<ReservationDto> apiV1ReservationPost(ReservationForm reservationForm) {
        return ResponseEntity.ok(reservationService.createReservation(reservationForm));
    }


    @Override
public ResponseEntity<ReservationDto> apiV1ReservationReservationIdStatusPut(Long reservationId, ChangeReservationStatusRequestDto changeReservationStatusRequestDto) {
    return ResponseEntity.ok(reservationService.updateReservationStatus(reservationId, changeReservationStatusRequestDto));
}

@Override
public ResponseEntity<ListReservationDto> apiV1ReservationsPost(ReservationsRequestDto reservationsRequestDto) {
    List<ReservationDto> reservations = reservationService.getReservationsFromInterval(reservationsRequestDto);

    return ResponseEntity.ok(new ListReservationDto().reservations(reservations));
}

@Override
public ResponseEntity<ListReservationDto> apiV1ReservationsUserIdGet(Long userId) {
    List<ReservationDto> reservations = reservationService.getReservationsForUser(userId);

    return ResponseEntity.ok(new ListReservationDto().reservations(reservations));
}
}
