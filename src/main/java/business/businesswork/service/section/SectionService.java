package business.businesswork.service.section;

import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.domain.Task;
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

    @Autowired(required = false)
    private CommonService commonService;

    public CommonResponse register(RegisterSection registerSection)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = em.find(Project.class, registerSection.getProjectId());

            if (project == null) throw new BusinessException(ResponseStatus.PROJECT_IS_NULL);

            Section section = new Section();
            section.setTitle(registerSection.getTitle());
            section.setDescription(registerSection.getDescription());
            section.setStatus(registerSection.getSectionStatus());
            section.setRegisterDate(this.getThisTime());
            section.setProject(project);
            em.persist(section);
            em.flush();

            if (section.getIndex() == null) throw new BusinessException(ResponseStatus.SECTION_REGISTER_FAIL);

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("register section exception error : "+e);
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

    public CommonResponse update(ModifySection modifySection)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Gson gson = new Gson();
        tx.begin();

        try {
            Section section = gson.fromJson(gson.toJson(em.find(Section.class, modifySection.getIndex())), Section.class);

            if (section == null) throw new BusinessException(ResponseStatus.SECTION_IS_NULL);

            if (section.getStatus() == SectionStatus.DELETE)
                throw new BusinessException(ResponseStatus.SECTION_WAS_DELETE);

            section.setTitle(modifySection.getTitle());
            section.setDescription(modifySection.getDescription());
            section.setLastModifyDate(this.getThisTime());
            em.merge(section);

            em.flush();
            em.clear();

            Section section1 = gson.fromJson(gson.toJson(em.find(Section.class, modifySection.getIndex())), Section.class);

            if (
                !section1.getStatus().equals(section.getStatus()) ||
                !section1.getTitle().equals(section.getTitle()) ||
                !section1.getDescription().equals(section.getDescription())
            )
                throw new BusinessException(ResponseStatus.SECTION_UPDATE_FAL);

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("update section exception error : "+e);
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

    public CommonResponse delete(Long SectionId)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        EntityManager em = emf.createEntityManager();
        Gson gson = new Gson();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            AllTasks allTasks = commonService.findTasksBySectionId(SectionId, em);

            Section section = gson.fromJson(gson.toJson(em.find(Section.class, SectionId)), Section.class);
            if (section == null) throw new BusinessException(ResponseStatus.SECTION_IS_NULL);

            section.setStatus(SectionStatus.DELETE);
            section.setDeleteDate(this.getThisTime());
            em.merge(section);

            for (TaskVO taskVO : allTasks.getTaskList()) {
                Task task1 = gson.fromJson(gson.toJson(em.find(Task.class, taskVO.getIndex())), Task.class);
                if (task1 != null) {
                    task1.setTaskStatusType(TaskStatusType.DELETE);
                    task1.setDeleteDate(this.getThisTime());
                    em.merge(task1);
                }
            }

            em.flush();
            em.clear();

            Section section1 = gson.fromJson(gson.toJson(em.find(Section.class, SectionId)), Section.class);

            if (!section.getStatus().equals(section1.getStatus()))
                throw new BusinessException(ResponseStatus.SECTION_UPDATE_FAL);

            commonResponse.setResponse(ResponseStatus.SUCCESS);
            tx.commit();
        } catch (Exception e) {
            logger.error("delete section exception error : "+e);
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

    public ResponseSection findById(Long id)
    {
        CommonResponse commonResponse = new CommonResponse(null);
        ResponseSection responseSection = new ResponseSection();
        Gson gson = new Gson();
        EntityManager em = emf.createEntityManager();

        try {
            responseSection.setResult(commonResponse);

            Section section1 = gson.fromJson(gson.toJson(em.find(Section.class, id)), Section.class);

            responseSection.setIndex(section1.getIndex());
            responseSection.setDescription(section1.getDescription());
            responseSection.setTitle(section1.getTitle());
            responseSection.setSectionStatus(section1.getStatus());
            responseSection.setLastModifyDate(section1.getLastModifyDate());
            responseSection.setRegisterDateTime(section1.getRegisterDate());

            commonResponse.setResult(ResponseStatus.SUCCESS.getResultCode());
            responseSection.setResult(commonResponse);

        } catch (Exception e) {
            System.out.println("section findById : "+e);
            commonResponse.setResult(ResponseStatus.SERVER_ERROR.getResultCode());
            commonResponse.setMessage(e.getMessage());
            responseSection.setResult(commonResponse);
        } finally {
            em.close();
        }

        return responseSection;
    }

    public AllSections findAllByProjectId(Integer projectId)
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
