package business.businesswork.domain;

import business.businesswork.enumerate.TaskStatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "business_task")
public class Task extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bt_index", nullable = false)
    public Long index;

    @Column(name="bt_description")
    public String description;

    @Column(name="bt_title", nullable = false)
    public String title;

    @ManyToOne
    @JoinColumn(name = "bm_index")
    public Member member;

    @ManyToOne
    @JoinColumn(name = "bs_index")
    public Section section;

    @Enumerated(EnumType.STRING)
    @Column(name="bt_status")
    public TaskStatusType taskStatusType = TaskStatusType.DELETE;
}
