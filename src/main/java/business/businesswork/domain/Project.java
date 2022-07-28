package business.businesswork.domain;

import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@SqlResultSetMapping(
    name = "projectMapping",
    columns = {
            @ColumnResult(name="project_index", type = Long.class),
            @ColumnResult(name="title", type = String.class),
            @ColumnResult(name="description", type = String.class),
            @ColumnResult(name="status", type = ProjectStatus.class),
            @ColumnResult(name="registerDate", type = LocalDateTime.class),
            @ColumnResult(name="lastModifyDate", type = LocalDateTime.class),
            @ColumnResult(name="deleteDate", type = LocalDateTime.class)
    },
    entities={
//        @EntityResult(
//            entityClass = business.businesswork.domain.Section.class,
//            fields = {
//                @FieldResult(name="project_index", column="project_index"),
//                @FieldResult(name = "section_index", column = "section_index"),
//                @FieldResult(name="title", column="title"),
//                @FieldResult(name="description", column="description"),
//                @FieldResult(name="status", column="status"),
//                @FieldResult(name="registerdate", column="registerdate"),
//                @FieldResult(name="lastmodifydate", column="lastmodifydate"),
//                @FieldResult(name="deletedate", column="deletedate"),
//            }
//        ),
        @EntityResult(
            entityClass = business.businesswork.domain.Project.class,
            fields={
                @FieldResult(name="project_index",column="project_index"),
                @FieldResult(name="title", column="title"),
                @FieldResult(name="description", column="description"),
                @FieldResult(name="status", column="status"),
                @FieldResult(name="registerdate", column="registerdate"),
                @FieldResult(name="lastmodifydate", column="lastmodifydate"),
                @FieldResult(name="deletedate", column="deletedate"),
            }
        )
    }
)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_index")
    private Long index;

    @Column(nullable = false)
    public String title;

    @Column(columnDefinition = "varchar(5000) default ''", nullable = false)
    public String description = "";

    @Enumerated(EnumType.STRING)
    public ProjectStatus status = ProjectStatus.ACTIVE;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "project")
//    private Set<Section> sections;

//    public void addSection(Section section) {
//        section.setProject(this);
//        sections.add(section);
//    }

    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", nullable = false)
    public LocalDateTime registerDate;

    public LocalDateTime lastModifyDate;

    public LocalDateTime deleteDate;

}
