package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.IssuePriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Long> {
    List<IssuePriority> findByProjectConfig_ProjectIdOrderByOrderIndexAsc(UUID projectId);
}
