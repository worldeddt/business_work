package business.businesswork.vo;

import business.businesswork.domain.Section;
import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseProject {
    public Integer result;
    public Long index;
    public String title;
    public String description;
    public ProjectStatus status;
    public List<Section> sections = new ArrayList<Section>();
}
