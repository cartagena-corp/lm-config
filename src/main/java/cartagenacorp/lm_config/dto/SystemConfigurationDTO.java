package cartagenacorp.lm_config.dto;

import cartagenacorp.lm_config.entity.IssueStatus;
import cartagenacorp.lm_config.entity.Priority;
import cartagenacorp.lm_config.entity.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfigurationDTO {
    private List<IssueStatus> issueStatuses;
    private List<ProjectStatus> projectStatuses;
    private List<Priority> priorities;
}
