package business.businesswork.service.section;

import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
//import business.businesswork.domain.Task;
import business.businesswork.enumerate.ResponseStatus;
import business.businesswork.enumerate.SectionStatus;
import business.businesswork.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class SectionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    public void register(RegisterSection registerSection)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = em.find(Project.class, registerSection.getProjectId());

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime datetime = LocalDateTime.parse(this.dateFormatter(now), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Section section = new Section();
            section.setTitle(registerSection.getTitle());
            section.setDescription(registerSection.getDescription());
            section.setStatus(registerSection.getSectionStatus());
            section.setRegisterDate(datetime);
            project.addSection(section);

            em.persist(section);
            em.flush();
            em.clear();

            List<Section> result = em.createQuery("select s from Section s order by s.index desc", Section.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            for (Section section1 : result) {
                System.out.println("section : "+ section1.getIndex());
            }

            tx.commit();
        } catch (Exception e) {
            System.out.println("e : "+e);
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public void update(ModifySection modifySection)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Section section = em.find(Section.class, modifySection.getIndex());
            Project project = em.find(Project.class, modifySection.getProjectId());
            section.setTitle(modifySection.getTitle());
            section.setDescription(modifySection.getDescription());
            section.setProject(project);

            em.persist(section);
            em.flush();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public void delete(Long SectionId)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Section section = em.find(Section.class, SectionId);
            section.setStatus(SectionStatus.DELETE);
            em.persist(section);

//            List<Task> tasks = section.getTasks();
//            for (Task task : tasks) {
//                Task task1 = em.find(Task.class, task.getIndex());
//                task1.setTaskStatusType(TaskStatusType.DELETE);
//                em.persist(task1);
//            }

            em.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public ResponseSection findById(Long id)
    {
        ResponseSection responseSection = new ResponseSection();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            TypedQuery<Section> query =
                    em.createQuery("select s from Section s where s.index = :index and s.status = :status", Section.class)
                            .setParameter("index", id)
                            .setParameter("status", SectionStatus.ACTIVE);

            Section section1 = query.getSingleResult();
            responseSection.setResult(ResponseStatus.FAIL.getResultCode());

            if (section1.getStatus() == SectionStatus.DELETE) return responseSection;

//            AllTasks allTasks = new AllTasks();
//            List<Task> tasks = section1.getTasks();

//            ArrayList<Task> taskList = new ArrayList<>();

//            allTasks.setResult(ResponseStatus.FAIL.getResultCode());
//            for (Task task : tasks) {
//                Task task1 = new Task();
//                task1.setIndex(task.getIndex());
//                task1.setDescription(task.getDescription());
//                task1.setTitle(task.getTitle());
//                task1.setTaskStatusType(task.getTaskStatusType());
//                task1.setRegisterDate(task.getRegisterDate());
//                task1.setLastModifyDate(task.getLastModifyDate());
//                taskList.add(task1);
//            }

//            allTasks.setResult(ResponseStatus.SUCCESS.getResultCode());
//            allTasks.setTaskList(taskList);
//            responseSection.setTaskList(allTasks);
            responseSection.setIndex(section1.getIndex());
            responseSection.setDescription(section1.getDescription());
            responseSection.setRegisterDateTime(section1.getRegisterDate());
            responseSection.setTitle(section1.getTitle());
            responseSection.setResult(ResponseStatus.SUCCESS.getResultCode());

            return responseSection;
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

        return responseSection;
    }

    public AllSections findAll(Long projectId)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = em.find(Project.class, projectId);
            List<Section> sections = project.getSections();

            AllSections responseSection = new AllSections();
            ArrayList<Section> sectionList = new ArrayList<>();

            responseSection.setResult(ResponseStatus.FAIL.getResultCode());
            for (Section section : sections) {
                Section section1 = new Section();
                section1.setIndex(section.getIndex());
                section1.setDescription(section.getDescription());
                section1.setTitle(section.getTitle());
                section1.setStatus(section.getStatus());

                sectionList.add(section1);
            }

            responseSection.setSectionList(sectionList);
            responseSection.setResult(ResponseStatus.SUCCESS.getResultCode());

            tx.commit();

            return responseSection;
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
