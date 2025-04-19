package cartagenacorp.lm_config.mapper;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.IssuePriority;
import cartagenacorp.lm_config.entity.IssueStatus;
import cartagenacorp.lm_config.entity.IssueType;
import cartagenacorp.lm_config.entity.ProjectStatus;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NamedIdMapper {

    NamedIdDTO toDto(IssuePriority issuePriority);

    NamedIdDTO toDto(IssueStatus issueStatus);

    NamedIdDTO toDto(IssueType issueType);

    NamedIdDTO toDto(ProjectStatus projectStatus);

    IssuePriority toEntityIssuePriority(NamedIdDTO namedIdDTO);

    IssueStatus toEntityIssueStatus(NamedIdDTO namedIdDTO);

    IssueType toEntityIssueType(NamedIdDTO namedIdDTO);

    ProjectStatus toEntityProjectStatus(NamedIdDTO namedIdDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateIssuePriority(NamedIdDTO namedIdDTO, @MappingTarget IssuePriority issuePriority);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateIssueStatus(NamedIdDTO namedIdDTO, @MappingTarget IssueStatus issueStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateIssueType(NamedIdDTO namedIdDTO, @MappingTarget IssueType issueType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateProjectStatus(NamedIdDTO namedIdDTO, @MappingTarget ProjectStatus projectStatus);
}
