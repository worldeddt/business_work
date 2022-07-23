package business.businesswork.domain;

import business.businesswork.enumerate.SectionStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "section_index")
    public UUID index;

    @Column(nullable = false)
    public String title;

    @Column(columnDefinition = "varchar(1000) default '' ", nullable = false)
    public String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public SectionStatus status = SectionStatus.ACTIVE;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_index")
    public Project project;

//    @OneToMany(mappedBy = "section")
//    private List<Task> tasks = new ArrayList<>();

    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", nullable = false)
    public LocalDateTime registerDate;

    public LocalDateTime lastModifyDate;

    public LocalDateTime deleteDate;

//    public void addTask(Task task) {
//        task.setSection(this);
//        tasks.add(task);
//    }
}
