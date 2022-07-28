package business.businesswork.dto;

import business.businesswork.domain.Project;
import business.businesswork.enumerate.SectionStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class SectionDto {
    public Long index;

    public String title;

    public String description;

    public SectionStatus status = SectionStatus.ACTIVE;

    public Project project;

    public LocalDateTime registerDate;

    public LocalDateTime lastModifyDate;

    public LocalDateTime deleteDate;
}
