package business.businesswork.vo;

import business.businesswork.domain.Section;
import business.businesswork.enumerate.TaskStatusType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskVO {
    private Long index;
    public String description;
    public String title;
    public Section section;
    public TaskStatusType taskStatusType = TaskStatusType.DELETE;
    public LocalDateTime registerDate;
    public LocalDateTime lastModifyDate;
}
