package coms.kw.ac.kr.server.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value="develop")
@PropertySource({"classpath:config/application-develop.properties"})
public class ProfileDevelop {
    
}