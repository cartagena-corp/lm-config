package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueStatusRepository extends JpaRepository<IssueStatus, String> {
}
