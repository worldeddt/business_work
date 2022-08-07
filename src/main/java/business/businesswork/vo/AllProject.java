package business.businesswork.vo;

import business.businesswork.domain.Project;
import business.businesswork.enumerate.ResponseStatus;
import lombok.Data;

import java.util.List;

@Data
public class AllProject {
    public Integer result = ResponseStatus.FAIL.getResultCode();
    public List<Project> projectList;
}
