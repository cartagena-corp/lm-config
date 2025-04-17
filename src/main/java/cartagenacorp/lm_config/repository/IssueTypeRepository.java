package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, Long> {
    List<IssueType> findByProjectConfig_ProjectId(UUID projectId);
}