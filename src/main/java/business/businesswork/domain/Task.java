package business.businesswork.domain;

import business.businesswork.enumerate.TaskStatusType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;

    @Column(name="descriptionName")
    public String description;

    public String title;

    @OneToMany(mappedBy = "task")
    List<Review> reviewList = new ArrayList<Review>();

    public void addReivew(Review review) {
        review.setTask(this);
        reviewList.add(review);
    }

    @ManyToOne
    @JoinColumn(name = "memberId")
    public Member member;

    @ManyToOne
    @JoinColumn(name = "sectionId")
    public Section section;

    @Enumerated(EnumType.STRING)
    public TaskStatusType taskStatusType;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date registerDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date lastModifyDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date deleteDate;
}
