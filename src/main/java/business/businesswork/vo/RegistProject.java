package business.businesswork.vo;

import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;

@Data
public class RegistProject {
    public String title;
    public String description;
    public ProjectStatus projectStatus = ProjectStatus.ACTIVE;
}
