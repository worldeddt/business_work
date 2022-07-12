package business.businesswork.enumerate;

public enum ProjectStatus {
    DELETE("DELETE"),
    ACTIVE("ACTIVE");

    private String projectStatus;

    ProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectStatus() {
        return projectStatus;
    }
}
