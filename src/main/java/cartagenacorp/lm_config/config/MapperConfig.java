package cartagenacorp.lm_config.config;

import cartagenacorp.lm_config.mapper.NamedIdMapper;
import cartagenacorp.lm_config.mapper.ProjectConfigMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ProjectConfigMapper projectConfigMapper() {
        return Mappers.getMapper(ProjectConfigMapper.class);
    }

    @Bean
    public NamedIdMapper namedIdMapper() {
        return Mappers.getMapper(NamedIdMapper.class);
    }
}
