package business.businesswork.domain;

import business.businesswork.enumerate.SectionStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@SqlResultSetMapping(
    name = "sectionMapping",
    columns={
            @ColumnResult(name="section_index", type = Long.class),
            @ColumnResult(name="title", type = String.class),
            @ColumnResult(name="description", type = String.class),
            @ColumnResult(name="status", type = SectionStatus.class),
            @ColumnResult(name="registerdate", type = LocalDateTime.class),
            @ColumnResult(name="lastmodifydate", type = LocalDateTime.class),
            @ColumnResult(name="deletedate", type = LocalDateTime.class),
    }
)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "section_index")
    public Long index;

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
