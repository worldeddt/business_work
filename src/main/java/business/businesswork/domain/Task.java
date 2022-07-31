package business.businesswork.domain;

import business.businesswork.enumerate.TaskStatusType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_index")
    private Long index;

    @Column(name="descriptionName")
    public String description;

    @Column(nullable = false)
    public String title;

    @ManyToOne
    @JoinColumn(name = "member_index")
    public Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_index")
    public Section section;

    @Enumerated(EnumType.STRING)
    public TaskStatusType taskStatusType = TaskStatusType.TODO;

    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", nullable = false)
    public LocalDateTime registerDate;

    @Column(columnDefinition = "timestamp")
    public LocalDateTime lastModifyDate;

    @Column(columnDefinition = "timestamp")
    public LocalDateTime deleteDate;
}
