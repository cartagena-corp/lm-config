package cartagenacorp.lm_config.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link cartagenacorp.lm_config.entity.IssueDescriptions}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDescriptionsDto implements Serializable {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private Integer orderIndex;
}