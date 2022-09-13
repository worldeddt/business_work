package business.businesswork.vo;

import business.businesswork.enumerate.SectionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseSection {
    public CommonResponse result;
    private Long index;
    private String title;
    private String Description = "";
    private Integer projectId;
    private LocalDateTime registerDateTime;
    private LocalDateTime lastModifyDate;
    private SectionStatus sectionStatus;
    private AllTasks taskList;
}
