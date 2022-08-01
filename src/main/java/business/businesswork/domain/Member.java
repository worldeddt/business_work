package business.businesswork.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "business member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bm_index")
    private Long index;

    @Column(name = "bm_name", nullable = false)
    public String name;

    @Column(name = "bm_kakao_id", columnDefinition = "varchar(200) default '' ")
    public String kakaoId;

    @Column(name = "bm_affiliation")
    public String affiliation;
}
