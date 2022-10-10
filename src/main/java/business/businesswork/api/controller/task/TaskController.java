package business.businesswork.api.controller.task;

import business.businesswork.service.task.TaskService;
import business.businesswork.vo.CommonResponse;
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
    @Autowired(required = false)
    private TaskService taskService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public CommonResponse register(@RequestBody RegisterTask registerTask) throws Exception {
        return taskService.register(registerTask);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public CommonResponse delete(@RequestParam("taskIndex") Long taskIndex) throws Exception {
        return taskService.delete(taskIndex);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public CommonResponse update(@RequestBody ModifyTask modifyTask) throws Exception {
        return taskService.update(modifyTask);
    }

    @RequestMapping(value="/updateSection", method = {RequestMethod.POST})
    public CommonResponse updateSection(@RequestBody ModifyTask modifyTask) throws Exception {
        return taskService.updateSection(modifyTask);
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ResponseTask findOne(@RequestParam(required = false, name = "taskIndex") Long taskIndex) {
        return taskService.findById(taskIndex);
    }
}
