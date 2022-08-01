package business.businesswork.api.controller.project;


import business.businesswork.service.project.ProjectService;
import business.businesswork.vo.AllProject;
import business.businesswork.vo.ModifyProject;
import business.businesswork.vo.RegistProject;
import business.businesswork.vo.ResponseProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    @Autowired(required = false)
    private ProjectService projectService;

    @Autowired(required = false)
    private RestTemplateBuilder restTemplateBuilder;

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

    @RequestMapping(value = "/template", method = {RequestMethod.POST})
    public ResponseProject findOne(@RequestParam(required = false, name = "projectId") Long projectId) throws Exception {
        return projectService.findProject(projectId);
    }

    @RequestMapping(value = "/allTemplate")
    public AllProject findAll() throws Exception {
        return projectService.findAll();
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    public ResponseProject findOneTemp(@RequestParam(required = false, name = "projectId") Long projectId) throws Exception {
        RestTemplate restTemplate = restTemplateBuilder.build();

        MultiValueMap<String, Long> parameters = new LinkedMultiValueMap<>();
        parameters.add("projectId", projectId);

        return restTemplate.postForObject("http://localhost:8090/project/template", parameters, ResponseProject.class);
    }
}
