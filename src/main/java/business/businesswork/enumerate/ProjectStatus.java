package business.businesswork.enumerate;

public enum ProjectStatus {
    DELETE("DELETE"),
    ACTIVE("ACTIVE");

    private String val;

    ProjectStatus(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
