package business.businesswork.api.controller.project;


import business.businesswork.domain.Project;
import business.businesswork.vo.RegistProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/register")
    public void register(@RequestBody RegistProject registProject) throws Exception {
        logger.info("============================= registProject"+registProject);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");
        EntityManager em = emf.createEntityManager();
        EntityTransaction ex = em.getTransaction();
        ex.begin();

        try {
            Project project = new Project();
            project.setTitle(registProject.getTitle());
            project.setDescription(registProject.getDescription());

            em.flush();
            em.close();


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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
