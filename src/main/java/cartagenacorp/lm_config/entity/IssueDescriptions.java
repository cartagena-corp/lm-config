package cartagenacorp.lm_config.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issue_descriptions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDescriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JsonBackReference
    private ProjectConfig projectConfig;
}
