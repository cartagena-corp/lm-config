package cartagenacorp.lm_config.controller;

import cartagenacorp.lm_config.entity.IssueStatus;
import cartagenacorp.lm_config.entity.ProjectStatus;
import cartagenacorp.lm_config.service.StatusService;
import cartagenacorp.lm_config.util.RequiresPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/config/statuses")
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/issue")
    @RequiresPermission({"CONFIG_CRUD"})
    public List<IssueStatus> allIssueStatuses() {
        return statusService.findAllIssueStatuses();
    }

    @GetMapping("/project")
    @RequiresPermission({"CONFIG_CRUD"})
    public List<ProjectStatus> allProjectStatuses() {
        return statusService.findAllProjectStatuses();
    }

    @PostMapping("/issue")
    @RequiresPermission({"CONFIG_CRUD"})
    public ResponseEntity<?> createIssueStatus(@RequestBody IssueStatus status) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(statusService.saveIssueStatus(status));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/project")
    @RequiresPermission({"CONFIG_CRUD"})
    public ResponseEntity<?> createProjectStatus(@RequestBody ProjectStatus status) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(statusService.saveProjectStatus(status));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/issue/{name}")
    @RequiresPermission({"CONFIG_CRUD"})
    public ResponseEntity<?> deleteIssueStatus(@PathVariable String name) {
        try {
            statusService.deleteIssueStatus(name);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        } catch (DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The resource cannot be deleted because it is being used by other records");
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/project/{name}")
    @RequiresPermission({"CONFIG_CRUD"})
    public ResponseEntity<?> deleteProjectStatus(@PathVariable String name) {
        try {
            statusService.deleteProjectStatus(name);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        } catch (DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The resource cannot be deleted because it is being used by other records");
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
