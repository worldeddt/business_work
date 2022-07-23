package business.businesswork.vo;

import business.businesswork.enumerate.ProjectStatus;

import java.util.UUID;

public class ResponseProject {
    public Integer result;
    public UUID index;
    public String title;
    public String description;
    public ProjectStatus status;
    public AllSections sectionList;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public UUID getIndex() {
        return index;
    }

    public void setIndex(UUID index) {
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

    public AllSections getSectionList() {
        return sectionList;
    }

    public void setSectionList(AllSections sectionList) {
        this.sectionList = sectionList;
    }
}
