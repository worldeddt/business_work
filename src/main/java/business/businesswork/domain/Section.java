package business.businesswork.domain;

import business.businesswork.enumerate.SectionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "business_section")
public class Section extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bs_index")
    public Long index;

    @Column(name = "bs_title", nullable = false)
    public String title;

    @Column(name = "bs_description", columnDefinition = "varchar(1000) default '' ", nullable = false)
    public String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "bs_status", nullable = false)
    public SectionStatus status = SectionStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bp_index")
    public Project project;
}
