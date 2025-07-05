package cartagenacorp.lm_config.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "project_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID projectId;

    @OneToMany(mappedBy = "projectConfig", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<IssueStatus> issueStatuses = new ArrayList<>();

    @OneToMany(mappedBy = "projectConfig", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<IssuePriority> issuePriorities = new ArrayList<>();

    @OneToMany(mappedBy = "projectConfig", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<IssueType> issueTypes = new ArrayList<>();

    @OneToMany(mappedBy = "projectConfig", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SprintStatus> sprintStatuses = new ArrayList<>();

    @OneToMany(mappedBy = "projectConfig", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<IssueDescriptions> issueDescriptions = new ArrayList<>();

}
