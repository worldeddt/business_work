package business.businesswork.enumerate;

public enum SectionStatus {
    DELETE("DELETE"),
    ACTIVE("ACTIVE");

    private String sectionStatus;

    SectionStatus(String projectStatus) {
        this.sectionStatus = sectionStatus;
    }

    public String getSectionStatus() {
        return sectionStatus;
    }
}
