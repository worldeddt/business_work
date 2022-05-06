package business.businesswork.vo;

import business.businesswork.domain.Section;
import business.businesswork.enumerate.TaskStatusType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseTask {
    public Integer result;
    private Long index;
    public String description;
    public String title;
    public Section section;
    public TaskStatusType taskStatusType;
    public LocalDateTime registerDate;
    public LocalDateTime lastModifyDate;
}
