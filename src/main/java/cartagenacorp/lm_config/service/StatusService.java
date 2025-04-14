package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.entity.IssueStatus;
import cartagenacorp.lm_config.entity.ProjectStatus;
import cartagenacorp.lm_config.repository.IssueStatusRepository;
import cartagenacorp.lm_config.repository.ProjectStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StatusService {

    private final IssueStatusRepository issueStatusRepository;
    private final ProjectStatusRepository projectStatusRepository;

    @Autowired
    public StatusService(IssueStatusRepository issueStatusRepository, ProjectStatusRepository projectStatusRepository) {
        this.issueStatusRepository = issueStatusRepository;
        this.projectStatusRepository = projectStatusRepository;
    }

    public List<IssueStatus> findAllIssueStatuses() {
        return issueStatusRepository.findAll();
    }

    public List<ProjectStatus> findAllProjectStatuses() {
        return projectStatusRepository.findAll();
    }

    public IssueStatus saveIssueStatus(IssueStatus status) {
        return issueStatusRepository.save(status);
    }

    public ProjectStatus saveProjectStatus(ProjectStatus status) {
        return projectStatusRepository.save(status);
    }

    public void deleteIssueStatus(String name) {
        IssueStatus status = issueStatusRepository.findById(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found"));
        issueStatusRepository.delete(status);
    }

    public void deleteProjectStatus(String name) {
        ProjectStatus status = projectStatusRepository.findById(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found"));
        projectStatusRepository.delete(status);
    }
}
