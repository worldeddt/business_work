package business.businesswork.api.controller;


import business.businesswork.domain.Project;
import business.businesswork.vo.RegistProject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@RestController
@RequestMapping(name = "project")
public class ProjectController {

    @RequestMapping(name = "regist")
    public void regist(@RequestBody RegistProject registProject) throws Exception {
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

    @RequestMapping(name = "update")
    public void update() throws Exception {

    }

    @RequestMapping(name = "delete")
    public void delete(@RequestBody String projectId) throws Exception {

        try {

        } catch (Exception e) {

        } finally {

        }
        projectId
    }

}
