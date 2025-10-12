package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.IssuePriority;
import cartagenacorp.lm_config.entity.IssueStatus;
import cartagenacorp.lm_config.entity.ProjectConfig;
import cartagenacorp.lm_config.mapper.NamedIdMapper;
import cartagenacorp.lm_config.repository.IssuePriorityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IssuePriorityService {

    private final IssuePriorityRepository priorityRepository;
    private final ProjectConfigService configService;
    private final NamedIdMapper namedIdMapper;

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
    public List<NamedIdDTO> update(UUID projectId, List<NamedIdDTO> priorities) {
        List<Long> ids = priorities.stream()
                .map(NamedIdDTO::getId)
                .toList();

        List<IssuePriority> existing = priorityRepository.findAllById(ids);

        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No issue priorities found for given IDs");
        }

        Set<UUID> projectIds = existing.stream()
                .map(s -> s.getProjectConfig().getProjectId())
                .collect(Collectors.toSet());

        if (projectIds.size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Priorities belong to multiple projects");
        }

        if (!projectIds.contains(projectId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Priorities do not belong to the specified project");
        }

        Map<Long, NamedIdDTO> updates = priorities.stream()
                .collect(Collectors.toMap(NamedIdDTO::getId, Function.identity()));

        for (IssuePriority priority : existing) {
            NamedIdDTO update = updates.get(priority.getId());
            if (update != null) {
                namedIdMapper.partialUpdateIssuePriority(update, priority);
            }
        }

        List<IssuePriority> saved = priorityRepository.saveAll(existing);
        return saved.stream()
                .sorted(Comparator.comparing(IssuePriority::getOrderIndex))
                .map(namedIdMapper::toDto)
                .toList();
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
