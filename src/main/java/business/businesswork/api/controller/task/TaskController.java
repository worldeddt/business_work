package business.businesswork.api.controller.task;

import business.businesswork.enumerate.StatusType;
import business.businesswork.vo.ModifyTask;
import business.businesswork.vo.RegisterTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import business.businesswork.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String regist(@RequestBody RegisterTask registerTask) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Date date = new Date();

            LocalDateTime now = LocalDateTime.now();
            String nowDateTime = String.format(
                    "%04d-%02d-%02d %02d:%02d:%02d",
                    now.getYear(),
                    now.getMonthValue(),
                    now.getDayOfMonth(),
                    now.getHour(),
                    now.getMinute(),
                    now.getSecond()
            );

            logger.info("now date : "+date);
            logger.info("now datetime : "+now);
            logger.info("now datetime string : "+nowDateTime);

            Task task = new Task();
            task.setTitle(registerTask.getTitle());
            task.setDescription(registerTask.getDescription());
            task.setRegisterDate(date);
            task.setStatusType(registerTask.getStatus());
            em.persist(task);

            Project project = em.find(Project.class, registerTask.getProjectId());
            Section section = em.find(Section.class, registerTask.getSectionId());

            project.addSection(section);
            section.addTask(task);

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

        return "regist";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public void delete(ModifyTask modifyTask) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Task task = new Task();
            task.setIndex(modifyTask.getIndex());
            em.persist(task);



        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public void update() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @RequestMapping(value="/test", method = {RequestMethod.GET})
    public void test() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = new Project();
            project.setTitle("정기 프로젝트");
            em.persist(project);

            Section section = new Section();
            section.setTitle("in sprint");
            em.persist(section);

            Member member = new Member();
            member.setName("김태형");
            em.persist(member);

            Task task = new Task();
            task.setStatusType(StatusType.TODO);
            task.setDescription("erp 정기 업무");
            em.persist(task);

            Review review = new Review();
            review.setReviewOpinion("수정해주세요");
            em.persist(review);


//            member.getTasks().add(task);
//            task.setMember(member);
//
//            section.getTasks().add(task);
//            task.setSection(section);

//            project.getSections().add(section);
            //양방향 매핑시 연관관계 주인에 값을 입력한다.
            project.addSection(section);
            task.addReivew(review);
            member.addReview(review);
            member.addTask(task);

            em.flush();
            em.clear();

            System.out.println("==");

            List<Member> result = em.createQuery("select m from Member m order by m.index desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("result size = "+result.size());

            for(Member member1 : result) {
                System.out.println("query member = " + member1.getName());
            }

//            Project findProject = em.find(Project.class, project.getIndex());
//
//            List<Section> sections = findProject.getSections();
//
//            for (Section section1 : sections) {
//                System.out.println(section1.getName());
//            }
//
//            Review findReview = em.find(Review.class, review.getIndex());
//
//            Task task2 = findReview.getTask();
//
//            System.out.println(task2.getDescription());

            System.out.println("==");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
