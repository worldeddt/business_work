package business.businesswork.vo;

import business.businesswork.enumerate.SectionStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ResponseSection {
    public Integer result;
    private UUID index;
    private String title;
    private String Description = "";
    private UUID projectId;
    private LocalDateTime registerDateTime;
    private LocalDateTime lastModifyDate;
    private SectionStatus sectionStatus;
//    private AllTasks taskList;
}
