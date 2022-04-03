package business.businesswork.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("business")
public class CommonConfig {
    private String persistenceName;
}
