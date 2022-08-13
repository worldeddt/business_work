package business.businesswork.service.common;

import business.businesswork.domain.Section;
import business.businesswork.domain.Task;
import business.businesswork.enumerate.ResponseStatus;
import business.businesswork.enumerate.SectionStatus;
import business.businesswork.enumerate.TaskStatusType;
import business.businesswork.vo.AllSections;
import business.businesswork.vo.AllTasks;
import business.businesswork.vo.SectionVO;
import business.businesswork.vo.TaskVO;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");

    public AllSections findSectionByProjectId(Long projectId, EntityManager em)
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

}
