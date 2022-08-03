package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Core user authentication service. Providing user authentication CURD method
 * and permission management. This interface extends {@link UserDetailsService}
 * required for {@link AuthenticationManagerBuilder} in spring security.
 */
public interface UserAuthenticationService extends UserDetailsService {

    public boolean insertUserAuthentication(UserAuthenticationVO userAuth);

    public UserAuthenticationVO getUserAuthentication(int userIndex);

    public boolean updateUserPassword(int userIndex, String password);

    public void deleteUserAuthentication(int userIndex);

    public boolean isAliasExist(String alias);

    public void updateUserLastLogin(int userIndex);

    public void grantPermission(int userIndex);

    public void denyPermission(int userIndex);

}