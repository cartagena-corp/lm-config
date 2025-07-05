package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.IssueDescriptionsDto;
import cartagenacorp.lm_config.entity.IssueDescriptions;
import cartagenacorp.lm_config.entity.ProjectConfig;
import cartagenacorp.lm_config.mapper.IssueDescriptionsMapper;
import cartagenacorp.lm_config.repository.IssueDescriptionsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IssueDescriptionsService {

    private final IssueDescriptionsRepository issueDescriptionsRepository;
    private final ProjectConfigService projectConfigService;
    private final IssueDescriptionsMapper issueDescriptionsMapper;


    public IssueDescriptionsService(IssueDescriptionsRepository issueDescriptionsRepository,
                                    ProjectConfigService projectConfigService,
                                    IssueDescriptionsMapper issueDescriptionsMapper) {
        this.issueDescriptionsRepository = issueDescriptionsRepository;
        this.projectConfigService = projectConfigService;
        this.issueDescriptionsMapper = issueDescriptionsMapper;
    }

    @Transactional
    public IssueDescriptionsDto create(UUID projectId, IssueDescriptionsDto issueDescriptionsDto) {
        ProjectConfig projectConfig = projectConfigService.getOrCreateConfig(projectId);
        IssueDescriptions issueDescriptions = issueDescriptionsMapper.toEntity(issueDescriptionsDto);
        issueDescriptions.setProjectConfig(projectConfig);
        return issueDescriptionsMapper.toDto(issueDescriptionsRepository.save(issueDescriptions));
    }

    @Transactional(readOnly = true)
    public List<IssueDescriptionsDto> getAll(UUID projectId) {
        return issueDescriptionsRepository.findByProjectConfig_ProjectId(projectId).stream()
                .map(issueDescriptionsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public IssueDescriptionsDto update(Long id, IssueDescriptionsDto issueDescriptionsDto) {
        IssueDescriptions issueDescriptions = issueDescriptionsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue description not found"));
        issueDescriptionsMapper.partialUpdate(issueDescriptionsDto, issueDescriptions);
        return issueDescriptionsMapper.toDto(issueDescriptionsRepository.save(issueDescriptions));
    }

    @Transactional
    public void delete(Long id) {
        IssueDescriptions issueDescriptions = issueDescriptionsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue description not found"));

        ProjectConfig config = issueDescriptions.getProjectConfig();
        if (config != null) {
            config.getIssueDescriptions().removeIf(s -> s.getId().equals(id));
        }

        issueDescriptionsRepository.delete(issueDescriptions);
    }
}
