package business.businesswork.domain;

import business.businesswork.enumerate.StatusType;
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

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

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
    public StatusType statusType;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date registerDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date lastModifyDate;
}
