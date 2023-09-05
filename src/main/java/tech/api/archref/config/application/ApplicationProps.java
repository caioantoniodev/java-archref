package tech.api.archref.config.application;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "marvelapi")
public class ApplicationProps {

    private String publicKey;
    private String privateKey;
    private List<Long> characterIds;
}
