package business.businesswork.domain;

import business.businesswork.enumerate.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@SqlResultSetMapping(name = "projectMapping",
    entities = {@EntityResult (entityClass = Project.class,
        fields = {
            @FieldResult(name="index", column="project_index"),
            @FieldResult(name="description", column="description"),
            @FieldResult(name="title", column="title"),
            @FieldResult(name="deletedate", column="deletedate"),
            @FieldResult(name="lastmodifydate", column="lastmodifydate"),
            @FieldResult(name="registerdate", column="registerdate"),
            @FieldResult(name="status", column="status"),
        }
    )
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_index")
    private Long index;

    @Column(nullable = false)
    public String title;

    @Column(columnDefinition = "varchar(5000) default ''", nullable = false)
    public String description = "";

    @Enumerated(EnumType.STRING)
    public ProjectStatus status = ProjectStatus.ACTIVE;

    @JsonManagedReference
    @OneToMany(mappedBy = "project")
    private List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        section.setProject(this);
        sections.add(section);
    }

    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", nullable = false)
    public LocalDateTime registerDate;

    public LocalDateTime lastModifyDate;

    public LocalDateTime deleteDate;

}
