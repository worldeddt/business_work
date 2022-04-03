package business.businesswork.domain;

import business.businesswork.enumerate.StatusType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Column(columnDefinition = "datetime default NOW()")
    @Temporal(TemporalType.TIMESTAMP)
    public Date registerDate;

    @Temporal(TemporalType.TIMESTAMP)
    public Date lastModifyDate;

    public Task() {

    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}
