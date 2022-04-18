package business.businesswork.service.task;

import business.businesswork.config.CommonConfig;
import business.businesswork.domain.Section;
import business.businesswork.domain.Task;
import business.businesswork.enumerate.StatusType;
import business.businesswork.vo.ModifyTask;
import business.businesswork.vo.RegisterTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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
            Date date = new Date();

            LocalDateTime now = LocalDateTime.now();
            String nowDateTime = String.format(
                    "%04d-%02d-%02d %02d:%02d:%02d",
                    now.getYear(),
                    now.getMonthValue(),
                    now.getDayOfMonth(),
                    now.getHour(),
                    now.getMinute(),
                    now.getSecond()
            );

            logger.info("now date : "+date);
            logger.info("now datetime : "+now);
            logger.info("now datetime string : "+nowDateTime);

            Task task = new Task();
            task.setTitle(registerTask.getTitle());
            task.setDescription(registerTask.getDescription());
            task.setStatusType(registerTask.getStatus());
            task.setRegisterDate(date);

            Section section = em.find(Section.class, registerTask.getSectionId());
            section.addTask(task);

            em.persist(task);
            em.persist(section);

            em.flush();
            em.clear();

            Query query =
                    em.createQuery("select t from Task t where t.section.index = :sectionIndex")
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

            Date date = new Date();

//            LocalDateTime now = LocalDateTime.now();
//            String nowDateTime = String.format(
//                    "%04d-%02d-%02d %02d:%02d:%02d",
//                    now.getYear(),
//                    now.getMonthValue(),
//                    now.getDayOfMonth(),
//                    now.getHour(),
//                    now.getMinute(),
//                    now.getSecond()
//            );

            Task task = em.find(Task.class, modifyTask.getIndex());
            Section section = em.find(Section.class, modifyTask.getSectionId());
            task.setSection(section);
            task.setStatusType(modifyTask.getStatus());
            task.setDescription(modifyTask.getDescription());
            task.setLastModifyDate(date);

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
            task.setStatusType(StatusType.DELETE);

            em.persist(task);
            em.flush();
            em.clear();

            Task task1 = em.find(Task.class, taskIndex);

            logger.info("task after delete request = "+ task1.getStatusType());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
