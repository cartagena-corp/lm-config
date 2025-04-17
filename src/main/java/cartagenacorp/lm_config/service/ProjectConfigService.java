package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.entity.IssuePriority;
import cartagenacorp.lm_config.entity.IssueStatus;
import cartagenacorp.lm_config.entity.IssueType;
import cartagenacorp.lm_config.entity.ProjectConfig;
import cartagenacorp.lm_config.repository.IssuePriorityRepository;
import cartagenacorp.lm_config.repository.IssueStatusRepository;
import cartagenacorp.lm_config.repository.IssueTypeRepository;
import cartagenacorp.lm_config.repository.ProjectConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectConfigService {

    private final ProjectConfigRepository configRepository;
    private final IssueStatusRepository issueStatusRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final IssuePriorityRepository issuePriorityRepository;

    @Autowired
    public ProjectConfigService(ProjectConfigRepository configRepository,
                                IssueStatusRepository issueStatusRepository,
                                IssueTypeRepository issueTypeRepository,
                                IssuePriorityRepository issuePriorityRepository) {
        this.configRepository = configRepository;
        this.issueStatusRepository = issueStatusRepository;
        this.issueTypeRepository = issueTypeRepository;
        this.issuePriorityRepository = issuePriorityRepository;
    }

    public ProjectConfig getOrCreateConfig(UUID projectId) {
        ProjectConfig projectConfig = configRepository.findByProjectId(projectId)
                .orElseGet(() -> {
                    ProjectConfig config = new ProjectConfig();
                    config.setProjectId(projectId);
                    return configRepository.save(config);
                });

        if (projectConfig.getIssueStatuses() == null || projectConfig.getIssueStatuses().isEmpty()) {

            List<IssueStatus> defaultStatuses = Arrays.asList(
                    new IssueStatus(null, "Nuevo", projectConfig),
                    new IssueStatus(null, "En progreso", projectConfig),
                    new IssueStatus(null, "Finalizado", projectConfig)
            );
            issueStatusRepository.saveAll(defaultStatuses);

            List<IssueType> defaultTypes = Arrays.asList(
                    new IssueType(null, "Bug", projectConfig),
                    new IssueType(null, "Historia", projectConfig),
                    new IssueType(null, "Tarea", projectConfig)
            );
            issueTypeRepository.saveAll(defaultTypes);

            List<IssuePriority> defaultPriorities = Arrays.asList(
                    new IssuePriority(null, "Baja", projectConfig),
                    new IssuePriority(null, "Media", projectConfig),
                    new IssuePriority(null, "Alta", projectConfig)
            );
            issuePriorityRepository.saveAll(defaultPriorities);

            projectConfig.setIssueStatuses(defaultStatuses);
            projectConfig.setIssueTypes(defaultTypes);
            projectConfig.setIssuePriorities(defaultPriorities);

            projectConfig = configRepository.save(projectConfig);
        }

        return projectConfig;
    }
}

