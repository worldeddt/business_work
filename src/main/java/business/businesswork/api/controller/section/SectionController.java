package business.businesswork.api.controller.section;

import business.businesswork.config.CommonConfig;
import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.service.section.SectionService;
import business.businesswork.util.KakaoApi;
import business.businesswork.vo.ModifySection;
import business.businesswork.vo.RegisterSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private CommonConfig commonConfig;

    private static final Logger logger = LoggerFactory.getLogger(KakaoApi.class);

    @Autowired
    private SectionService sectionService;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterSection registerSection) throws Exception {
        sectionService.register(registerSection);
    }

    @ResponseBody
    @RequestMapping(value =  "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam("sectionIndex") String index) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(commonConfig.getPersistenceName());
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Section section = em.find(Section.class, index);
            em.remove(section);

            em.flush();
            em.clear();

//            List<Member> result = em.createQuery("select m from Member m order by m.index desc", Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();

            Section result = em.createQuery("select s from Section s where s.index = :index", Section.class)
                    .setParameter("index", index)
                    .getSingleResult();

            logger.info("section query result"+result);


        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    @ResponseBody
    @RequestMapping(value =  "/update")
    public void update(@RequestParam ModifySection modifySection) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(commonConfig.getPersistenceName());
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {


        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
