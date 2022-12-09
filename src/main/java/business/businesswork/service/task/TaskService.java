package business.businesswork.service.task;

import business.businesswork.domain.Section;
import business.businesswork.domain.Task;
import business.businesswork.enumerate.ResponseStatus;
import business.businesswork.enumerate.TaskStatusType;
import business.businesswork.exceptions.BusinessException;
import business.businesswork.vo.CommonResponse;
import business.businesswork.vo.ModifyTask;
import business.businesswork.vo.RegisterTask;
import business.businesswork.vo.ResponseTask;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TaskService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    public CommonResponse register(RegisterTask registerTask)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        Gson gson = new Gson();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            String queryString = "insert into business_task(bt_title, bt_description, bs_index, bt_status, register_date)" +
                    "values(:title, :desc, :bIndex, :status, :register)";
            Query nativeQuery = em.createNativeQuery(queryString)
                    .setParameter("title", registerTask.getTitle())
                    .setParameter("desc", registerTask.getDescription())
                    .setParameter("bIndex", registerTask.getSectionId())
                    .setParameter("status", registerTask.getStatus().toString())
                    .setParameter("register", getThisTime());
            nativeQuery.executeUpdate();

//            Section section = gson.fromJson(gson.toJson(em.find(Section.class, registerTask.getSectionId())), Section.class);
//            Task task = new Task();
//            task.setTitle(registerTask.getTitle());
//            task.setDescription(registerTask.getDescription());
//            task.setTaskStatusType(registerTask.getStatus());
//            task.setRegisterDate(getThisTime());
//            task.setSection(section);
//            em.persist(task);

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("register task exception error : "+e);
            commonResponse.setResponse(ResponseStatus.SERVER_ERROR);
            tx.rollback();
        } finally {
            em.close();
        }

        return commonResponse;
    }

    public CommonResponse updateSection(ModifyTask modifyTask)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        EntityManager em = emf.createEntityManager();
        Gson gson = new Gson();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            String queryString =
                    "update business_task set bs_index = '"+modifyTask.getSectionId()+"' " +
                            "where bt_index = '"+modifyTask.getIndex()+"';";
            em.createNativeQuery(queryString).executeUpdate();

//            Task task3 = gson.fromJson(gson.toJson(em.find(Task.class, modifyTask.getIndex())),Task.class);
//
//            if (!task3.getSection().getIndex().equals(modifyTask.getSectionId()))
//                throw new BusinessException(ResponseStatus.TASK_SECTION_UPDATE_FAL);

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("update task exception error : "+e);
            tx.rollback();
            commonResponse.setResponse(ResponseStatus.SERVER_ERROR);
            if (e instanceof BusinessException) {
                commonResponse.setResult(((BusinessException) e).getResultCode());
                commonResponse.setMessage(((BusinessException) e).getReason());
            }
        } finally {
            em.close();
        }

        return commonResponse;
    }

    public CommonResponse update(ModifyTask modifyTask)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        EntityManager em = emf.createEntityManager();
        Gson gson = new Gson();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Task task = gson.fromJson(gson.toJson(em.find(Task.class, modifyTask.getIndex())),Task.class);
//            Section section = gson.fromJson(gson.toJson(em.find(Section.class, modifyTask.getSectionId())), Section.class);

//            if (Objects.equals(task.getTaskStatusType().toString(), "DELETE"))
//                throw new BusinessException(ResponseStatus.TASK_WAS_DELETE);

            String queryString =
                "update business_task set " +
                    "bt_description = :desc, " +
                    "bt_title = :title, " +
                    "last_modify_date = :lastModify, " +
                    "bt_status = :status " +
                    "where bt_index = :index";

            Query query = em.createNativeQuery(queryString)
                .setParameter("desc", modifyTask.getDescription())
                .setParameter("title", modifyTask.getTitle())
                .setParameter("status", modifyTask.getStatus().toString())
                .setParameter("index", modifyTask.getIndex())
                .setParameter("lastModify", this.getThisTime());

            query.executeUpdate();
