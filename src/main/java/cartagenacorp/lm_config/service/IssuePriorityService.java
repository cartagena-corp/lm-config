package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.IssuePriority;
import cartagenacorp.lm_config.entity.ProjectConfig;
import cartagenacorp.lm_config.mapper.NamedIdMapper;
import cartagenacorp.lm_config.repository.IssuePriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IssuePriorityService {

    private final IssuePriorityRepository priorityRepository;
    private final ProjectConfigService configService;
    private final NamedIdMapper namedIdMapper;

    @Autowired
    public IssuePriorityService(IssuePriorityRepository priorityRepository, ProjectConfigService configService, NamedIdMapper namedIdMapper) {
        this.priorityRepository = priorityRepository;
        this.configService = configService;
        this.namedIdMapper = namedIdMapper;
    }

    @Transactional
    public NamedIdDTO create(UUID projectId, NamedIdDTO namedIdDTO) {
        ProjectConfig config = configService.getOrCreateConfig(projectId);
        IssuePriority priority = namedIdMapper.toEntityIssuePriority(namedIdDTO);
        priority.setProjectConfig(config);
        return namedIdMapper.toDto(priorityRepository.save(priority));
    }

    @Transactional(readOnly = true)
    public List<NamedIdDTO> getAll(UUID projectId) {
        return priorityRepository.findByProjectConfig_ProjectIdOrderByOrderIndexAsc(projectId).stream()
                .map(namedIdMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public NamedIdDTO update(Long id, NamedIdDTO namedIdDTO) {
        IssuePriority priority = priorityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        namedIdMapper.partialUpdateIssuePriority(namedIdDTO, priority);
        return namedIdMapper.toDto(priorityRepository.save(priority));
    }

    @Transactional
    public void delete(Long id) {
        IssuePriority priority = priorityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ProjectConfig config = priority.getProjectConfig();
        if (config != null) {
            config.getIssuePriorities().removeIf(s -> s.getId().equals(id));
        }

        priorityRepository.delete(priority);
    }
}
