package business.businesswork.vo;

import business.businesswork.enumerate.ProjectStatus;
import business.businesswork.enumerate.ResponseStatus;
import lombok.Data;

import java.util.List;

@Data
public class ResponseProject {
    public Integer result = ResponseStatus.FAIL.getResultCode();
    public Long index;
    public String title;
    public String description;
    public ProjectStatus status;
    public List<SectionVO> sectionList;
}
