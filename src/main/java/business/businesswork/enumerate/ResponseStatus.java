package business.businesswork.enumerate;

public enum ResponseStatus {
    SUCCESS(1, "success"),
    FAIL(2, "fail"),
    SERVER_ERROR(3, "server error"),
    TASK_WAS_DELETE(4, "task was delete"),
    SECTION_WAS_DELETE(8, "section was delete"),
    TASK_SECTION_UPDATE_FAL(5, "section update fail"),
    TASK_UPDATE_FAL(6, "task update fail"),
    SECTION_UPDATE_FAL(9, "section update fail"),
    SECTION_DELETE_FAL(10, "section delete fail"),
    TASK_DELETE_FAL(7, "task delete fail"),
    PROJECT_DELETE_FAIL(11, "project delete fail"),
    PROJECT_WAS_DELETE(12, "project was delete"),
    PROJECT_REGISTER_FAIL(13, "project register fail"),
    SECTION_REGISTER_FAIL(14, "section register fail"),
    TASK_REGISTER_FAIL(15, "task register fail"),
    PROJECT_UPDATE_FAIL(16, "project update fail"),
    PROJECT_IS_NULL(17, "project is null"),
    SECTION_IS_NULL(18, "section is null"),
    TASK_IS_NULL(19, "task is null")
    ;

    private final int resultCode;
    private final String resultMessage;

    ResponseStatus(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
