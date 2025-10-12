package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.IssueStatus;
import cartagenacorp.lm_config.entity.ProjectConfig;
import cartagenacorp.lm_config.mapper.NamedIdMapper;
import cartagenacorp.lm_config.repository.IssueStatusRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IssueStatusService {

    private final IssueStatusRepository statusRepository;
    private final ProjectConfigService configService;
    private final NamedIdMapper namedIdMapper;

    public IssueStatusService(IssueStatusRepository statusRepository, ProjectConfigService configService, NamedIdMapper namedIdMapper) {
        this.statusRepository = statusRepository;
        this.configService = configService;
        this.namedIdMapper = namedIdMapper;
    }

    @Transactional
    public NamedIdDTO create(UUID projectId, NamedIdDTO namedIdDTO) {
        ProjectConfig config = configService.getOrCreateConfig(projectId);
        IssueStatus status = namedIdMapper.toEntityIssueStatus(namedIdDTO);
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
    public List<NamedIdDTO> update(UUID projectId, List<NamedIdDTO> statuses) {
        List<Long> ids = statuses.stream()
                .map(NamedIdDTO::getId)
                .toList();

        List<IssueStatus> existing = statusRepository.findAllById(ids);

        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No issue statuses found for given IDs");
        }

        Set<UUID> projectIds = existing.stream()
                .map(s -> s.getProjectConfig().getProjectId())
                .collect(Collectors.toSet());

        if (projectIds.size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Statuses belong to multiple projects");
        }

        if (!projectIds.contains(projectId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Statuses do not belong to the specified project");
        }

        Map<Long, NamedIdDTO> updates = statuses.stream()
                .collect(Collectors.toMap(NamedIdDTO::getId, Function.identity()));

        for (IssueStatus status : existing) {
            NamedIdDTO update = updates.get(status.getId());
            if (update != null) {
                namedIdMapper.partialUpdateIssueStatus(update, status);
            }
        }

        List<IssueStatus> saved = statusRepository.saveAll(existing);
        return saved.stream()
                .sorted(Comparator.comparing(IssueStatus::getOrderIndex))
                .map(namedIdMapper::toDto)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        IssueStatus status = statusRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ProjectConfig config = status.getProjectConfig();
        if (config != null) {
            config.getIssueStatuses().removeIf(s -> s.getId().equals(id));
        }

        statusRepository.delete(status);
    }
}
