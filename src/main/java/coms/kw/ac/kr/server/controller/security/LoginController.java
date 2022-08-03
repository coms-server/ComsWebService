package coms.kw.ac.kr.server.controller.security;

import coms.kw.ac.kr.server.config.WebSecurityConfig;
import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    private final AuthenticationFacade authFacade;

    @Autowired
    public LoginController(AuthenticationFacade authFacade) {
        this.authFacade = authFacade;
    }

    @RequestMapping(WebSecurityConfig.LOGIN_PATH)
    public String login(Model model) {
        if (authFacade.isAuthenticated())
            return ViewSelector.REDIRECT_TO_INDEX;

        return ViewSelector.byLayoutType(model, LayoutType.LOGIN);
    }
}
