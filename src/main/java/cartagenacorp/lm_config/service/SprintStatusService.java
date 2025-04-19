package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.SprintStatus;
import cartagenacorp.lm_config.mapper.NamedIdMapper;
import cartagenacorp.lm_config.repository.SprintStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SprintStatusService {

    private final SprintStatusRepository statusRepository;
    private final NamedIdMapper namedIdMapper;

    @Autowired
    public SprintStatusService(SprintStatusRepository statusRepository, NamedIdMapper namedIdMapper) {
        this.statusRepository = statusRepository;
        this.namedIdMapper = namedIdMapper;
    }

    public NamedIdDTO create(NamedIdDTO namedIdDTO) {
        SprintStatus status = namedIdMapper.toEntitySprintStatus(namedIdDTO);
        return namedIdMapper.toDto(statusRepository.save(status));
    }

    public List<NamedIdDTO> getAll() {
        return statusRepository.findAll().stream()
                .map(namedIdMapper::toDto)
                .collect(Collectors.toList());
    }

    public NamedIdDTO update(Long id, NamedIdDTO namedIdDTO) {
        SprintStatus status = statusRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found"));
        namedIdMapper.partialUpdateSprintStatus(namedIdDTO, status);
        return namedIdMapper.toDto(statusRepository.save(status));
    }

    public void delete(Long id) {
        statusRepository.deleteById(id);
    }
}
