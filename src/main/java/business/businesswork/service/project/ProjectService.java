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

//            List<Section> sections = project.getSections();
//            for (Section section : sections) {
//                Section section1 = em.find(Section.class, section.getIndex());
//                section1.setStatus(SectionStatus.DELETE);
//                section1.setDeleteDate(datetime);
//                em.persist(section1);
//            }

            em.flush();
            tx.commit();
        } catch (Exception e) {
            logger.error("delete project exception error : "+e);
            tx.rollback();
        } finally {
            em.close();
        }
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
            logger.error("register project exception error : "+e);
            tx.rollback();
        } finally {
            em.close();
        }
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
            logger.error("update project exception error : "+e);
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public ResponseProject findProject(Long projectId)
    {
        ResponseProject responseProject = new ResponseProject();

        Gson gson = new Gson();
        EntityManager em = emf.createEntityManager();
        try {
            responseProject.setResult(ResponseStatus.FAIL.getResultCode());

            Project project = gson.fromJson(gson.toJson(em.find(Project.class, projectId)), Project.class);

            System.out.println("project single :" +project.getIndex());

            if ((project.getStatus() == ProjectStatus.DELETE) || project.getStatus() == null) return responseProject;

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
            responseProject.setResult(ResponseStatus.SUCCESS.getResultCode());

        } catch (Exception e) {
            logger.error("findProject exception error : "+e);
        } finally {
            em.close();
        }

        return responseProject;
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
        } finally {
            em.close();
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
                task2.setTitle(task1.getTitle());
                task2.setTaskStatusType(task1.getTaskStatusType());

                if (task2.getTaskStatusType() != TaskStatusType.DELETE) taskList.add(task2);
            }

            responseTask.setTaskList(taskList);
            responseTask.setResult(ResponseStatus.SUCCESS.getResultCode());

        } catch (Exception e) {
            logger.error("findTasksBySectionId error : "+e);
        } finally {
            em.close();
        }

        return responseTask;
    }

    public AllProject findAll()
    {
        AllProject projectList = new AllProject();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            TypedQuery<Project> query =
                    em.createQuery("SELECT p FROM Project p WHERE p.status = :status", Project.class)
                    .setParameter("status", ProjectStatus.ACTIVE.getProjectStatus());

            List<Project> projects = query.getResultList();
            ArrayList<Project> project2 = new ArrayList<>();

            for (Project project1 : projects) {
                Project project = new Project();
                project.setTitle(project1.getTitle());
                project.setDescription(project1.getDescription());
                project.setStatus(project1.getStatus());
                project.setIndex(project1.getIndex());
                project.setRegisterDate(project1.getRegisterDate());
                project.setLastModifyDate(project1.getLastModifyDate());

                project2.add(project);
            }

            logger.info("====== projects : "+project2);
            projectList.setProjectList(project2);
            projectList.setResult(ResponseStatus.SUCCESS.getResultCode());

        } catch (Exception e) {
            logger.error("findAll (project) exection error : "+e);
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
