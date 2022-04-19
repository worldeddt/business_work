package business.businesswork.api.controller.project;


import business.businesswork.domain.Project;
import business.businesswork.enumerate.ProjectStatus;
import business.businesswork.service.project.ProjectService;
import business.businesswork.vo.ModifyProject;
import business.businesswork.vo.RegistProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    @Autowired(required = false)
    private ProjectService projectService;

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public void register(@RequestBody RegistProject registProject) throws Exception {
        projectService.register(registProject);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public void update(@RequestBody ModifyProject modifyProject) throws Exception {
        projectService.update(modifyProject);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public void delete(@RequestParam(required = false ,name = "projectId") Long projectId) throws Exception {
        projectService.deleteProject(projectId);
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public void findOne(@RequestParam(required = false, name = "projectId") Long projectId) throws Exception {
        projectService.findById(projectId);
    }
}
