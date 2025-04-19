package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.IssueType;
import cartagenacorp.lm_config.entity.ProjectConfig;
import cartagenacorp.lm_config.mapper.NamedIdMapper;
import cartagenacorp.lm_config.repository.IssueTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IssueTypeService {

    private final IssueTypeRepository typeRepository;
    private final ProjectConfigService configService;
    private final NamedIdMapper namedIdMapper;

    @Autowired
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
        return typeRepository.findByProjectConfig_ProjectId(projectId).stream()
                .map(namedIdMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public NamedIdDTO update(Long id, NamedIdDTO namedIdDTO) {
        IssueType type = typeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        namedIdMapper.partialUpdateIssueType(namedIdDTO, type);
        return namedIdMapper.toDto(typeRepository.save(type));
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
