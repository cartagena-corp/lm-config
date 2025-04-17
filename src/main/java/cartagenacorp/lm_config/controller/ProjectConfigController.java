package cartagenacorp.lm_config.controller;

import cartagenacorp.lm_config.dto.ProjectConfigDTO;
import cartagenacorp.lm_config.entity.ProjectConfig;
import cartagenacorp.lm_config.service.ProjectConfigService;
import cartagenacorp.lm_config.util.RequiresPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/config")
public class ProjectConfigController {

    private final ProjectConfigService configService;

    @Autowired
    public ProjectConfigController(ProjectConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/{projectId}")
    @RequiresPermission({"CONFIG_CRUD", "CONFIG_READ"})
    public ResponseEntity<ProjectConfig> getOrCreate(@PathVariable UUID projectId) {
        ProjectConfig config = configService.getOrCreateConfig(projectId);
        return new ResponseEntity<>(config, HttpStatus.OK);
    }
}
