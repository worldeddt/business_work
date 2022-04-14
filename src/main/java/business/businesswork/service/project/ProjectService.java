package business.businesswork.service.project;


import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.enumerate.ProjectStatus;
import business.businesswork.enumerate.SectionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@Service
public class ProjectService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    public void deleteProject(String projectId)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = em.find(Project.class, projectId);
            project.setStatus(ProjectStatus.DELETE);

            List<Section> sections = project.getSections();

            for (Section section : sections) {
                (em.find(Section.class, section.getIndex())).setStatus(SectionStatus.DELETE);
            }

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
