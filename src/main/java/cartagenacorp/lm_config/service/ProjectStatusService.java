package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.ProjectStatus;
import cartagenacorp.lm_config.mapper.NamedIdMapper;
import cartagenacorp.lm_config.repository.ProjectStatusRepository;
import cartagenacorp.lm_config.util.JwtContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectStatusService {

    private final ProjectStatusRepository statusRepository;
    private final NamedIdMapper namedIdMapper;

    public ProjectStatusService(ProjectStatusRepository statusRepository, NamedIdMapper namedIdMapper) {
        this.statusRepository = statusRepository;
        this.namedIdMapper = namedIdMapper;
    }

    public NamedIdDTO create(NamedIdDTO namedIdDTO) {
        ProjectStatus status = namedIdMapper.toEntityProjectStatus(namedIdDTO);
        status.setOrganizationId(JwtContextHolder.getOrganizationId());
        return namedIdMapper.toDto(statusRepository.save(status));
    }

    public List<NamedIdDTO> getAll() {
        return statusRepository.findAllByOrganizationIdOrderByOrderIndexAsc(JwtContextHolder.getOrganizationId()).stream()
                .map(namedIdMapper::toDto)
                .collect(Collectors.toList());
    }

    public NamedIdDTO update(Long id, NamedIdDTO namedIdDTO) {
        ProjectStatus status = statusRepository.findByIdAndOrganizationId(id, JwtContextHolder.getOrganizationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found"));
        namedIdMapper.partialUpdateProjectStatus(namedIdDTO, status);
        return namedIdMapper.toDto(statusRepository.save(status));
    }

    public void delete(Long id) {
        ProjectStatus status = statusRepository.findByIdAndOrganizationId(id, JwtContextHolder.getOrganizationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found"));
        statusRepository.delete(status);
    }

    @Transactional
    public void initializeStatuses(UUID organizationId) {
        List<ProjectStatus> existing = statusRepository.findByOrganizationId(organizationId);
        if (existing.isEmpty()) {
            List<ProjectStatus> defaultStatuses = Arrays.asList(
                    new ProjectStatus(null, "Inactivo", "#3f3f46", 1, organizationId),
                    new ProjectStatus(null, "En progreso", "#0069a8", 2, organizationId),
                    new ProjectStatus(null, "Completado", "#008236", 3, organizationId)
            );
            statusRepository.saveAll(defaultStatuses);
        }
    }

    @Transactional
    public void deleteByOrganizationId(UUID organizationId) {
        List<ProjectStatus> existing = statusRepository.findByOrganizationId(organizationId);
        if (!existing.isEmpty()) {
            statusRepository.deleteByOrganizationId(organizationId);
        }
    }
}
