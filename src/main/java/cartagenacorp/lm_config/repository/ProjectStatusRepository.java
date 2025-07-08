package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    List<ProjectStatus> findAllByOrderByOrderIndexAsc();
}
