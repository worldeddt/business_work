//package business.businesswork.api.controller.task;
//
//import business.businesswork.domain.*;
//import business.businesswork.enumerate.TaskStatusType;
////import business.businesswork.service.task.TaskService;
////import business.businesswork.vo.ModifyTask;
//import business.businesswork.vo.RegisterTask;
//import business.businesswork.vo.ResponseTask;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
//import java.util.List;
//
//@RestController
//@RequestMapping("/task")
//public class TaskController {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired(required = false)
//    private TaskService taskService;
//
//    @RequestMapping(value = "/register", method = {RequestMethod.POST})
//    public void register(@RequestBody RegisterTask registerTask) throws Exception {
//        taskService.register(registerTask);
//    }
//
//    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
//    public void delete(@RequestParam("taskIndex") Long taskIndex) throws Exception {
//        taskService.delete(taskIndex);
//    }
//
//    @RequestMapping(value = "/update", method = {RequestMethod.POST})
//    public void update(@RequestBody ModifyTask modifyTask) {
//        taskService.update(modifyTask);
//    }
//
//    @RequestMapping(value = "/", method = {RequestMethod.GET})
//    public ResponseTask findOne(@RequestParam(required = false, name = "taskIndex") Long taskIndex) {
//        return taskService.findById(taskIndex);
//    }
//
//    @RequestMapping(value="/test", method = {RequestMethod.GET})
//    public void test() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("businessWork");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//        try {
//            Project project = new Project();
//            project.setTitle("정기 프로젝트");
//            em.persist(project);
//
//            Section section = new Section();
//            section.setTitle("in sprint");
//            em.persist(section);
//
//            Member member = new Member();
//            member.setName("김태형");
//            em.persist(member);
//
//            Task task = new Task();
//            task.setTaskStatusType(TaskStatusType.TODO);
//            task.setDescription("erp 정기 업무");
//            em.persist(task);
//
//            Review review = new Review();
//            review.setReviewOpinion("수정해주세요");
//            em.persist(review);
//
////            member.getTasks().add(task);
////            task.setMember(member);
////
////            section.getTasks().add(task);
////            task.setSection(section);
//
////            project.getSections().add(section);
//            //양방향 매핑시 연관관계 주인에 값을 입력한다.
//            project.addSection(section);
//            task.addReivew(review);
//            member.addReview(review);
//            member.addTask(task);
//
//            em.flush();
//            em.clear();
//
//            System.out.println("==");
//
//            List<Member> result = em.createQuery("select m from Member m order by m.index desc", Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            System.out.println("result size = "+result.size());
//
//            for(Member member1 : result) {
//                System.out.println("query member = " + member1.getName());
//            }
//
////            Project findProject = em.find(Project.class, project.getIndex());
////
////            List<Section> sections = findProject.getSections();
////
////            for (Section section1 : sections) {
////                System.out.println(section1.getName());
////            }
////
////            Review findReview = em.find(Review.class, review.getIndex());
////
////            Task task2 = findReview.getTask();
////
////            System.out.println(task2.getDescription());
//
//            System.out.println("==");
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();
//    }
//}
