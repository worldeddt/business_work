package business.businesswork.vo;

import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class ResponseProject {
    public Integer result;
    public Long index;
    public String title;
    public String description;
    public ProjectStatus status;
    public AllSections sectionList;
}
