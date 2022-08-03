package business.businesswork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "business_review")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="br_index")
    public Long index;

    @Column(name="br_opiinion", columnDefinition = "varchar(500) default '' ", nullable = false)
    public String reviewOpinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bm_index")
    public Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bt_index")
    public Task task;
}
