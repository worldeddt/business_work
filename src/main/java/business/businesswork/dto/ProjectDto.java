package business.businesswork.dto;

import business.businesswork.domain.Section;
import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDto {
    private Long index;

    public String title;

    public String description = "";

    public ProjectStatus status = ProjectStatus.ACTIVE;

    private List<Section> sections = new ArrayList<>();

    public LocalDateTime registerDate;

    public LocalDateTime lastModifyDate;

    public LocalDateTime deleteDate;
}
