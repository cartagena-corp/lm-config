package cartagenacorp.lm_config.repository;

import cartagenacorp.lm_config.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    List<ProjectStatus> findAllByOrganizationIdOrderByOrderIndexAsc(UUID organizationId);

    List<ProjectStatus> findByOrganizationId(UUID organizationId);

    Optional<ProjectStatus> findByIdAndOrganizationId(Long id, UUID organizationId);

    @Modifying
    @Query("DELETE FROM ProjectStatus r WHERE r.organizationId = :organizationId")
    void deleteByOrganizationId(UUID organizationId);
}
