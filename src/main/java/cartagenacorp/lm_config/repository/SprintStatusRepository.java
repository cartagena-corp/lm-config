package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.SprintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintStatusRepository extends JpaRepository<SprintStatus, Long> {
}