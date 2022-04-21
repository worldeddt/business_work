package business.businesswork.service.project;


import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.enumerate.ProjectStatus;
import business.businesswork.enumerate.SectionStatus;
import business.businesswork.vo.ModifyProject;
import business.businesswork.vo.RegistProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    public void deleteProject(Long projectId)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = em.find(Project.class, projectId);
            project.setStatus(ProjectStatus.DELETE);
            em.persist(project);

            List<Section> sections = project.getSections();
            for (Section section : sections) {
                Section section1 = em.find(Section.class, section.getIndex());
                section1.setStatus(SectionStatus.DELETE);
                em.persist(section1);
            }

            em.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public void register(RegistProject registProject)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = new Project();
            project.setTitle(registProject.getTitle());
            project.setDescription(registProject.getDescription());
            em.persist(project);
            em.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public void update(ModifyProject modifyProject)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = em.find(Project.class, modifyProject.getIndex());
            project.setTitle(modifyProject.getTitle());
            project.setDescription(modifyProject.getDescription());

            em.persist(project);
            em.flush();
            em.clear();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public Optional<Project> findById(Long id)
    {
        Project project = new Project();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            logger.info("================== project id : "+ id);

            TypedQuery<Project> query =
                    em.createQuery("select p from Project p where p.index = :index and p.status = :status", Project.class)
                            .setParameter("index", id)
                            .setParameter("status", ProjectStatus.ACTIVE);

            Project project1 = query.getSingleResult();

            project.setIndex(project1.getIndex());
            project.setDescription(project1.getDescription());
            project.setTitle(project1.getTitle());
            project.setSections(project1.getSections());

            return Optional.of(project);

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

        return Optional.of(project);
    }

    public List<Project> findAll()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            return em.createQuery("select p from Project p where p.status = :status", Project.class)
                    .setParameter("status", ProjectStatus.ACTIVE)
                    .getResultList();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

        return null;
    }
}
