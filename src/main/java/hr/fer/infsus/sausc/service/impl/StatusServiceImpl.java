package hr.fer.infsus.sausc.service.impl;

import hr.fer.infsus.sausc.mapper.StatusMapper;
import hr.fer.infsus.sausc.model.db.Status;
import hr.fer.infsus.sausc.repository.StatusRepository;
import hr.fer.infsus.sausc.rest.model.StatusDto;
import hr.fer.infsus.sausc.rest.model.StatusForm;
import hr.fer.infsus.sausc.service.StatusService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatusServiceImpl implements StatusService {

    private final StatusMapper statusMapper;
    private final StatusRepository statusRepository;

    @Transactional
    @Override
    public StatusDto createStatus(StatusForm statusForm) {
        return statusMapper.toDto(statusRepository.save(statusMapper.toEntity(statusForm)));
    }

    @Override
    public List<StatusDto> getStatuses() {
        List<Status> statuses = statusRepository.findAll();
        return statuses.stream().map(statusMapper::toDto).toList();
    }

    @Transactional
    @Override
    public void deleteStatus(Long statusId) {
        statusRepository.findById(statusId)
                .orElseThrow(() -> new EntityNotFoundException("Status with ID: " + statusId + " not found"));
        statusRepository.deleteById(statusId);
    }

    @Override
    public StatusDto getStatus(Long statusId) {

        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new EntityNotFoundException("Status with ID: " + statusId + " not found"));
        return statusMapper.toDto(status);
    }
    @Transactional
    @Override
    public StatusDto updateStatus(Long statusId, StatusForm statusForm) {
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new EntityNotFoundException("Status with ID: " + statusId + " not found"));

        statusMapper.toStatus(status, statusForm);
        statusRepository.save(status);
        return getStatus(statusId);
    }
}
