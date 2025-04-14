package cartagenacorp.lm_config.controller;

import cartagenacorp.lm_config.dto.SystemConfigurationDTO;
import cartagenacorp.lm_config.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping
    public SystemConfigurationDTO getConfiguration() {
        return configurationService.getConfiguration();
    }
}
