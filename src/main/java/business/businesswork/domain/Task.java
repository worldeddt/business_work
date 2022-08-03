package business.businesswork.domain;

import business.businesswork.enumerate.TaskStatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name="bt_assign")
    public Long bm_index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bs_index")
    public Section section;

    @OneToMany(mappedBy = "task")
    public List<Review> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name="bt_status")
    public TaskStatusType taskStatusType = TaskStatusType.TODO;

    public void addReviews(Review review) {
        review.setTask(this);
        reviews.add(review);
    }
}
