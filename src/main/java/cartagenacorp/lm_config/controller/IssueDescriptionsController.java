package cartagenacorp.lm_config.controller;

import cartagenacorp.lm_config.dto.IssueDescriptionsDto;
import cartagenacorp.lm_config.service.IssueDescriptionsService;
import cartagenacorp.lm_config.util.RequiresPermission;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/config/issue-descriptions")
public class IssueDescriptionsController {

    private final IssueDescriptionsService issueDescriptionsService;

    public IssueDescriptionsController(IssueDescriptionsService issueDescriptionsService) {
        this.issueDescriptionsService = issueDescriptionsService;
    }

    @PostMapping("/{projectId}")
    @RequiresPermission({"CONFIG_ISSUE"})
    public ResponseEntity<IssueDescriptionsDto> create(@PathVariable UUID projectId, @RequestBody @Valid IssueDescriptionsDto issueDescriptionsDto) {
        return new ResponseEntity<>(issueDescriptionsService.create(projectId, issueDescriptionsDto), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    @RequiresPermission({"CONFIG_READ"})
    public ResponseEntity<List<IssueDescriptionsDto>> getAll(@PathVariable UUID projectId) {
        return new ResponseEntity<>(issueDescriptionsService.getAll(projectId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @RequiresPermission({"CONFIG_ISSUE"})
    public ResponseEntity<IssueDescriptionsDto> update(@PathVariable Long id, @RequestBody @Valid IssueDescriptionsDto issueDescriptionsDto) {
        return new ResponseEntity<>(issueDescriptionsService.update(id, issueDescriptionsDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @RequiresPermission({"CONFIG_DELETE"})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        issueDescriptionsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
