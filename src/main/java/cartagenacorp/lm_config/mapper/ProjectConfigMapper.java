package cartagenacorp.lm_config.mapper;

import cartagenacorp.lm_config.dto.ProjectConfigDTO;
import cartagenacorp.lm_config.entity.ProjectConfig;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectConfigMapper {

    ProjectConfig toEntity(ProjectConfigDTO projectConfigDTO);

    @AfterMapping
    default void linkIssueStatuses(@MappingTarget ProjectConfig projectConfig) {
        projectConfig.getIssueStatuses().forEach(issueStatus -> issueStatus.setProjectConfig(projectConfig));
    }

    @AfterMapping
    default void linkIssuePriorities(@MappingTarget ProjectConfig projectConfig) {
        projectConfig.getIssuePriorities().forEach(issuePriority -> issuePriority.setProjectConfig(projectConfig));
    }

    @AfterMapping
    default void linkIssueTypes(@MappingTarget ProjectConfig projectConfig) {
        projectConfig.getIssueTypes().forEach(issueType -> issueType.setProjectConfig(projectConfig));
    }

//    @Mapping(source = "issueStatuses", target = "issueStatuses")
//    @Mapping(source = "issuePriorities", target = "issuePriorities")
//    @Mapping(source = "issueTypes", target = "issueTypes")
    ProjectConfigDTO toDto(ProjectConfig projectConfig);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectConfig partialUpdate(ProjectConfigDTO projectConfigDTO, @MappingTarget ProjectConfig projectConfig);

}