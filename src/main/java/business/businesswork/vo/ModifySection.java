package business.businesswork.vo;

import lombok.Data;

@Data
public class ModifySection {
    private Long index;
    private String title;
    private Integer projectId;
    private String description;
}
