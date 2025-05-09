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
                    new IssueStatus(null, "Nuevo", "#3498db", projectConfig),
                    new IssueStatus(null, "En progreso", "#f1c40f",  projectConfig),
                    new IssueStatus(null, "Finalizado", "#2ecc71",  projectConfig)
            );
            issueStatusRepository.saveAll(defaultStatuses);

            List<IssueType> defaultTypes = Arrays.asList(
                    new IssueType(null, "Bug", "#e74c3c",  projectConfig),
                    new IssueType(null, "Historia", "#9b59b6",  projectConfig),
                    new IssueType(null, "Tarea", "#2980b9",  projectConfig)
            );
            issueTypeRepository.saveAll(defaultTypes);

            List<IssuePriority> defaultPriorities = Arrays.asList(
                    new IssuePriority(null, "Baja", "#27ae60",  projectConfig),
                    new IssuePriority(null, "Media", "#f39c12",  projectConfig),
                    new IssuePriority(null, "Alta", "#c0392b",  projectConfig)
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

