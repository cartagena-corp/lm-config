package cartagenacorp.lm_config.controller;

import cartagenacorp.lm_config.dto.NamedIdDTO;
import cartagenacorp.lm_config.service.IssueStatusService;
import cartagenacorp.lm_config.util.RequiresPermission;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/config/issue-statuses")
public class IssueStatusController {

     private final IssueStatusService service;

    public IssueStatusController(IssueStatusService service) {
        this.service = service;
    }

    @PostMapping("/{projectId}")
    @RequiresPermission({"CONFIG_ISSUE"})
    public ResponseEntity<NamedIdDTO> create(@PathVariable UUID projectId, @RequestBody @Valid NamedIdDTO namedIdDTO) {
        return new ResponseEntity<>(service.create(projectId, namedIdDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    @RequiresPermission({"CONFIG_READ"})
    public ResponseEntity<List<NamedIdDTO>> getAll(@PathVariable UUID projectId) {
        return ResponseEntity.ok(service.getAll(projectId));
    }

    @PutMapping("/{id}")
    @RequiresPermission({"CONFIG_ISSUE"})
    public ResponseEntity<NamedIdDTO> update(@PathVariable Long id, @RequestBody @Valid NamedIdDTO namedIdDTO) {
        return ResponseEntity.ok(service.update(id, namedIdDTO));
    }

    @DeleteMapping("/{id}")
    @RequiresPermission({"CONFIG_DELETE"})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
