package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.SprintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SprintStatusRepository extends JpaRepository<SprintStatus, Long> {
    List<SprintStatus> findByProjectConfig_ProjectIdOrderByOrderIndexAsc(UUID projectId);
}