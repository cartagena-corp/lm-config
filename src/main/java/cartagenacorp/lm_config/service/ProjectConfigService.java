package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.entity.*;
import cartagenacorp.lm_config.repository.*;
import cartagenacorp.lm_config.util.JwtContextHolder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectConfigService {

    private final ProjectConfigRepository configRepository;
    private final IssueStatusRepository issueStatusRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final IssuePriorityRepository issuePriorityRepository;
    private final SprintStatusRepository sprintStatusRepository;
    private final IssueDescriptionsRepository issueDescriptionsRepository;

    @Autowired
    public ProjectConfigService(ProjectConfigRepository configRepository,
                                IssueStatusRepository issueStatusRepository,
                                IssueTypeRepository issueTypeRepository,
                                IssuePriorityRepository issuePriorityRepository,
                                SprintStatusRepository sprintStatusRepository,
                                IssueDescriptionsRepository issueDescriptionsRepository) {
        this.configRepository = configRepository;
        this.issueStatusRepository = issueStatusRepository;
        this.issueTypeRepository = issueTypeRepository;
        this.issuePriorityRepository = issuePriorityRepository;
        this.sprintStatusRepository = sprintStatusRepository;
        this.issueDescriptionsRepository = issueDescriptionsRepository;
    }

    public ProjectConfig getOrCreateConfig(UUID projectId) {
        ProjectConfig projectConfig = configRepository.findByProjectId(projectId)
                .orElseGet(() -> {
                    UUID organizationId = JwtContextHolder.getOrganizationId();
                    ProjectConfig config = new ProjectConfig();
                    config.setProjectId(projectId);
                    config.setOrganizationId(organizationId);
                    return configRepository.save(config);
                });

        if (projectConfig.getIssueStatuses() == null || projectConfig.getIssueStatuses().isEmpty()) {

            List<IssueStatus> defaultStatuses = Arrays.asList(
                    new IssueStatus(null, "Nuevo", "#3498db", projectConfig, 1),
                    new IssueStatus(null, "En progreso", "#f1c40f",  projectConfig, 2),
                    new IssueStatus(null, "Finalizado", "#2ecc71",  projectConfig, 3)
            );
            issueStatusRepository.saveAll(defaultStatuses);

            List<IssueType> defaultTypes = Arrays.asList(
                    new IssueType(null, "Bug", "#e74c3c",  projectConfig, 1),
                    new IssueType(null, "Historia", "#9b59b6",  projectConfig, 2),
                    new IssueType(null, "Tarea", "#2980b9",  projectConfig, 3)
            );
            issueTypeRepository.saveAll(defaultTypes);

            List<IssuePriority> defaultPriorities = Arrays.asList(
                    new IssuePriority(null, "Baja", "#27ae60",  projectConfig, 1),
                    new IssuePriority(null, "Media", "#f39c12",  projectConfig, 2),
                    new IssuePriority(null, "Alta", "#c0392b",  projectConfig, 3)
            );
            issuePriorityRepository.saveAll(defaultPriorities);

            List<SprintStatus> defaultSprintStatuses = Arrays.asList(
                    new SprintStatus(null, "Planificado", "#3498db", projectConfig, 1),
                    new SprintStatus(null, "En progreso", "#f1c40f", projectConfig, 2),
                    new SprintStatus(null, "Completado", "#2ecc71", projectConfig, 3)
            );
            sprintStatusRepository.saveAll(defaultSprintStatuses);

            List<IssueDescriptions> defaultDescriptions = Arrays.asList(
                    new IssueDescriptions(null, "Descripción", projectConfig, 1)
            );
            issueDescriptionsRepository.saveAll(defaultDescriptions);


            projectConfig.setIssueStatuses(defaultStatuses);
            projectConfig.setIssueTypes(defaultTypes);
            projectConfig.setIssuePriorities(defaultPriorities);
            projectConfig.setSprintStatuses(defaultSprintStatuses);
            projectConfig.setIssueDescriptions(defaultDescriptions);

            projectConfig = configRepository.save(projectConfig);
        }

        return projectConfig;
    }

    @Transactional
    public void deleteConfigByProjectId(UUID projectId) {
        UUID organizationId = JwtContextHolder.getOrganizationId();
        ProjectConfig projectConfig = configRepository.findByProjectId(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project config not found for project ID: " + projectId));
        if(!projectConfig.getOrganizationId().equals(organizationId)) {
            throw new EntityNotFoundException("Project config does not belong to the current organization");
        }
        configRepository.deleteByProjectId(projectId);
    }
}