//            task.setSection(section);
//            task.setTaskStatusType(modifyTask.getStatus());
//            task.setDescription(modifyTask.getDescription());
//            task.setTitle(modifyTask.getTitle());
//            task.setLastModifyDate(getThisTime());
//            em.merge(task);
//
//            em.flush();
//            em.clear();

//            Task task1 = gson.fromJson(gson.toJson(em.find(Task.class, modifyTask.getIndex())),Task.class);

//            if (!task1.getSection().getIndex().equals(task.getSection().getIndex()))
//                throw new BusinessException(ResponseStatus.TASK_SECTION_UPDATE_FAL);

//            if
//            (
//                !task1.getTaskStatusType().equals(task.getTaskStatusType()) ||
//                !task1.getTitle().equals(task.getTitle()) ||
//                !task1.getDescription().equals(task.getDescription())
//            )
//                throw new BusinessException(ResponseStatus.TASK_UPDATE_FAL);

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("update task exception error : "+e);
            tx.rollback();
            commonResponse.setResponse(ResponseStatus.SERVER_ERROR);
            if (e instanceof BusinessException) {
                commonResponse.setResult(((BusinessException) e).getResultCode());
                commonResponse.setMessage(((BusinessException) e).getReason());
            }
        } finally {
            em.close();
        }

        return commonResponse;
    }

    public CommonResponse delete(Long taskIndex)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        EntityManager em = emf.createEntityManager();
        Gson gson = new Gson();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            String queryString = "update business_task set bt_status = '"+TaskStatusType.DELETE+"' where bt_index = "+taskIndex;

            Query nativeQuery = em.createNativeQuery(queryString);
            nativeQuery.executeUpdate();

//            Task task = gson.fromJson(gson.toJson(em.find(Task.class, taskIndex)), Task.class);
//            task.setTaskStatusType(TaskStatusType.DELETE);
//            task.setDeleteDate(getThisTime());
//            em.merge(task);
//
//            em.flush();
//            em.clear();
//
//            Task task1 = gson.fromJson(gson.toJson(em.find(Task.class, taskIndex)), Task.class);

//            if (!task.getTaskStatusType().equals(task1.getTaskStatusType()))
//                throw new BusinessException(ResponseStatus.TASK_DELETE_FAL);

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("delete task exception error : "+e);
            tx.rollback();
            commonResponse.setResponse(ResponseStatus.SERVER_ERROR);
            if (e instanceof BusinessException) {
                commonResponse.setResult(((BusinessException) e).getResultCode());
                commonResponse.setMessage(((BusinessException) e).getReason());
            }
        } finally {
            em.close();
        }

        return commonResponse;
    }

    public ResponseTask findById(Long id)
    {
        ResponseTask responseTask = new ResponseTask();
        responseTask.setResult(ResponseStatus.FAIL.getResultCode());

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            logger.info("================== section id : "+ id);

            TypedQuery<Task> query =
                    em.createQuery("select t from Task t where t.index = :index", Task.class)
                            .setParameter("index", id);

            Task task1 = em.find(Task.class, id);
            Section section = em.find(Section.class, id);

            responseTask.setIndex(task1.getIndex());
            responseTask.setDescription(task1.getDescription());
            responseTask.setTitle(task1.getTitle());
            responseTask.setLastModifyDate(task1.getLastModifyDate());
            responseTask.setRegisterDate(task1.getRegisterDate());
            responseTask.setTaskStatusType(task1.getTaskStatusType());
            responseTask.setResult(ResponseStatus.SUCCESS.getResultCode());

            return responseTask;

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        return responseTask;
    }

    private LocalDateTime getThisTime()
    {
        LocalDateTime now = LocalDateTime.now();
        return LocalDateTime.parse(this.dateFormatter(now), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
