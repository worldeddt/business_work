package business.businesswork.exceptions;

import business.businesswork.enumerate.ResponseStatus;
import business.businesswork.vo.CommonResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends Exception {
    private int resultCode;
    private String reason;

    public BusinessException(ResponseStatus responseStatus) {
        super(String.format(
                "resultCode: %s, resultMessage: %s", responseStatus.getResultCode(), responseStatus.getResultMessage()));
        this.resultCode = responseStatus.getResultCode();
        this.reason = responseStatus.getResultMessage();
    }
}
