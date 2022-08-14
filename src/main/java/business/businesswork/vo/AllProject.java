package business.businesswork.vo;

import business.businesswork.domain.Project;
import business.businesswork.enumerate.ResponseStatus;
import lombok.Data;

import java.util.List;

@Data
public class AllProject {
    public CommonResponse commonResponse;
    public List<Project> projectList;
}
