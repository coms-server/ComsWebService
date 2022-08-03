package coms.kw.ac.kr.server.controller.user;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.exception.implement.UnauthorizedException;
import coms.kw.ac.kr.server.service.event.EventService;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.user.UserInformationService;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(MyPageController.BASE_CONTEXT)
public class MyPageController {
    public static final String BASE_CONTEXT = "/mypage";
    public static final String INDEX_URI = "/index";
    private static final String INFORMATION_URI = "/information";

    private final UserInformationService uInfoService;
    private final AuthenticationFacade authFacade;

    @Autowired
    public MyPageController(UserInformationService uInfoService, EventService eventService,
            AuthenticationFacade authFacade) {
        this.uInfoService = uInfoService;
        this.authFacade = authFacade;
    }

    @RequestMapping("/")
    public String rootRedirect() {
        return "redirect:" + BASE_CONTEXT + INDEX_URI;
    }

    @RequestMapping("/index")
    public String mypageIndex(Model model) {
        return ViewSelector.byLayoutType(model, LayoutType.MYPAGE);
    }

    @RequestMapping(INFORMATION_URI + "/{userIndex}")
    public String personalInformationPage(@PathVariable int userIndex, Model model) {
        if (authFacade.getUserIndex() != userIndex)
            throw new UnauthorizedException();

        UserInformationVO userInfo = uInfoService.getUserInformation(userIndex);
        model.addAttribute("userInfo", userInfo);

        return ViewSelector.layoutOnly(LayoutType.PERSONAL_INFORMATION);
    }
}