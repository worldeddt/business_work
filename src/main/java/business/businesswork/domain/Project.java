package business.businesswork.domain;

import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "business_project")
public class Project extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bp_index")
    public Integer index;

    @Column(name = "bp_title", nullable = false)
    public String title;

    @Column(name = "bp_description", columnDefinition = "varchar(5000) default ''", nullable = false)
    public String description = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "bp_status")
    public ProjectStatus status = ProjectStatus.DELETE;
}
