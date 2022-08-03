package coms.kw.ac.kr.server.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value="production")
@PropertySource({"classpath:config/application-production.properties"})
public class ProfileProduction {
    
}