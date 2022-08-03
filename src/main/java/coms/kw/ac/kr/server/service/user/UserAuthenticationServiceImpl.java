package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.config.WebSecurityConfig;
import coms.kw.ac.kr.server.config.WebSecurityConfig.Authority;
import coms.kw.ac.kr.server.dao.UserAuthenticationDAO;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationServiceImpl.class);

    private final UserAuthenticationDAO userAuthDAO;
    private final AuthenticationFacade authFacade;

    @Autowired
    public UserAuthenticationServiceImpl(UserAuthenticationDAO userAuthDAO, AuthenticationFacade authFacade) {
        this.userAuthDAO = userAuthDAO;
        this.authFacade = authFacade;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Override
    public boolean insertUserAuthentication(UserAuthenticationVO userAuth) {
        boolean invalid = false;
        String alias = userAuth.getAlias();
        String password = userAuth.getPassword();
        String name = userAuth.getName();
        if (alias == null || alias.isEmpty() || alias.length() < 4)
            invalid = true;
        else if (!isPasswordCapable(password))
            invalid = true;
        else if (name == null || name.isEmpty())
            invalid = true;

        if (invalid)
            return false;

        userAuth.setPermission(false);
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));

        try {
            userAuthDAO.insertUserAuthentication(userAuth);
        } catch (DataAccessException exception) {
            logger.error("Failed to insert user authentication.", exception);
            return false;
        }

        logger.info("New authentication inserted; user_idx={}, name={}", userAuth.getUser_idx(), userAuth.getName());
        return true;
    }

    @Override
    public UserAuthenticationVO getUserAuthentication(int userIndex) {
        UserAuthenticationVO userAuth = userAuthDAO.getUserAuthentication(userIndex);

        return userAuth;
    }

    @Override
    public UserDetails loadUserByUsername(String alias) throws UsernameNotFoundException {
        UserAuthenticationVO vo = userAuthDAO.selectUserByAlias(alias);
        if (vo == null)
            throw new UsernameNotFoundException("Username not found.");

        return vo;
    }

    @Override
    public boolean updateUserPassword(int userIndex, String password) {
        if (!isPasswordCapable(password))
            return false;

        password = passwordEncoder.encode(password);
        userAuthDAO.updateUserPassword(userIndex, password);

        UserAuthenticationVO user = authFacade.getPrincipal();
        logger.info("User password updated; user_idx={}, name={}", user.getUser_idx(), user.getName());
        return true;
    }

    @Override
    public void deleteUserAuthentication(int userIndex) {
        userAuthDAO.deleteUserAuthentication(userIndex, "DELETED_" + userIndex);

        UserAuthenticationVO user = authFacade.getPrincipal();
        logger.info("User authentication deleted; user_idx={}, name={}", user.getUser_idx(), user.getName());
    }

    @Override
    public boolean isAliasExist(String alias) {
        String result = userAuthDAO.isAliasExists(alias);

        return (result != null && !result.isEmpty());
    }

    @Override
    public void updateUserLastLogin(int userIndex) {
        userAuthDAO.updateUserLastLogin(userIndex, LocalDateTime.now().toString());

        UserAuthenticationVO user = authFacade.getPrincipal();
        logger.info("User logged in; user_idx={}, name={}", user.getUser_idx(), user.getName());
    }

    @Override
    public void grantPermission(int userIndex) {
        userAuthDAO.grantPermission(userIndex, Authority.MEMBER.getValue());

        UserAuthenticationVO granted = userAuthDAO.getUserAuthentication(userIndex);
        UserAuthenticationVO performer = authFacade.getPrincipal();
        logger.info("User signup granted; granted user_idx={}, name={}; granted by user_idx={}, name={}",
                granted.getUser_idx(), granted.getName(), performer.getUser_idx(), performer.getName());
    }

    @Override
    public void denyPermission(int userIndex) {
        // UserAuthenticationDAO::denyPermission will delete the record. Save it first.
        String deniedUserName = userAuthDAO.getUserAuthentication(userIndex).getName();
        userAuthDAO.denyPermission(userIndex);

        UserAuthenticationVO performer = authFacade.getPrincipal();
        logger.info("User signup denied; denied user's name={}; denied by user_idx={}, name={}", deniedUserName,
                performer.getUser_idx(), performer.getName());
    }

    private boolean isPasswordCapable(String password) {
        // Password is null
        if (password == null || password.isEmpty())
            return false;

        // Password is not capable
        Matcher matcher = WebSecurityConfig.PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }

}