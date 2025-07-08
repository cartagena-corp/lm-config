package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueStatusRepository extends JpaRepository<IssueStatus, Long> {
    List<IssueStatus> findByProjectConfig_ProjectIdOrderByOrderIndexAsc(UUID projectId);
}
