package business.businesswork.vo;


import business.businesswork.enumerate.ResponseStatus;
import lombok.Data;

@Data
public class CommonResponse {
    public int result = ResponseStatus.FAIL.getResultCode();

    public String message = "";

    public CommonResponse() {}
}
