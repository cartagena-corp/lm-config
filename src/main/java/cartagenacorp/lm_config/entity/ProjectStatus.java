package cartagenacorp.lm_config.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatus {
    @Id
    private String name;
}
