package business.businesswork.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long index;

    public String title;

    @ManyToOne
    @JoinColumn(name = "projectId")
    public Project project;

    @OneToMany(mappedBy = "section")
    List<Task> tasks = new ArrayList<Task>();

    public void addTask(Task task) {
        task.setSection(this);
        tasks.add(task);
    }
}
