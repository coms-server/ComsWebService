package coms.kw.ac.kr.server.service.security;

import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

/**
 * Provides the {@link Authentication} and {@code Principal} from the current
 * security context.
 */
public interface AuthenticationFacade {
    /**
     * Obtains the currently authenticated principal, or an authentication request
     * token. See {@link SecurityContext#getAuthentication()}.
     * 
     * @return the {@code Authentication} or {@code null} if no authentication
     *         information is available
     */
    public Authentication getAuthentication();

    /**
     * Returns {@code true} if current {@code principal} is authenticated. This
     * simply means that the user is logged in.
     * 
     * @return is authenticated
     */
    public boolean isAuthenticated();

    /**
     * Return the {@code principal} form of {@link UserAuthenticationVO}.
     * 
     * @return authenticated principal
     * @throws IllegalStateException if token is not authenticated
     */
    public UserAuthenticationVO getPrincipal();

    /**
     * Return only {@code user_idx} from the principal.
     * 
     * @return unique user index
     * @throws IllegalStateException if token is not authenticated
     */
    public int getUserIndex();
}