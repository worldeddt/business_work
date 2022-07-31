package business.businesswork.vo;

import business.businesswork.domain.Task;
import lombok.Data;

import java.util.List;

@Data
public class AllTasks {
    public Integer result;
    public List<Task> taskList;
}
