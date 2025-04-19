package cartagenacorp.lm_config.controller;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.service.ProjectStatusService;
import cartagenacorp.lm_config.util.RequiresPermission;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config/project-statuses")
public class ProjectStatusController {

     private final ProjectStatusService service;

    @Autowired
    public ProjectStatusController(ProjectStatusService service) {
        this.service = service;
    }

    @PostMapping
    @RequiresPermission({"CONFIG_CRUD"})
    public ResponseEntity<NamedIdDTO> create(@RequestBody @Valid NamedIdDTO namedIdDTO) {
        return new ResponseEntity<>(service.create(namedIdDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public List<NamedIdDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    @RequiresPermission({"CONFIG_CRUD"})
    public NamedIdDTO update(@PathVariable Long id, @RequestBody @Valid NamedIdDTO namedIdDTO) {
        return service.update(id, namedIdDTO);
    }

    @DeleteMapping("/{id}")
    @RequiresPermission({"CONFIG_CRUD"})
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
