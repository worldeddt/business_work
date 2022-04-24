package business.businesswork.vo;


import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;

@Data
public class ModifyProject {
    public Long index;
    public String title;
    public String description;
    public ProjectStatus status = ProjectStatus.ACTIVE;
}
