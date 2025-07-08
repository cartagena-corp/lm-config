package cartagenacorp.lm_config.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issue_priority")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuePriority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String color;

    @ManyToOne
    @JsonBackReference
    private ProjectConfig projectConfig;

    private Integer orderIndex;
}
