package business.businesswork.domain;

import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long index;

    @Column(nullable = false)
    public String title;

    public String description = "";

    @Enumerated(EnumType.STRING)
    public ProjectStatus status = ProjectStatus.ACTIVE;

    @OneToMany(mappedBy = "project")
    List<Section> sections = new ArrayList<Section>();

    public void addSection(Section section) {
        section.setProject(this);
        sections.add(section);
    }

    @Column(columnDefinition = "timestamp DEFAULT current_timestamp()", nullable = false)
    public LocalDateTime registerDate;

    public LocalDateTime lastModifyDate;

    public LocalDateTime deleteDate;
}
