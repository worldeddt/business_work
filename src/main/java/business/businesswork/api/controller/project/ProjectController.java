package business.businesswork.api.controller.project;


import business.businesswork.domain.Project;
import business.businesswork.enumerate.ProjectStatus;
import business.businesswork.service.project.ProjectService;
import business.businesswork.vo.RegistProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");
    private ProjectService projectService;

    @RequestMapping(value = "/register")
    public void register(@RequestBody RegistProject registProject) throws Exception {
        logger.info("============================= registProject"+registProject);

        EntityManager em = emf.createEntityManager();
        EntityTransaction ex = em.getTransaction();
        ex.begin();

        try {
            Project project = new Project();
            project.setTitle(registProject.getTitle());
            project.setDescription(registProject.getDescription());
            em.persist(project);

            em.flush();
            em.clear();
            ex.commit();

        } catch (Exception e) {
            ex.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

//    @RequestMapping(value = "/update")
//    public void update() throws Exception {
//
//    }

    @RequestMapping(value = "/delete")
    public void delete(@RequestBody String projectId) throws Exception {
        projectService.deleteProject(projectId);
    }
}
