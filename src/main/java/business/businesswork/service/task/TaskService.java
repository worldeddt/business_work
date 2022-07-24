//package business.businesswork.service.task;
//
//import business.businesswork.domain.Section;
//import business.businesswork.domain.Task;
//import business.businesswork.enumerate.ResponseStatus;
//import business.businesswork.enumerate.TaskStatusType;
//import business.businesswork.vo.ModifyTask;
//import business.businesswork.vo.RegisterTask;
//import business.businesswork.vo.ResponseTask;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@Service
//public class TaskService {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");
//
//    public void register(RegisterTask registerTask)
//    {
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//        try {
//            Section section = em.find(Section.class, registerTask.getSectionId());
//
//            Task task = new Task();
//            task.setTitle(registerTask.getTitle());
//            task.setDescription(registerTask.getDescription());
//            task.setTaskStatusType(registerTask.getStatus());
//            task.setRegisterDate(getThisTime());
//            task.setSection(section);
//            em.persist(task);
//
////            section.addTask(task);
//
//            em.persist(section);
//
//            em.flush();
//            em.clear();
//
//            TypedQuery<Task> query =
//                    em.createQuery("select t from Task t where t.section.index = :sectionIndex", Task.class)
//                            .setParameter("sectionIndex", registerTask.getSectionId());
//
//            logger.info("=========================== registered task = "+query.getResultList());
//
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//
//        emf.close();
//    }
//
//    public void update(ModifyTask modifyTask)
//    {
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//        try {
//            Task task = em.find(Task.class, modifyTask.getIndex());
//            Section section = em.find(Section.class, modifyTask.getSectionId());
//
//            if (task.getTaskStatusType() == TaskStatusType.DELETE) return;
//
//            task.setSection(section);
//            task.setTaskStatusType(modifyTask.getStatus());
//            task.setDescription(modifyTask.getDescription());
//            task.setTitle(modifyTask.getTitle());
//            task.setLastModifyDate(getThisTime());
//
//            em.persist(task);
//
//            em.flush();
//            em.clear();
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//
//        emf.close();
//    }
//
//    public void delete(Long taskIndex)
//    {
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//        try {
//            Task task = em.find(Task.class, taskIndex);
//            task.setTaskStatusType(TaskStatusType.DELETE);
//            task.setDeleteDate(getThisTime());
//
//            em.persist(task);
//            em.flush();
//            em.clear();
//
//            Task task1 = em.find(Task.class, taskIndex);
//
//            logger.info("task after delete request = "+ task1.getTaskStatusType());
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//
//        emf.close();
//    }
//
//    public ResponseTask findById(Long id)
//    {
//        ResponseTask responseTask = new ResponseTask();
//        responseTask.setResult(ResponseStatus.FAIL.getResultCode());
//
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//        try {
//            logger.info("================== section id : "+ id);
//
//            TypedQuery<Task> query =
//                    em.createQuery("select t from Task t where t.index = :index", Task.class)
//                            .setParameter("index", id);
//
//            Task task1 = em.find(Task.class, id);
//            Section section = em.find(Section.class, id);
//            logger.info("====== section's project :"+section.getProject());
//
//            logger.info("====== task1 : "+task1.getSection());
//
//            responseTask.setIndex(task1.getIndex());
//            responseTask.setDescription(task1.getDescription());
//            responseTask.setTitle(task1.getTitle());
//            responseTask.setLastModifyDate(task1.getLastModifyDate());
//            responseTask.setRegisterDate(task1.getRegisterDate());
//            responseTask.setTaskStatusType(task1.getTaskStatusType());
//            responseTask.setResult(ResponseStatus.SUCCESS.getResultCode());
//
//            return responseTask;
//
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//
//        emf.close();
//
//        return responseTask;
//    }
//
//    private LocalDateTime getThisTime()
//    {
//        LocalDateTime now = LocalDateTime.now();
//        return LocalDateTime.parse(this.dateFormatter(now), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//    }
//
//    private String dateFormatter(LocalDateTime date)
//    {
//        return String.format(
//                "%04d-%02d-%02d %02d:%02d:%02d",
//                date.getYear(),
//                date.getMonthValue(),
//                date.getDayOfMonth(),
//                date.getHour(),
//                date.getMinute(),
//                date.getSecond()
//        );
//    }
//}
