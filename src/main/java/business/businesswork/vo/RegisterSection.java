package business.businesswork.vo;

import business.businesswork.enumerate.SectionStatus;
import lombok.Data;

@Data
public class RegisterSection {
    private Long index;
    private String title;
    private String Description = "";
    private Long projectId;
    private SectionStatus sectionStatus;
}
