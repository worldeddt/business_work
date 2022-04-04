package business.businesswork.api.controller.section;

import business.businesswork.config.CommonConfig;
import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.vo.ModifySection;
import business.businesswork.vo.RegisterSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private CommonConfig commonConfig;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterSection registerSection) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(commonConfig.getPersistenceName());
        EntityManager em =  entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Section section = new Section();
            section.setTitle(registerSection.getTitle());
            em.persist(section);

            Project project = em.find(Project.class, registerSection.getProjectId());
            project.addSection(section);

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        entityManagerFactory.close();
    }

    @ResponseBody
    @RequestMapping(value =  "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam("sectionIndex") String index) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(commonConfig.getPersistenceName());
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Section section = em.find(Section.class, index);
            em.remove(section);

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    @ResponseBody
    @RequestMapping(value =  "/update")
    public void update(@RequestParam ModifySection modifySection) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(commonConfig.getPersistenceName());
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {



        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
