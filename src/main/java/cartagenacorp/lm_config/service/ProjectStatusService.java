package cartagenacorp.lm_config.service;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.entity.ProjectStatus;
import cartagenacorp.lm_config.mapper.NamedIdMapper;
import cartagenacorp.lm_config.repository.ProjectStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectStatusService {

    private final ProjectStatusRepository statusRepository;
    private final NamedIdMapper namedIdMapper;

    @Autowired
    public ProjectStatusService(ProjectStatusRepository statusRepository, NamedIdMapper namedIdMapper) {
        this.statusRepository = statusRepository;
        this.namedIdMapper = namedIdMapper;
    }

    public NamedIdDTO create(String name) {
        ProjectStatus status = new ProjectStatus();
        status.setName(name);
        return namedIdMapper.toDto(statusRepository.save(status));
    }

    public List<NamedIdDTO> getAll() {
        return statusRepository.findAll().stream()
                .map(namedIdMapper::toDto)
                .collect(Collectors.toList());
    }

    public NamedIdDTO update(Long id, String name) {
        ProjectStatus status = statusRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found"));
        status.setName(name);
        return namedIdMapper.toDto(statusRepository.save(status));
    }

    public void delete(Long id) {
        statusRepository.deleteById(id);
    }
}
