package cartagenacorp.lm_config.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProjectConfigDTO {
    private UUID projectId;
    private List<NamedIdDTO> issueStatuses;
    private List<NamedIdDTO> issuePriorities;
    private List<NamedIdDTO> issueTypes;
}
