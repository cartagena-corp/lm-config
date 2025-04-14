package cartagenacorp.lm_config.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issue_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueStatus {
    @Id
    private String name;
}

