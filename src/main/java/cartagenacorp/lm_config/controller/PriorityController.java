package cartagenacorp.lm_config.controller;

import cartagenacorp.lm_config.entity.Priority;
import cartagenacorp.lm_config.service.PriorityService;
import cartagenacorp.lm_config.util.RequiresPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/config/priorities")
public class PriorityController {

    private final PriorityService priorityService;

    @Autowired
    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping
    @RequiresPermission({"CONFIG_CRUD"})
    public List<Priority> all() {
        return priorityService.findAll();
    }

    @PostMapping
    @RequiresPermission({"CONFIG_CRUD"})
    public ResponseEntity<?> create(@RequestBody Priority priority) {
        return ResponseEntity.status(HttpStatus.CREATED).body(priorityService.save(priority));
    }

    @DeleteMapping("/{name}")
    @RequiresPermission({"CONFIG_CRUD"})
    public ResponseEntity<?> delete(@PathVariable String name) {
        try {
            priorityService.delete(name);
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
