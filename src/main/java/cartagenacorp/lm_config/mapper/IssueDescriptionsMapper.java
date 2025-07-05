package cartagenacorp.lm_config.mapper;

import cartagenacorp.lm_config.dto.IssueDescriptionsDto;
import cartagenacorp.lm_config.entity.IssueDescriptions;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IssueDescriptionsMapper {
    IssueDescriptions toEntity(IssueDescriptionsDto issueDescriptionsDto);

    IssueDescriptionsDto toDto(IssueDescriptions issueDescriptions);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    IssueDescriptions partialUpdate(IssueDescriptionsDto issueDescriptionsDto, @MappingTarget IssueDescriptions issueDescriptions);
}