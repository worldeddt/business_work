package business.businesswork.service.project;

import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.domain.Task;
import business.businesswork.enumerate.ProjectStatus;
import business.businesswork.enumerate.ResponseStatus;
import business.businesswork.enumerate.SectionStatus;
import business.businesswork.enumerate.TaskStatusType;
import business.businesswork.exceptions.BusinessException;
import business.businesswork.service.common.CommonService;
import business.businesswork.vo.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    @Autowired(required = false)
    private CommonService commonService;

    public CommonResponse deleteProject(Integer projectId)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        EntityManager em = emf.createEntityManager();
        Gson gson = new Gson();
        EntityTransaction tx = em.getTransaction();
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

            AllSections allSections = commonService.findSectionByProjectId(projectId, em);

            if (allSections.getResult() == ResponseStatus.SUCCESS.getResultCode()) {
                for (SectionVO sectionVo : allSections.getSectionList()) {
                    Section section1 = gson.fromJson(gson.toJson(em.find(Section.class, sectionVo.getIndex())), Section.class);

                    if (section1 == null) continue;

                    String sectionQueryString = "update business_section set bs_status = :status where bs_index = :index";

                    em.createNativeQuery(sectionQueryString)
                            .setParameter("status", SectionStatus.DELETE.getSectionStatus())
                            .setParameter("index", sectionVo.getIndex())
                            .executeUpdate();

                    if (sectionVo.getTaskList().size() == 0) continue;

                    for (TaskVO taskVO : sectionVo.getTaskList()) {
                        String taskQueryString = "update business_task set bt_status = :status where bt_index = :index";

//                        Task task1 = gson.fromJson(gson.toJson(em.find(Task.class, taskVO.getIndex())), Task.class);

                        em.createNativeQuery(taskQueryString)
                                .setParameter("status", SectionStatus.DELETE.getSectionStatus())
                                .setParameter("index", sectionVo.getIndex())
                                .executeUpdate();
                    }
                }
            }

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("delete project exception error : "+e.getMessage());
            tx.rollback();
            commonResponse.setResponse(ResponseStatus.SERVER_ERROR);
            if (e instanceof BusinessException) {
                commonResponse.setResult(((BusinessException) e).getResultCode());
                commonResponse.setMessage(((BusinessException) e).getReason());
            }
        }

        return commonResponse;
    }

    public CommonResponse register(RegistProject registProject)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Gson gson = new Gson();
        tx.begin();

        try {
            String queryString = "insert into business_project(bp_title, bp_description, bp_status, register_date)" +
                    "values(:title, :desc, :status, :register)";
            Query nativeQuery = em.createNativeQuery(queryString)
                    .setParameter("title", registProject.getTitle())
                    .setParameter("desc", registProject.getDescription())
                    .setParameter("status", ProjectStatus.ACTIVE.getProjectStatus())
                    .setParameter("register", this.getThisTime());
            nativeQuery.executeUpdate();

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("register project exception error : "+e);
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

    public CommonResponse update(ModifyProject modifyProject)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        EntityManager em = emf.createEntityManager();
        Gson gson = new Gson();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = gson.fromJson(gson.toJson(em.find(Project.class, modifyProject.getIndex())), Project.class);

            if (project == null) throw new BusinessException(ResponseStatus.PROJECT_IS_NULL);

            if (project.getStatus().equals(ProjectStatus.DELETE))
                throw new BusinessException(ResponseStatus.PROJECT_WAS_DELETE);

            project.setTitle(modifyProject.getTitle());
            project.setDescription(modifyProject.getDescription());
            project.setLastModifyDate(this.getThisTime());
            em.merge(project);

            em.flush();
            em.clear();

            Project project1 = gson.fromJson(gson.toJson(em.find(Project.class, modifyProject.getIndex())), Project.class);

            if (
                !project.getDescription().equals(project1.getDescription()) ||
                !project.getTitle().equals(project1.getTitle())
            )
                throw new BusinessException(ResponseStatus.PROJECT_UPDATE_FAIL);

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("update project exception error : "+e);
            commonResponse.setResponse(ResponseStatus.SERVER_ERROR);
            tx.rollback();
            if (e instanceof BusinessException) {
                commonResponse.setResult(((BusinessException) e).getResultCode());
                commonResponse.setMessage(((BusinessException) e).getReason());
            }
        } finally {
            em.close();
        }

        return commonResponse;
    }

    public ResponseProject findProject(Integer projectId)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        ResponseProject responseProject = new ResponseProject();

        Gson gson = new Gson();
        EntityManager em = emf.createEntityManager();
        try {
            responseProject.setCommonResponse(commonResponse);

            Project project = gson.fromJson(gson.toJson(em.find(Project.class, projectId)), Project.class);

            if ((project.getStatus() == ProjectStatus.DELETE) || project.getStatus() == null) return responseProject;

            //find sections
            AllSections allSections  = commonService.findSectionByProjectId(projectId, em);

            if (allSections.getResult() == ResponseStatus.SUCCESS.getResultCode()) {
                logger.debug("sections request success");
                responseProject.setSectionList(allSections.getSectionList());
            } else
                logger.error("sections request success");

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

    public AllProject findAll()
    {
        AllProject projectList = new AllProject();
        CommonResponse commonResponse = new CommonResponse(null);
        Gson gson = new Gson();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            String queryString =
                    "select * from business_project where bp_status <> '" + ProjectStatus.DELETE.getProjectStatus()+ "';";

            List projects = em.createNativeQuery(queryString, Project.class)
                    .getResultList();

            ArrayList<Project> project3 = new ArrayList<>();

            for (Object project2 : projects) {
                Project project1 = gson.fromJson(gson.toJson(project2), Project.class);
                Project project = new Project();
                project.setTitle(project1.getTitle());
                project.setDescription(project1.getDescription());
                project.setStatus(project1.getStatus());
                project.setIndex(project1.getIndex());
                project.setRegisterDate(project1.getRegisterDate());
                project.setLastModifyDate(project1.getLastModifyDate());

                project3.add(project);
            }

            projectList.setProjectList(project3);
            commonResponse.setResponse(ResponseStatus.SUCCESS);
            projectList.setCommonResponse(commonResponse);

        } catch (Exception e) {
            logger.error("findAll (project) exection error : "+e);
            commonResponse.setResponse(ResponseStatus.SERVER_ERROR);
            projectList.setCommonResponse(commonResponse);
        } finally {
            em.close();
        }

        return projectList;
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
