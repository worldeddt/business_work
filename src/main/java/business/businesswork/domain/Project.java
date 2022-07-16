package business.businesswork.domain;

import business.businesswork.enumerate.ProjectStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "projectMapping",
    entities = {@EntityResult (entityClass = Project.class,
        fields = {
            @FieldResult(name="index", column="index"),
            @FieldResult(name="description", column="description"),
            @FieldResult(name="title", column="title"),
        }
    )
})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projectIndex")
    private Long index;

    @Column(nullable = false)
    public String title;

    @Column(columnDefinition = "varchar(5000) default ''", nullable = false)
    public String description = "";

    @Enumerated(EnumType.STRING)
    public ProjectStatus status = ProjectStatus.ACTIVE;

//    @OneToMany(mappedBy = "project")
//    private List<Section> sections = new ArrayList<>();

//    public void addSection(Section section) {
//        section.setProject(this);
//        sections.add(section);
//    }

    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", nullable = false)
    public LocalDateTime registerDate;

    public LocalDateTime lastModifyDate;

    public LocalDateTime deleteDate;

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

//    public List<Section> getSections() {
//        return sections;
//    }
//
//    public void setSections(List<Section> sections) {
//        this.sections = sections;
//    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDateTime getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(LocalDateTime lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public LocalDateTime getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }
}
