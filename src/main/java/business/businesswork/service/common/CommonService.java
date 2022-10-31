package business.businesswork.service.common;

import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.domain.Task;
import business.businesswork.enumerate.ProjectStatus;
import business.businesswork.enumerate.ResponseStatus;
import business.businesswork.enumerate.SectionStatus;
import business.businesswork.enumerate.TaskStatusType;
import business.businesswork.exceptions.BusinessException;
import business.businesswork.vo.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    public AllSections findSectionByProjectId(Integer projectId, EntityManager em)
    {
        AllSections responseSection = new AllSections();
        Gson gson = new Gson();

        try {
            String queryString =
                    "select * from business_section where bp_index = '" + projectId + "' and bs_status = '" + SectionStatus.ACTIVE + "';";

            List sectionQueryList = em.createNativeQuery(queryString, Section.class)
                    .getResultList();

            ArrayList<SectionVO> sectionList = new ArrayList<>();

            for (Object section3 : sectionQueryList) {
                Section section1 = gson.fromJson(gson.toJson(section3), Section.class);
                SectionVO section2 = new SectionVO();
                section2.setIndex(section1.getIndex());
                section2.setDescription(section1.getDescription());
                section2.setRegisterDateTime(section1.getRegisterDate());
                section2.setLastModifyDate(section1.getLastModifyDate());
                section2.setTitle(section1.getTitle());
                section2.setSectionStatus(section1.getStatus());

                if (section1.getStatus() != SectionStatus.DELETE ) {
                    AllTasks allTasks  = this.findTasksBySectionId(section1.getIndex(), em);

                    if (allTasks.getResult() == ResponseStatus.SUCCESS.getResultCode())
                        section2.setTaskList(allTasks.getTaskList());

                    sectionList.add(section2);
                }
            }

            responseSection.setSectionList(sectionList);
            responseSection.setResult(ResponseStatus.SUCCESS.getResultCode());

        } catch (Exception e) {
            this.logger.error("findSectionByProjectId error : "+e);
        }

        return responseSection;
    }

    public AllTasks findTasksBySectionId(Long sectionId, EntityManager em)
    {
        AllTasks responseTask = new AllTasks();
        Gson gson = new Gson();

        try {
            String queryString =
                    "select * from business_task where bs_index = '" + sectionId + "' and bt_status <> '" + TaskStatusType.DELETE + "';";

            List taskQueryList = em.createNativeQuery(queryString, Task.class).getResultList();

            ArrayList<TaskVO> taskList = new ArrayList<>();

            for (Object task3 : taskQueryList) {
                Task task1 = gson.fromJson(gson.toJson(task3), Task.class);
                TaskVO task2 = new TaskVO();
                task2.setIndex(task1.getIndex());
                task2.setDescription(task1.getDescription());
                task2.setRegisterDate(task1.getRegisterDate());
                task2.setLastModifyDate(task1.getLastModifyDate());
                task2.setTitle(task1.getTitle());
                task2.setTaskStatusType(task1.getTaskStatusType());

                if (task2.getTaskStatusType() != TaskStatusType.DELETE) taskList.add(task2);
            }

            responseTask.setTaskList(taskList);
            responseTask.setResult(ResponseStatus.SUCCESS.getResultCode());

        } catch (Exception e) {
            logger.error("findTasksBySectionId error : "+e);
        }

        return responseTask;
    }

    public ResponseProject findProjectById(Integer projectId)
    {
        EntityManager em = emf.createEntityManager();

        CommonResponse commonResponse = new CommonResponse(null);
        ResponseProject responseProject = new ResponseProject();
        Gson gson = new Gson();

        try {
            responseProject.setCommonResponse(commonResponse);

            Project project = gson.fromJson(gson.toJson(em.find(Project.class, projectId)), Project.class);

            if ((project.getStatus() == ProjectStatus.DELETE) || project.getStatus() == null) return responseProject;

            responseProject.setTitle(project.getTitle());
            responseProject.setDescription(project.getDescription());
            responseProject.setStatus(project.getStatus());
            responseProject.setIndex(project.getIndex());
            responseProject.setRegisterDateTime(project.getRegisterDate());
            responseProject.setLastModifyDate(project.getLastModifyDate());
            commonResponse.setResponse(ResponseStatus.SUCCESS);
            responseProject.setCommonResponse(commonResponse);

        } catch (Exception e) {
            logger.error("findProject exception error : "+e);
            commonResponse.setResponse(ResponseStatus.SERVER_ERROR);
            responseProject.setCommonResponse(commonResponse);
        } finally {
            em.close();
        }

        return responseProject;
    }

    public CommonResponse ProjectDelete(Integer projectId)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        CommonResponse commonResponse = new CommonResponse(null);

        tx.begin();
        try {

            String queryString =
                    "update business_project set bp_status = :status,  delete_date = :deleteTime " +
                            "where bp_index = :index";

            em.createNativeQuery(queryString)
                    .setParameter("status", ProjectStatus.DELETE.getProjectStatus())
                    .setParameter("deleteTime", this.getThisTime())
                    .setParameter("index", projectId)
                    .executeUpdate();

            String queryString1 =
                    "select bp_status from business_project where bp_index = :index";

            Query query1 = em.createNativeQuery(queryString1)
                    .setParameter("index", projectId);

            if (!Objects.equals(query1.getSingleResult(),ProjectStatus.DELETE.getProjectStatus()))
                throw new BusinessException(ResponseStatus.PROJECT_DELETE_FAIL);

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("delete project exception error : "+e.getMessage());
            commonResponse.setResponse(ResponseStatus.SERVER_ERROR);
            if (e instanceof BusinessException) {
                commonResponse.setResult(((BusinessException) e).getResultCode());
                commonResponse.setMessage(((BusinessException) e).getReason());
            }
            tx.rollback();
        } finally {
            em.close();
        }

        return commonResponse;
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
