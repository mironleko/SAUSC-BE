package hr.fer.infsus.sausc;

import hr.fer.infsus.sausc.mapper.ActivityMapper;
import hr.fer.infsus.sausc.mapper.ReservationMapper;
import hr.fer.infsus.sausc.model.db.Activity;
import hr.fer.infsus.sausc.model.db.Reservation;
import hr.fer.infsus.sausc.repository.ActivityRepository;
import hr.fer.infsus.sausc.repository.ReservationRepository;
import hr.fer.infsus.sausc.rest.model.ActivityDto;
import hr.fer.infsus.sausc.rest.model.ReservationDto;
import hr.fer.infsus.sausc.rest.model.ReservationForm;
import hr.fer.infsus.sausc.service.ActivityService;
import hr.fer.infsus.sausc.service.ReservationService;
import hr.fer.infsus.sausc.service.impl.ActivityServiceImpl;
import hr.fer.infsus.sausc.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationMapper reservationMapper;
    @Mock
    private ActivityMapper activityMapper;

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ActivityService activityService;

    private ReservationForm reservationForm;
    private Reservation reservation;
    private ReservationDto reservationDto;
    private ActivityDto activityDto;

    @BeforeEach
    public void init() {
        reservationForm = new ReservationForm();
        reservationForm.setStartTime(LocalDateTime.of(2024, 6, 1, 10, 0));
        reservationForm.setEndTime(LocalDateTime.of(2024, 6, 1, 12, 0));
        reservationForm.setNumberOfParticipants(5);
        reservationForm.setSportsCenterMemberId(1L);
        reservationForm.setIdActivity(1L);
        reservationForm.setIdStatus(1L);

        reservation = new Reservation();
        reservation.setIdReservation(1L);

        reservationDto = new ReservationDto();
        reservationDto.setIdReservation(1L);
        reservationDto.setStartTime(LocalDateTime.of(2024, 6, 1, 10, 0));
        reservationDto.setEndTime(LocalDateTime.of(2024, 6, 1, 12, 0));
        reservationDto.setNumberOfParticipants(5);
        reservationDto.setReservationPrice(100.0);

        activityDto = new ActivityDto();
        activityDto.setIdActivity(1L);
        activityDto.setPricePerHour(50.0);
    }

    @Test
    public void testCreateReservation() {
        given(activityService.getActivity(ArgumentMatchers.anyLong())).willReturn(activityDto);
        given(reservationMapper.toEntity(ArgumentMatchers.any(ReservationForm.class), ArgumentMatchers.anyDouble())).willReturn(reservation);
        given(reservationRepository.save(ArgumentMatchers.any(Reservation.class))).willReturn(reservation);
        given(reservationMapper.toDto(ArgumentMatchers.any(Reservation.class))).willReturn(reservationDto);

        ReservationDto createdReservation = reservationService.createReservation(reservationForm);

        verify(activityService).getActivity(ArgumentMatchers.anyLong());
        verify(reservationMapper).toEntity(ArgumentMatchers.any(ReservationForm.class), ArgumentMatchers.anyDouble());
        verify(reservationRepository).save(ArgumentMatchers.any(Reservation.class));
        verify(reservationMapper).toDto(ArgumentMatchers.any(Reservation.class));

        assertEquals(reservationDto, createdReservation);
    }

}