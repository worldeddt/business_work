package business.businesswork.vo;


import business.businesswork.enumerate.ResponseStatus;
import lombok.Data;

@Data
public class CommonResponse {
    public int result = ResponseStatus.FAIL.getResultCode();

    public String message = ResponseStatus.FAIL.getResultMessage();

    public CommonResponse(ResponseStatus responseStatus) {
        if (responseStatus != null) {
            this.result = responseStatus.getResultCode();
            this.message = responseStatus.getResultMessage();
        }
    }

    public void setResponse(ResponseStatus responseStatus) {
        this.result = responseStatus.getResultCode();
        this.message = responseStatus.getResultMessage();
    }
}
