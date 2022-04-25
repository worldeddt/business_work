package business.businesswork.vo;

import lombok.Data;

@Data
public class ModifySection {
    private Long index;
    private String title;
    private Long projectId;
    private String description;
}
