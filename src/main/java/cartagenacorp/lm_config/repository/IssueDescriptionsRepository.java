package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.IssueDescriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueDescriptionsRepository extends JpaRepository<IssueDescriptions, Long> {
    List<IssueDescriptions> findByProjectConfig_ProjectIdOrderByOrderIndexAsc(UUID projectId);
}