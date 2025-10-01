package cartagenacorp.lm_config.controller;

import cartagenacorp.lm_config.entity.ProjectConfig;
import cartagenacorp.lm_config.service.ProjectConfigService;
import cartagenacorp.lm_config.service.ProjectStatusService;
import cartagenacorp.lm_config.util.RequiresPermission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/config")
public class ProjectConfigController {

    private final ProjectConfigService configService;
    private final ProjectStatusService projectStatusService;

    public ProjectConfigController(ProjectConfigService configService, ProjectStatusService projectStatusService) {
        this.configService = configService;
        this.projectStatusService = projectStatusService;
    }

    @PostMapping("/{projectId}")
    @RequiresPermission({"CONFIG_READ", "PROJECT_CREATE"}) //se usa desde lm-projects (uso interno) o desde frontend para obtener config de proyectos
    public ResponseEntity<ProjectConfig> getOrCreate(@PathVariable UUID projectId) {
        ProjectConfig config = configService.getOrCreateConfig(projectId);
        return new ResponseEntity<>(config, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    @RequiresPermission({"CONFIG_DELETE", "PROJECT_DELETE"}) //se usa desde lm-projects (uso interno)
    public ResponseEntity<Void> deleteConfig(@PathVariable UUID projectId) {
        configService.deleteConfigByProjectId(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/initialize/{organizationId}") //se usa desde lm-organization (uso interno)
    @RequiresPermission({"ORGANIZATION_CONTROL", "ORGANIZATION_CREATE"})
    public ResponseEntity<Void> initializeStatusesForOrganizationId(@PathVariable UUID organizationId) {
        projectStatusService.initializeStatuses(organizationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/organization/{organizationId}") //se usa desde lm-organization (uso interno)
    @RequiresPermission({"ORGANIZATION_CONTROL", "ORGANIZATION_DELETE"})
    public ResponseEntity<Void> deleteStatusesForOrganizationId(@PathVariable UUID organizationId) {
        projectStatusService.deleteByOrganizationId(organizationId);
        return ResponseEntity.ok().build();
    }
}
