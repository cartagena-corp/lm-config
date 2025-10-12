package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.IssueStatus;
import cartagenacorp.lm_config.entity.IssueType;
import cartagenacorp.lm_config.entity.ProjectConfig;
import cartagenacorp.lm_config.mapper.NamedIdMapper;
import cartagenacorp.lm_config.repository.IssueTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IssueTypeService {

    private final IssueTypeRepository typeRepository;
    private final ProjectConfigService configService;
    private final NamedIdMapper namedIdMapper;

    public IssueTypeService(IssueTypeRepository typeRepository, ProjectConfigService configService, NamedIdMapper namedIdMapper) {
        this.typeRepository = typeRepository;
        this.configService = configService;
        this.namedIdMapper = namedIdMapper;
    }

    @Transactional
    public NamedIdDTO create(UUID projectId, NamedIdDTO namedIdDTO) {
        ProjectConfig config = configService.getOrCreateConfig(projectId);
        IssueType type = namedIdMapper.toEntityIssueType(namedIdDTO);
        type.setProjectConfig(config);
        return namedIdMapper.toDto(typeRepository.save(type));
    }

    @Transactional(readOnly = true)
    public List<NamedIdDTO> getAll(UUID projectId) {
        return typeRepository.findByProjectConfig_ProjectIdOrderByOrderIndexAsc(projectId).stream()
                .map(namedIdMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<NamedIdDTO> update(UUID projectId, List<NamedIdDTO> types) {
        List<Long> ids = types.stream()
                .map(NamedIdDTO::getId)
                .toList();

        List<IssueType> existing = typeRepository.findAllById(ids);

        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No issue types found for given IDs");
        }

        Set<UUID> projectIds = existing.stream()
                .map(s -> s.getProjectConfig().getProjectId())
                .collect(Collectors.toSet());

        if (projectIds.size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Types belong to multiple projects");
        }

        if (!projectIds.contains(projectId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Types do not belong to the specified project");
        }

        Map<Long, NamedIdDTO> updates = types.stream()
                .collect(Collectors.toMap(NamedIdDTO::getId, Function.identity()));

        for (IssueType type : existing) {
            NamedIdDTO update = updates.get(type.getId());
            if (update != null) {
                namedIdMapper.partialUpdateIssueType(update, type);
            }
        }

        List<IssueType> saved = typeRepository.saveAll(existing);
        return saved.stream()
                .sorted(Comparator.comparing(IssueType::getOrderIndex))
                .map(namedIdMapper::toDto)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        IssueType type = typeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ProjectConfig config = type.getProjectConfig();
        if (config != null) {
            config.getIssueTypes().removeIf(s -> s.getId().equals(id));
        }

        typeRepository.delete(type);
    }
}
