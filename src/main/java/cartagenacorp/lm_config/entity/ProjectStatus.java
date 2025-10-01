package cartagenacorp.lm_config.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "project_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String color;

    private Integer orderIndex;

    @Column(name = "organization_id")
    private UUID organizationId;
}
