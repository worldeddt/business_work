package business.businesswork.service.project;


import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.enumerate.ProjectStatus;
import business.businesswork.enumerate.SectionStatus;
import business.businesswork.vo.ModifyProject;
import business.businesswork.vo.RegistProject;
import business.businesswork.vo.ResponseProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime datetime = LocalDateTime.parse(this.dateFormatter(now), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Project project = em.find(Project.class, projectId);
            project.setStatus(ProjectStatus.DELETE);
            project.setDeleteDate(datetime);
            em.persist(project);

            List<Section> sections = project.getSections();
            for (Section section : sections) {
                Section section1 = em.find(Section.class, section.getIndex());
                section1.setStatus(SectionStatus.DELETE);
                section1.setDeleteDate(datetime);
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

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime datetime = LocalDateTime.parse(this.dateFormatter(now), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Project project = new Project();
            project.setTitle(registProject.getTitle());
            project.setDescription(registProject.getDescription());
            project.setRegisterDate(datetime);

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

            if (project.getStatus() == ProjectStatus.DELETE) return;

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

    public ResponseProject findById(Long id)
    {
        ResponseProject responseProject = new ResponseProject();
        responseProject.result = 2;

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

            if (project1 == null) return responseProject;

            responseProject.result = 1;
            responseProject.setIndex(project1.getIndex());
            responseProject.setDescription(project1.getDescription());
            responseProject.setTitle(project1.getTitle());

            return responseProject;

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

        return responseProject;
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

    private String dateFormatter(LocalDateTime date)
    {
        return String.format(
                "%04d-%02d-%02d %02d:%02d:%02d",
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getHour(),
                date.getMinute(),
                date.getSecond()
        );
    }

}
