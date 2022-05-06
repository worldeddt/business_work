package business.businesswork.vo;

import business.businesswork.enumerate.TaskStatusType;
import lombok.Data;

@Data
public class ModifyTask {
    private Long index;
    private String title;
    private String description;
    private Long sectionId;
    private TaskStatusType status = TaskStatusType.TODO;
    private String memberId;
}

