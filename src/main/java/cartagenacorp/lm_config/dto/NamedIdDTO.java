package cartagenacorp.lm_config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NamedIdDTO {
    private Long id;
    private String name;
}
