package business.businesswork.vo;

import business.businesswork.domain.Project;
import lombok.Data;

import java.util.List;

@Data
public class AllProject {
    public Integer result;
    public List<Project> projectList;
}
