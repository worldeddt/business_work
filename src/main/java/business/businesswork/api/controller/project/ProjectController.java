package business.businesswork.api.controller.project;


import business.businesswork.service.project.ProjectService;
import business.businesswork.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired(required = false)
    private ProjectService projectService;

    @Autowired(required = false)
    private RestTemplateBuilder restTemplateBuilder;

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public CommonResponse register(@RequestBody RegistProject registProject) throws Exception {
        return projectService.register(registProject);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public CommonResponse update(@RequestBody ModifyProject modifyProject) throws Exception {
        return projectService.update(modifyProject);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public CommonResponse delete(@RequestParam(required = false ,name = "projectId") Integer projectId) throws Exception {
        return projectService.deleteProject(projectId);
    }

    @RequestMapping(value = "/template", method = {RequestMethod.POST})
    public ResponseProject findOne(@RequestParam(required = false, name = "projectId") Integer projectId) throws Exception {
        return projectService.findProject(projectId);
    }

    @RequestMapping(value = "/allTemplate")
    public AllProject findAll() throws Exception {
        return projectService.findAll();
    }

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    public ResponseProject findOneTemp(@RequestParam(required = false, name = "projectId") Integer projectId) throws Exception {
        RestTemplate restTemplate = restTemplateBuilder.build();

        MultiValueMap<String, Integer> parameters = new LinkedMultiValueMap<>();
        parameters.add("projectId", projectId);

        return restTemplate.postForObject("http://localhost:8090/project/template", parameters, ResponseProject.class);
    }
}
