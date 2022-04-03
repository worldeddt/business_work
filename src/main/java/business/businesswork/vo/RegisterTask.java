package business.businesswork.vo;

import business.businesswork.enumerate.StatusType;
import lombok.Data;

@Data
public class RegisterTask {
    private String title;
    private String description;
    private Long sectionId;
    private Long projectId;
    private StatusType status;
    private String memberId;
}
