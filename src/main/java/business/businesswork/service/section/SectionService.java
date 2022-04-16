package business.businesswork.service.section;

import business.businesswork.config.CommonConfig;
import business.businesswork.domain.Project;
import business.businesswork.domain.Section;
import business.businesswork.enumerate.SectionStatus;
import business.businesswork.vo.ModifySection;
import business.businesswork.vo.RegisterSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import javax.persistence.*;


@Service
public class SectionService {

    @Autowired
    private CommonConfig commonConfig;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(commonConfig.getPersistenceName());

    public void register(RegisterSection registerSection)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Project project = em.find(Project.class, registerSection.getProjectId());

            Section section = new Section();
            section.setTitle(registerSection.getTitle());
            section.setDescription(registerSection.getDescription());
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

    public void update(ModifySection modifySection)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Section section = em.find(Section.class, modifySection.getIndex());
            section.setTitle(modifySection.getTitle());
            section.setDescription(modifySection.getDescription());

            em.persist(section);

            TypedQuery<Section> sectionQuery =
                    em.createQuery("select s from Section s where s.index = :index ", Section.class)
                            .setParameter("index", modifySection.getIndex());

            logger.info("========= section delete : "+sectionQuery);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public void delete(String SectionId)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Section section = em.find(Section.class, SectionId);
            section.setStatus(SectionStatus.DELETE);
            em.persist(section);
            em.flush();

            TypedQuery<Section> sectionQuery =
                    em.createQuery("select s from Section s where s.index = :index ", Section.class)
                            .setParameter("index", SectionId);

            logger.info("========= section delete : "+sectionQuery);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
