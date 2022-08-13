package business.businesswork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={
        "business.businesswork.*"})
public class BusinessWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessWorkApplication.class, args);
    }

}
