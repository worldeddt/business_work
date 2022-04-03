package business.businesswork.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long index;

    public String title;

    public String description;

    @OneToMany(mappedBy = "project")
    List<Section> sections = new ArrayList<Section>();

    public void addSection(Section section) {
        section.setProject(this);
        sections.add(section);
    }
}
