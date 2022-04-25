package business.businesswork.vo;

import business.businesswork.domain.Section;
import business.businesswork.enumerate.ProjectStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ResponseProject {
    public Integer result;
    public Long index;
    public String title;
    public String description;
    public ProjectStatus status;
    public List<Section> sections = new ArrayList<Section>();

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
