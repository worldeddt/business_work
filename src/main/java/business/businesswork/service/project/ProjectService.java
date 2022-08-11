package business.businesswork.service.project;

import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.domain.Task;
import business.businesswork.enumerate.ProjectStatus;
import business.businesswork.enumerate.ResponseStatus;
import business.businesswork.enumerate.SectionStatus;
import business.businesswork.enumerate.TaskStatusType;
import business.businesswork.vo.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    public CommonResponse deleteProject(Long projectId)
    {
        CommonResponse commonResponse = new CommonResponse();
        EntityManager em = emf.createEntityManager();
        Gson gson = new Gson();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = gson.fromJson(gson.toJson(em.find(Project.class, projectId)), Project.class);
            project.setStatus(ProjectStatus.DELETE);
            project.setDeleteDate(this.getThisTime());
            em.merge(project);

            AllSections allSections = this.findSectionByProjectId(projectId, em);

            if (allSections.getResult() == ResponseStatus.SUCCESS.getResultCode()) {
                for (SectionVO sectionVo : allSections.getSectionList()) {
                    Section section1 = gson.fromJson(gson.toJson(em.find(Section.class, sectionVo.getIndex())), Section.class);
                    section1.setStatus(SectionStatus.DELETE);
                    section1.setDeleteDate(this.getThisTime());
                    em.merge(section1);
                    if (sectionVo.getTaskList().size() == 0) continue;
                    for (TaskVO taskVO : sectionVo.getTaskList()) {
                        Task task1 = gson.fromJson(gson.toJson(em.find(Task.class, taskVO.getIndex())), Task.class);
                        task1.setTaskStatusType(TaskStatusType.DELETE);
                        task1.setDeleteDate(this.getThisTime());
                        em.merge(task1);
                    }
                }
            }

            tx.commit();
            commonResponse.setResult(ResponseStatus.SUCCESS.getResultCode());
        } catch (Exception e) {
            logger.error("delete project exception error : "+e);
            commonResponse.setResult(ResponseStatus.SERVER_ERROR.getResultCode());
            commonResponse.setMessage(e.getMessage());
            tx.rollback();
        }

        return commonResponse;
    }

    public CommonResponse register(RegistProject registProject)
    {
        CommonResponse commonResponse = new CommonResponse();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = new Project();
            project.setTitle(registProject.getTitle());
            project.setDescription(registProject.getDescription());
            project.setRegisterDate(this.getThisTime());
            project.setStatus(ProjectStatus.ACTIVE);

            em.persist(project);
            tx.commit();
            commonResponse.setResult(ResponseStatus.SUCCESS.getResultCode());
        } catch (Exception e) {
            logger.error("register project exception error : "+e);
            commonResponse.setResult(ResponseStatus.SERVER_ERROR.getResultCode());
            commonResponse.setMessage(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

        return commonResponse;
    }

    public CommonResponse update(ModifyProject modifyProject)
    {
        CommonResponse commonResponse = new CommonResponse();
        EntityManager em = emf.createEntityManager();
        Gson gson = new Gson();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = gson.fromJson(gson.toJson(em.find(Project.class, modifyProject.getIndex())), Project.class);

            if (project.getStatus() == ProjectStatus.DELETE) return commonResponse;

            project.setTitle(modifyProject.getTitle());
            project.setDescription(modifyProject.getDescription());
            project.setLastModifyDate(this.getThisTime());
            em.merge(project);

            tx.commit();
            commonResponse.setResult(ResponseStatus.SUCCESS.getResultCode());
        } catch (Exception e) {
            logger.error("update project exception error : "+e);
            commonResponse.setResult(ResponseStatus.SERVER_ERROR.getResultCode());
            commonResponse.setMessage(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

        return commonResponse;
    }

    public ResponseProject findProject(Long projectId)
    {
        CommonResponse commonResponse = new CommonResponse();
        ResponseProject responseProject = new ResponseProject();

        Gson gson = new Gson();
        EntityManager em = emf.createEntityManager();
        try {
            responseProject.setResult(commonResponse);

            Project project = gson.fromJson(gson.toJson(em.find(Project.class, projectId)), Project.class);

            if ((project.getStatus() == ProjectStatus.DELETE) || project.getStatus() == null) return responseProject;

            //find sections
            AllSections allSections  = this.findSectionByProjectId(projectId, em);

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
            commonResponse.setResult(ResponseStatus.SUCCESS.getResultCode());
            responseProject.setResult(commonResponse);

        } catch (Exception e) {
            logger.error("findProject exception error : "+e);
            commonResponse.setResult(ResponseStatus.SERVER_ERROR.getResultCode());
            responseProject.setResult(commonResponse);
        } finally {
            em.close();
        }

        return responseProject;
    }

    public AllProject findAll()
    {
        AllProject projectList = new AllProject();
        CommonResponse commonResponse = new CommonResponse();
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
            commonResponse.setResult(ResponseStatus.SUCCESS.getResultCode());
            projectList.setResult(commonResponse);

        } catch (Exception e) {
            logger.error("findAll (project) exection error : "+e);
            commonResponse.setResult(ResponseStatus.SERVER_ERROR.getResultCode());
            commonResponse.setMessage(e.getMessage());
            projectList.setResult(commonResponse);
        } finally {
            em.close();
        }

        return projectList;
    }

    private AllSections findSectionByProjectId(Long projectId, EntityManager em)
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
            logger.error("findSectionByProjectId error : "+e);
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
