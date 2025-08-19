package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.ProjectConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectConfigRepository extends JpaRepository<ProjectConfig, Long> {
    Optional<ProjectConfig> findByProjectId(UUID projectId);

    void deleteByProjectId(UUID projectId);
}