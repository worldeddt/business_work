package business.businesswork.domain;

import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "business_project")
public class Project extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bp_index")
    private Long index;

    @Column(name = "bp_title", nullable = false)
    public String title;

    @Column(name = "bp_description", columnDefinition = "varchar(5000) default ''", nullable = false)
    public String description = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "bp_status")
    public ProjectStatus status = ProjectStatus.DELETE;

    public Project() {
        super();
    }
}
