package business.businesswork;

import business.businesswork.api.controller.project.ProjectController;
import business.businesswork.api.controller.section.SectionController;
import business.businesswork.api.controller.task.TaskController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={
        "business.businesswork.*"})
public class BusinessWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessWorkApplication.class, args);
    }

}
