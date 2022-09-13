package business.businesswork.vo;

import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseProject {
    public CommonResponse commonResponse;
    public Integer index;
    public String title;
    public String description;
    public ProjectStatus status;
    private LocalDateTime registerDateTime;
    private LocalDateTime lastModifyDate;
    private LocalDateTime deleteDate;
    public List<SectionVO> sectionList;
}
