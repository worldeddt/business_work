package business.businesswork.enumerate;

public enum ResponseStatus {
    SUCCESS(1),
    FAIL(2),
    SERVER_ERROR(3);

    private int resultCode;

    ResponseStatus(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }
}
