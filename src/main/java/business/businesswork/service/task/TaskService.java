package business.businesswork.service.task;

import business.businesswork.config.CommonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Service
public class TaskService {

    @Autowired
    private CommonConfig commonConfig;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(commonConfig.getPersistenceName());


    public void regist()
    {

    }

}
