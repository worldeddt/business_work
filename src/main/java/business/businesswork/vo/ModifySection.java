package business.businesswork.vo;

import business.businesswork.enumerate.SectionStatus;
import lombok.Data;

@Data
public class ModifySection {
    private Long index;
    private String title;
    private String projectId;
    private SectionStatus sectionStatus;
}
