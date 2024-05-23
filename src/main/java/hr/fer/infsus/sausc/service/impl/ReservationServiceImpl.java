package hr.fer.infsus.sausc.service.impl;

import hr.fer.infsus.sausc.constants.SAUSCConstants;
import hr.fer.infsus.sausc.exception.ReservationOverlapException;
import hr.fer.infsus.sausc.mapper.ReservationMapper;
import hr.fer.infsus.sausc.model.db.Reservation;
import hr.fer.infsus.sausc.repository.ReservationRepository;
import hr.fer.infsus.sausc.rest.model.*;
import hr.fer.infsus.sausc.service.ActivityService;
import hr.fer.infsus.sausc.service.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final ActivityService activityService;


    @Transactional
    @Override
    public ReservationDto createReservation(ReservationForm reservationForm) {
        ActivityDto activity = activityService.getActivity(reservationForm.getIdActivity());

        validateReservation(reservationForm);
        
        double reservationPrice = calculateReservationPrice(reservationForm, activity);

        return reservationMapper.toDto(reservationRepository.save(reservationMapper.toEntity(reservationForm, reservationPrice)));
    }

    private void validateReservation(ReservationForm reservationForm) {
        List<Reservation> overlappingReservations = reservationRepository.findByActivity_IdActivityAndDateRange(
                reservationForm.getIdActivity(),
                reservationForm.getStartTime(),
                reservationForm.getEndTime()
        );

        if (!overlappingReservations.isEmpty()) {
            throw new ReservationOverlapException("The reservation time overlaps with an existing reservation.");
        }
    }

    private static double calculateReservationPrice(ReservationForm reservationForm, ActivityDto activity) {
        Duration duration = Duration.between(reservationForm.getStartTime(), reservationForm.getEndTime());
        double durationInHours = duration.toMinutes() / 60.0;

        return durationInHours * activity.getPricePerHour();
    }

    @Override
    public List<ReservationDto> getReservationsForUser(Long userId) {
        List<Reservation> reservations = reservationRepository.findBySportsCenterMember_IdUser(userId);
        return reservations.stream().map(reservationMapper::toDto).toList();
    }

    @Override
    public List<ReservationDto> getReservationsFromInterval(ReservationsRequestDto reservationsRequestDto) {

        LocalDateTime startDateTime = LocalDateTime.of(reservationsRequestDto.getStartDate(), SAUSCConstants.START_TIME);

        LocalDateTime endDateTime = LocalDateTime.of(reservationsRequestDto.getEndDate(), SAUSCConstants.END_TIME);

        List<Reservation> reservations = reservationRepository
                .findReservationsInInterval(startDateTime, endDateTime);

        return reservations.stream().map(reservationMapper::toDto).toList();
    }

    @Override
    public List<String> getAvailableStartTimes(ReservationAvailableStartTimesRequestDto request) {
        List<String> availableStartTimes = generateStartTimes(request.getIdActivity(), request.getDate());

        return availableStartTimes;
    }

    @Override
    public List<String> getAvailableEndTimes(ReservationAvailableEndTimesRequestDto request) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime parsedStartTime = LocalTime.parse(request.getStartTime(), timeFormatter);
        LocalDateTime startDateTime = LocalDateTime.of(request.getStartDate(), parsedStartTime);
        List<String> availableEndTimes = generateEndTimes(request.getIdActivity(), startDateTime);

        return availableEndTimes;
    }

    @Override
    public ReservationDto updateReservationStatus(Long reservationId, ChangeReservationStatusRequestDto request) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with ID: " + reservationId + " not found"));

        reservationMapper.changeReservationStatus(reservation, request);
        reservationRepository.save(reservation);

        return reservationMapper.toDto(reservation);
    }

    private List<String> generateEndTimes(Long activityId, LocalDateTime startDateTime) {
        List<String> endTimes = new ArrayList<>();
        LocalDateTime currentEndTime = startDateTime.plusMinutes(SAUSCConstants.INTERVAL_MINUTES);
        LocalDateTime maxEndTime = startDateTime.plusHours(SAUSCConstants.MAX_RESERVATION_DURATION);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Generate possible end times in 30-minute intervals within the next 3 hours
        while (currentEndTime.isBefore(maxEndTime) || currentEndTime.isEqual(maxEndTime)) {
            if (isTimeSlotAvailable(activityId, startDateTime, currentEndTime)) {
                endTimes.add(currentEndTime.toLocalTime().format(timeFormatter));
            }
            currentEndTime = currentEndTime.plusMinutes(SAUSCConstants.INTERVAL_MINUTES);
        }

        return endTimes;
    }

    private boolean isTimeSlotAvailable(Long activityId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> existingReservations = reservationRepository.findByActivity_IdActivityAndDateRange(activityId, startTime, endTime);

        for (Reservation reservation : existingReservations) {
            if (startTime.isBefore(reservation.getEndTime()) && endTime.isAfter(reservation.getStartTime())) {
                return false;
            }
        }
        return true;
    }

    private List<String> generateStartTimes(Long idActivity, LocalDate date) {
        List<String> startTimes = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime startOfDay = date.atTime(SAUSCConstants.START_TIME);
        LocalDateTime endOfDay = date.atTime(SAUSCConstants.END_TIME);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        if (date.isEqual(currentDateTime.toLocalDate())) {
            startOfDay = currentDateTime.plusMinutes(SAUSCConstants.INTERVAL_MINUTES - (currentDateTime.getMinute() % SAUSCConstants.INTERVAL_MINUTES)).withSecond(0).withNano(0);
        }

        while (startOfDay.isBefore(endOfDay)) {
            startTimes.add(startOfDay.toLocalTime().format(timeFormatter));
            startOfDay = startOfDay.plusMinutes(SAUSCConstants.INTERVAL_MINUTES);
        }

        List<Reservation> existingReservations = reservationRepository.findByActivity_IdActivityAndDate(idActivity, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
        return filterOverlappingStartTimes(startTimes, existingReservations, timeFormatter);
    }

    private List<String> filterOverlappingStartTimes(List<String> startTimes, List<Reservation> reservations, DateTimeFormatter timeFormatter) {
        return startTimes.stream().filter(startTime -> {
            LocalTime startTimeAsLocalTime = LocalTime.parse(startTime, timeFormatter);
            for (Reservation reservation : reservations) {
                LocalTime reservationStartTime = reservation.getStartTime().toLocalTime();
                LocalTime reservationEndTime = reservation.getEndTime().toLocalTime();
                if (startTimeAsLocalTime.isBefore(reservationEndTime) && startTimeAsLocalTime.plusMinutes(SAUSCConstants.INTERVAL_MINUTES).isAfter(reservationStartTime)) {
                    return false;
                }
            }
            return true;
        }).toList();
    }
}
