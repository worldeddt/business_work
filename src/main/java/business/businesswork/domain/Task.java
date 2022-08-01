package business.businesswork.domain;

import business.businesswork.enumerate.TaskStatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "business_task")
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bt_index")
    private Long index;

    @Column(name="bt_description")
    public String description;

    @Column(name="bt_title", nullable = false)
    public String title;

    @ManyToOne
    @JoinColumn(name = "bm_index")
    public Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bs_index")
    public Section section;

    @Enumerated(EnumType.STRING)
    @Column(name="bt_status")
    public TaskStatusType taskStatusType = TaskStatusType.TODO;
}
