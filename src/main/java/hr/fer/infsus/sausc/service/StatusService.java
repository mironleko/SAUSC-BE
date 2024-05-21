package hr.fer.infsus.sausc.service;

import hr.fer.infsus.sausc.rest.model.StatusDto;
import hr.fer.infsus.sausc.rest.model.StatusForm;

import java.util.List;

public interface StatusService {
    StatusDto createStatus(StatusForm statusForm);

    List<StatusDto> getStatuses();

    void deleteStatus(Long statusId);

    StatusDto getStatus(Long statusId);

    StatusDto updateStatus(Long statusId, StatusForm statusForm);
}
