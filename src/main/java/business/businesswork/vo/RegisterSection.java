package business.businesswork.vo;

import business.businesswork.enumerate.SectionStatus;
import lombok.Data;

@Data
public class RegisterSection {
    private Long index;
    private String title;
    private String description;
    private Integer projectId;
    private SectionStatus sectionStatus = SectionStatus.ACTIVE;
}
