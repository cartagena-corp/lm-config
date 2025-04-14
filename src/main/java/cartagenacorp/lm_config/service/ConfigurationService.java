package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.SystemConfigurationDTO;
import cartagenacorp.lm_config.entity.IssueStatus;
import cartagenacorp.lm_config.entity.Priority;
import cartagenacorp.lm_config.entity.ProjectStatus;
import cartagenacorp.lm_config.repository.PriorityRepository;
import cartagenacorp.lm_config.repository.IssueStatusRepository;
import cartagenacorp.lm_config.repository.ProjectStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationService {

    private final IssueStatusRepository issueStatusRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final PriorityRepository priorityRepository;

    @Autowired
    public ConfigurationService(IssueStatusRepository issueStatusRepository, ProjectStatusRepository projectStatusRepository,
                                PriorityRepository priorityRepository) {
        this.issueStatusRepository = issueStatusRepository;
        this.projectStatusRepository = projectStatusRepository;
        this.priorityRepository = priorityRepository;
    }

    public SystemConfigurationDTO getConfiguration() {
        List<IssueStatus> issueStatuses = issueStatusRepository.findAll();
        List<ProjectStatus> projectStatuses = projectStatusRepository.findAll();
        List<Priority> priorities = priorityRepository.findAll();

        return new SystemConfigurationDTO(issueStatuses, projectStatuses, priorities);
    }
}
