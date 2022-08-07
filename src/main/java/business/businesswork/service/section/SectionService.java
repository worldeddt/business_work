package business.businesswork.service.section;

import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.domain.Task;
import business.businesswork.enumerate.ResponseStatus;
import business.businesswork.enumerate.SectionStatus;
import business.businesswork.enumerate.TaskStatusType;
import business.businesswork.vo.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class SectionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    public CommonResponse register(RegisterSection registerSection)
    {
        CommonResponse commonResponse = new CommonResponse();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = em.find(Project.class, registerSection.getProjectId());

            Section section = new Section();
            section.setTitle(registerSection.getTitle());
            section.setDescription(registerSection.getDescription());
            section.setStatus(registerSection.getSectionStatus());
            section.setRegisterDate(this.getThisTime());
            section.setProject(project);

            em.persist(section);
            em.flush();
            em.clear();
            tx.commit();

            commonResponse.setResult(ResponseStatus.SUCCESS.getResultCode());
        } catch (Exception e) {
            logger.error("register section exception error : "+e);
            commonResponse.setResult(ResponseStatus.SERVER_ERROR.getResultCode());
            commonResponse.setMessage(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

        return commonResponse;
    }

    public CommonResponse update(ModifySection modifySection)
    {
        CommonResponse commonResponse = new CommonResponse();
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

            commonResponse.setResult(ResponseStatus.SUCCESS.getResultCode());
        } catch (Exception e) {
            logger.error("update section exception error : "+e);
            commonResponse.setResult(ResponseStatus.SERVER_ERROR.getResultCode());
            commonResponse.setMessage(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

        return commonResponse;
    }

    public CommonResponse delete(Long SectionId)
    {
        CommonResponse commonResponse = new CommonResponse();
        EntityManager em = emf.createEntityManager();
        Gson gson = new Gson();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Section section = em.find(Section.class, SectionId);
            section.setStatus(SectionStatus.DELETE);
            em.persist(section);

            String queryString =
                    "select * from business_task where bs_index = '" + SectionId + "' and bt_status <> '" + TaskStatusType.DELETE + "';";
            List tasks = em.createNativeQuery(queryString, Section.class)
                    .getResultList();

            for (Object task : tasks) {
                Task task1 = gson.fromJson(gson.toJson(task), Task.class);
                task1.setTaskStatusType(TaskStatusType.DELETE);
                em.persist(task1);
            }

            em.flush();
            tx.commit();

            commonResponse.setResult(ResponseStatus.SUCCESS.getResultCode());
        } catch (Exception e) {
            logger.error("delete section exception error : "+e);
            commonResponse.setResult(ResponseStatus.SERVER_ERROR.getResultCode());
            commonResponse.setMessage(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

        return commonResponse;
    }

    public ResponseSection findById(Long id)
    {
        ResponseSection responseSection = new ResponseSection();
        Gson gson = new Gson();
        EntityManager em = emf.createEntityManager();

        try {
            responseSection.setResult(ResponseStatus.FAIL.getResultCode());

            Section section1 = gson.fromJson(gson.toJson(em.find(Section.class, id)), Section.class);

            responseSection.setIndex(section1.getIndex());
            responseSection.setDescription(section1.getDescription());
            responseSection.setTitle(section1.getTitle());
            responseSection.setSectionStatus(section1.getStatus());
            responseSection.setLastModifyDate(section1.getLastModifyDate());
            responseSection.setRegisterDateTime(section1.getRegisterDate());
            responseSection.setResult(ResponseStatus.SUCCESS.getResultCode());

        } catch (Exception e) {
            System.out.println("section findById : "+e);
        } finally {
            em.close();
        }

        return responseSection;
    }

    public AllSections findAllByProjectId(Long projectId)
    {
        AllSections responseSection = new AllSections();
        EntityManager em = emf.createEntityManager();
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

                sectionList.add(section2);
            }

            responseSection.setSectionList(sectionList);
            responseSection.setResult(ResponseStatus.SUCCESS.getResultCode());

        } catch (Exception e) {
            System.out.println("error exception : "+e);
        } finally {
            em.close();
        }

        return responseSection;
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
