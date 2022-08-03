package coms.kw.ac.kr.server.config;

import coms.kw.ac.kr.server.service.user.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String LOGIN_PATH = "/login";
    public static final String LOGOUT_PATH = "/logout";

    public static final String[] ALL_PERMITTED = { 
        "/", 
        "/index", 
        "/information-policy", 
        "/about-coms", 
        "/account/**", 
        "/resources/**", 
        "/event/monthly/**"
    };

    public static final String PASSWORD_REGEX = "(?=.*\\d{1,40})(?=.*[a-zA-Z]{1,40}).{8,40}$";
    public static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    private final UserAuthenticationService userAuthService;
    private final AuthenticationSuccessHandler authSuccessHandler;
    
    public static enum Authority {
        ADMIN("ADMIN"), MEMBER("MEMBER");

        private final String value;
        public static final String HIERARCHY = ADMIN.value + ">" + MEMBER.value;

        private Authority(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    @Autowired
    public WebSecurityConfig(UserAuthenticationService userAuthService,
            SavedRequestAwareAuthenticationSuccessHandler authSuccessHandler) {
        this.userAuthService = userAuthService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(ALL_PERMITTED).permitAll()
                .antMatchers("/admin/**").hasAuthority(Authority.ADMIN.value)
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage(LOGIN_PATH)
                .defaultSuccessUrl("/index")
                .failureUrl(LOGIN_PATH)
                .usernameParameter("alias")
                .passwordParameter("password")
                .permitAll()
                .successHandler(authSuccessHandler)
                .and()
            .logout()
                .logoutUrl(LOGOUT_PATH)
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID") 
                .permitAll();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy(){
        RoleHierarchyImpl hierarchyImpl = new RoleHierarchyImpl();
        hierarchyImpl.setHierarchy(Authority.HIERARCHY);
        return hierarchyImpl;
    }

    
}