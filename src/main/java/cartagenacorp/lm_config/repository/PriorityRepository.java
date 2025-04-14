package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, String> {
}
