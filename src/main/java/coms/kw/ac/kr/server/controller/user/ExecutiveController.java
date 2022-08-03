package coms.kw.ac.kr.server.controller.user;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.exception.implement.UnauthorizedException;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.user.ExecutiveManageService;
import coms.kw.ac.kr.server.vo.user.ExecutiveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ExecutiveController.BASE_CONTEXT)
public class ExecutiveController {
    public static final String BASE_CONTEXT = "/executive";

    private final ExecutiveManageService executiveManageService;
    private final AuthenticationFacade authFacade;

    @Autowired
    public ExecutiveController(ExecutiveManageService execManageService, AuthenticationFacade authFacade) {
        this.executiveManageService = execManageService;
        this.authFacade = authFacade;
    }

    @RequestMapping("/new-executive-form")
    public String newExecutiveForm(Model model) {
        // Authorization check
        if (!authFacade.getPrincipal().isAdmin())
            throw new UnauthorizedException();

        return ViewSelector.layoutOnly(LayoutType.NEW_EXEC_FORM);
    }

    @RequestMapping("/editor/{execIndex}")
    public String executiveEditor(@PathVariable int execIndex, Model model) {
        // Authorization check
        if (!authFacade.getPrincipal().isAdmin())
            throw new UnauthorizedException();

        ExecutiveVO info = executiveManageService.getExecutiveInformation(execIndex);
        model.addAttribute("executiveInfo", info);

        return ViewSelector.layoutOnly(LayoutType.EXEC_EDITOR);
    }

}
