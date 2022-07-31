package business.businesswork.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_index")
    private Long index;

    @Column(name = "member_name", nullable = false)
    public String name;

    @Column(name = "member_kakao_id", nullable = true, columnDefinition = "varchar(200) default '' ")
    public String kakaoId;

    @Column
    public String affiliation;
}
