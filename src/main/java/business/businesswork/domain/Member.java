//package business.businesswork.domain;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
////@Entity
//public class Member {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "member_index")
//    private Long index;
//    public String name;
//    public String kakaoId;
//    public String affiliation; //소속
//
//    @OneToMany(mappedBy = "member")
//    List<Task> Tasks = new ArrayList<Task>();
//
//    @OneToMany(mappedBy = "member")
//    List<Review> reviewList = new ArrayList<Review>();
//
//    public List<Review> getReviewList() {
//        return reviewList;
//    }
//
//    public void addTask(Task task) {
//        task.setMember(this);
//        Tasks.add(task);
//    }
//
//    public void addReview(Review review) {
//        review.setMember(this);
//        reviewList.add(review);
//    }
//
//    public void setReviewList(List<Review> reviewList) {
//        this.reviewList = reviewList;
//    }
//
//    public List<Task> getTasks() {
//        return Tasks;
//    }
//
//    public void setTasks(List<Task> tasks) {
//        Tasks = tasks;
//    }
//
//    public Long getIndex() {
//        return index;
//    }
//
//    public void setIndex(Long index) {
//        this.index = index;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getKakaoId() {
//        return kakaoId;
//    }
//
//    public void setKakaoId(String kakaoId) {
//        this.kakaoId = kakaoId;
//    }
//
//    public String getAffiliation() {
//        return affiliation;
//    }
//
//    public void setAffiliation(String affiliation) {
//        this.affiliation = affiliation;
//    }
//}
