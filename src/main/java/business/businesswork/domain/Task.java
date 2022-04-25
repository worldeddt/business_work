package business.businesswork.domain;

import business.businesswork.enumerate.TaskStatusType;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    public String title;

    @OneToMany(mappedBy = "task")
    List<Review> reviewList = new ArrayList<Review>();

    public void addReivew(Review review) {
        review.setTask(this);
        reviewList.add(review);
    }

    @ManyToOne
    @JoinColumn(name = "member_index")
    public Member member;

    @ManyToOne
    @JoinColumn(name = "section_index")
    public Section section;

    @Enumerated(EnumType.STRING)
    public TaskStatusType taskStatusType = TaskStatusType.TODO;

    @Column(columnDefinition = "datetime DEFAULT ''", nullable = false)
    public LocalDateTime registerDate;

    @Column(columnDefinition = "datetime DEFAULT ''")
    public LocalDateTime lastModifyDate;

    @Column(columnDefinition = "datetime DEFAULT ''")
    public LocalDateTime deleteDate;

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
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

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
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

    public TaskStatusType getTaskStatusType() {
        return taskStatusType;
    }

    public void setTaskStatusType(TaskStatusType taskStatusType) {
        this.taskStatusType = taskStatusType;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDateTime getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(LocalDateTime lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public LocalDateTime getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }
}
