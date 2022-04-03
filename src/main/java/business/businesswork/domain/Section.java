package business.businesswork.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long index;

    public String name;

    @ManyToOne
    @JoinColumn(name = "projectId")
    public Project project;

    @OneToMany(mappedBy = "section")
    List<Task> tasks = new ArrayList<Task>();

    public void addTask(Task task) {
        task.setSection(this);
        tasks.add(task);
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
