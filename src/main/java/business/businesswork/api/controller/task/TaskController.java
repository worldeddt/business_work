package business.businesswork.api.controller.task;

import business.businesswork.service.task.TaskService;
import business.businesswork.vo.ModifyTask;
import business.businesswork.vo.RegisterTask;
import business.businesswork.vo.ResponseTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    private TaskService taskService;

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public void register(@RequestBody RegisterTask registerTask) throws Exception {
        taskService.register(registerTask);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public void delete(@RequestParam("taskIndex") Long taskIndex) throws Exception {
        taskService.delete(taskIndex);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public void update(@RequestBody ModifyTask modifyTask) {
        taskService.update(modifyTask);
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ResponseTask findOne(@RequestParam(required = false, name = "taskIndex") Long taskIndex) {
        return taskService.findById(taskIndex);
    }
}
