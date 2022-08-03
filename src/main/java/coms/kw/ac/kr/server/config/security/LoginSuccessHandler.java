package coms.kw.ac.kr.server.config.security;

import coms.kw.ac.kr.server.service.user.UserAuthenticationService;
import coms.kw.ac.kr.server.service.user.UserHibernationService;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserAuthenticationService authenticationService;
    private final UserHibernationService hibernationService;

    @Autowired
    public LoginSuccessHandler(UserAuthenticationService authenticationService,
            UserHibernationService hibernationService) {
        this.authenticationService = authenticationService;
        this.hibernationService = hibernationService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        UserAuthenticationVO user = (UserAuthenticationVO) authentication.getPrincipal();
        authenticationService.updateUserLastLogin(user.getUser_idx());
        hibernationService.restoreHibernationState(user);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}