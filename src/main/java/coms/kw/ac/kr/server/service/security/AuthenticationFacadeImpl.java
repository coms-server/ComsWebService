package coms.kw.ac.kr.server.service.security;

import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private static final AuthenticationTrustResolver authTrustResolver = new AuthenticationTrustResolverImpl();

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public boolean isAuthenticated() {
        return !authTrustResolver.isAnonymous(getAuthentication());
    }

    private boolean isAuthenticated(Authentication authentication){
        return !authTrustResolver.isAnonymous(authentication);
    }

    @Override
    public UserAuthenticationVO getPrincipal() {
        Authentication authentication = getAuthentication();
        if (!isAuthenticated(authentication))
            throw new IllegalStateException();

        return (UserAuthenticationVO) authentication.getPrincipal();
    }

    @Override
    public int getUserIndex() {
        return getPrincipal().getUser_idx();
    }

}