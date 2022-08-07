package business.businesswork.vo;

import business.businesswork.domain.Task;
import business.businesswork.enumerate.ResponseStatus;
import lombok.Data;

import java.util.List;

@Data
public class AllTasks {
    public Integer result = ResponseStatus.FAIL.getResultCode();
    public List<TaskVO> taskList;
}
