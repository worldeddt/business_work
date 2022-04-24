package business.businesswork.service.task;

import business.businesswork.domain.Section;
import business.businesswork.domain.Task;
import business.businesswork.enumerate.TaskStatusType;
import business.businesswork.vo.ModifyTask;
import business.businesswork.vo.RegisterTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    public void register(RegisterTask registerTask)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime datetime = LocalDateTime.parse(this.dateFormatter(now), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Section section = em.find(Section.class, registerTask.getSectionId());

            Task task = new Task();
            task.setTitle(registerTask.getTitle());
            task.setDescription(registerTask.getDescription());
            task.setTaskStatusType(registerTask.getStatus());
            task.setRegisterDate(datetime);
            task.setSection(section);
            em.persist(task);

            section.addTask(task);

            em.persist(section);

            em.flush();
            em.clear();

            TypedQuery<Task> query =
                    em.createQuery("select t from Task t where t.section.index = :sectionIndex", Task.class)
                            .setParameter("sectionIndex", registerTask.getSectionId());

            logger.info("=========================== registered task = "+query.getResultList());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public void update(ModifyTask modifyTask)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime datetime = LocalDateTime.parse(this.dateFormatter(now), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Task task = em.find(Task.class, modifyTask.getIndex());
            Section section = em.find(Section.class, modifyTask.getSectionId());

            task.setSection(section);
            task.setTaskStatusType(modifyTask.getStatus());
            task.setDescription(modifyTask.getDescription());
            task.setTitle(modifyTask.getTitle());
            task.setLastModifyDate(datetime);

            em.persist(modifyTask);

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

    public void delete(String taskIndex)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Task task = em.find(Task.class, taskIndex);
            task.setTaskStatusType(TaskStatusType.DELETE);

            em.persist(task);
            em.flush();
            em.clear();

            Task task1 = em.find(Task.class, taskIndex);

            logger.info("task after delete request = "+ task1.getTaskStatusType());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public Optional<Task> findById(Long id)
    {
        Task task = new Task();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            logger.info("================== section id : "+ id);

            TypedQuery<Task> query =
                    em.createQuery("select t from Task t where t.index = :index and t.taskStatusType not in (:status)", Task.class)
                            .setParameter("index", id)
                            .setParameter("status", TaskStatusType.DELETE);

            Task task1 = query.getSingleResult();

            task.setIndex(task1.getIndex());
            task.setDescription(task1.getDescription());
            task.setTitle(task1.getTitle());

            return Optional.of(task);

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

        return Optional.of(task);
    }

    public List<Task> findAll()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            return em.createQuery("select t from Task t where t.taskStatusType not in (:status)", Task.class)
                    .setParameter("status", TaskStatusType.DELETE)
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
