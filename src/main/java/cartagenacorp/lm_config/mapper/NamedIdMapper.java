package cartagenacorp.lm_config.mapper;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.IssuePriority;
import cartagenacorp.lm_config.entity.IssueStatus;
import cartagenacorp.lm_config.entity.IssueType;
import cartagenacorp.lm_config.entity.ProjectStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NamedIdMapper {

    NamedIdDTO toDto(IssuePriority issuePriority);

    NamedIdDTO toDto(IssueStatus issueStatus);

    NamedIdDTO toDto(IssueType issueType);

    NamedIdDTO toDto(ProjectStatus projectStatus);
}
