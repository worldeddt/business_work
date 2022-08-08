package business.businesswork.vo;

import business.businesswork.enumerate.SectionStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SectionVO {
    private Long index;
    private String title;
    private String Description = "";
    private LocalDateTime registerDateTime;
    private LocalDateTime lastModifyDate;
    private SectionStatus sectionStatus;
    private List<TaskVO> taskList;
}
