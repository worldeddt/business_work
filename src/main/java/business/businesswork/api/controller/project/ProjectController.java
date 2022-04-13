package business.businesswork.api.controller.project;


import business.businesswork.domain.Project;
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
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

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
            em.close();

            logger.info("=============================ㅇㅇ=====");
            TypedQuery<Project> query = em.createQuery("SELECT count(p) FROM Project p ORDER BY p.index DESC", Project.class);
            logger.info("============================= ㅁㅁ====="+query.getResultList());
            logger.info("============================= ㅋㅋ====="+query.getSingleResult());

            System.out.println("=========project list======"+query.getResultList());

            ex.commit();

        } catch (Exception e) {
            ex.rollback();
        } finally {
//            em.close();
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
            Project project = em.find(Project.class, projectId);
            em.persist(project);

            em.remove(project);
            em.flush();
            em.clear();

            Project project1 = em.createQuery("select p from Project p where p.index = :index", Project.class)
                    .setParameter("index", projectId)
                    .getSingleResult();

            logger.info("project1______"+project1);


        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
