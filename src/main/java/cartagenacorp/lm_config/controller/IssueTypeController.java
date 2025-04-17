package cartagenacorp.lm_config.controller;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.service.IssueTypeService;
import cartagenacorp.lm_config.util.RequiresPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/config/issue-types")
public class IssueTypeController {

     private final IssueTypeService service;

    @Autowired
    public IssueTypeController(IssueTypeService service) {
        this.service = service;
    }

    @PostMapping("/{projectId}")
    @RequiresPermission({"CONFIG_CRUD"})
    public ResponseEntity<NamedIdDTO> create(@PathVariable UUID projectId, @RequestBody NamedIdDTO namedIdDTO) {
        return new ResponseEntity<>(service.create(projectId, namedIdDTO.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public List<NamedIdDTO> getAll(@PathVariable UUID projectId) {
        return service.getAll(projectId);
    }

    @PutMapping("/{id}")
    @RequiresPermission({"CONFIG_CRUD"})
    public NamedIdDTO update(@PathVariable Long id, @RequestBody NamedIdDTO namedIdDTO) {
        return service.update(id, namedIdDTO.getName());
    }

    @DeleteMapping("/{id}")
    @RequiresPermission({"CONFIG_CRUD"})
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
