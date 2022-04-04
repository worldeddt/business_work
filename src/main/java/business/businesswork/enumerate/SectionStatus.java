package business.businesswork.enumerate;

public enum SectionStatus {

    DELETE("DELETE"),
    ACTIVE("ACTIVE");


    private String val;

    SectionStatus(String val) {
        this.val = val;
    }


    public String getVal() {
        return val;
    }
}
