package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.ProjectConfig;
import cartagenacorp.lm_config.entity.SprintStatus;
import cartagenacorp.lm_config.mapper.NamedIdMapper;
import cartagenacorp.lm_config.repository.SprintStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SprintStatusService {

    private final SprintStatusRepository statusRepository;
    private final ProjectConfigService configService;
    private final NamedIdMapper namedIdMapper;

    @Autowired
    public SprintStatusService(SprintStatusRepository statusRepository, ProjectConfigService configService, NamedIdMapper namedIdMapper) {
        this.statusRepository = statusRepository;
        this.configService = configService;
        this.namedIdMapper = namedIdMapper;
    }

    @Transactional
    public NamedIdDTO create(UUID projectId, NamedIdDTO namedIdDTO) {
        ProjectConfig config = configService.getOrCreateConfig(projectId);
        SprintStatus status = namedIdMapper.toEntitySprintStatus(namedIdDTO);
        status.setProjectConfig(config);
        return namedIdMapper.toDto(statusRepository.save(status));
    }

    @Transactional(readOnly = true)
    public List<NamedIdDTO> getAll(UUID projectId) {
        return statusRepository.findByProjectConfig_ProjectIdOrderByOrderIndexAsc(projectId).stream()
                .map(namedIdMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public NamedIdDTO update(Long id, NamedIdDTO namedIdDTO) {
        SprintStatus status = statusRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found"));
        namedIdMapper.partialUpdateSprintStatus(namedIdDTO, status);
        return namedIdMapper.toDto(statusRepository.save(status));
    }

    @Transactional
    public void delete(Long id) {
        SprintStatus status = statusRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found"));

        ProjectConfig config = status.getProjectConfig();
        if (config != null) {
            config.getSprintStatuses().removeIf(s -> s.getId().equals(id));
        }

        statusRepository.delete(status);
    }
}
