package hr.fer.infsus.sausc.service.impl;

import hr.fer.infsus.sausc.mapper.ReservationMapper;
import hr.fer.infsus.sausc.repository.ReservationRepository;
import hr.fer.infsus.sausc.rest.model.ReservationDto;
import hr.fer.infsus.sausc.rest.model.ReservationForm;
import hr.fer.infsus.sausc.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Transactional
    @Override
    public ReservationDto createReservation(ReservationForm reservationForm) {
        return reservationMapper.toDto(reservationRepository.save(reservationMapper.toEntity(reservationForm)));
    }
}
