package coms.kw.ac.kr.server.controller.user;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.exception.implement.UnauthorizedException;
import coms.kw.ac.kr.server.service.event.EventService;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.statics.StaticInformationService;
import coms.kw.ac.kr.server.service.user.ExecutiveManageService;
import coms.kw.ac.kr.server.service.user.UserInformationService;
import coms.kw.ac.kr.server.vo.event.EventVO;
import coms.kw.ac.kr.server.vo.statics.PositionVO;
import coms.kw.ac.kr.server.vo.user.ExecutiveVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(AdminController.BASE_CONTEXT)
public class AdminController {
    public static final String BASE_CONTEXT = "/admin";
    public static final String USER_URI = "/user";
    public static final String EVENT_URI = "/event";
    public static final String EXECUTIVE_URI = "/executive";

    private final UserInformationService userInformationService;
    private final ExecutiveManageService execManageService;
    private final StaticInformationService staticInfoService;
    private final EventService eventService;
    private final AuthenticationFacade authFacade;

    @Autowired
    public AdminController(UserInformationService userInformationService, ExecutiveManageService execManageService,
            StaticInformationService staticInfoService, EventService eventService, AuthenticationFacade authFacade) {
        this.userInformationService = userInformationService;
        this.execManageService = execManageService;
        this.staticInfoService = staticInfoService;
        this.eventService = eventService;
        this.authFacade = authFacade;
    }

    @RequestMapping(value = "/")
    public String rootRedirect() {
        return "redirect:/admin/index";
    }

    @RequestMapping(value = "/index")
    public String adminIndex(Model model) {
        // Authorization check
        if (!authFacade.getPrincipal().isAdmin())
            throw new UnauthorizedException();

        List<UserInformationVO> userList = userInformationService.getApprovedUserList();
        List<UserInformationVO> pendingUserList = userInformationService.getPendingUserList();
        model.addAttribute("userList", userList);
        model.addAttribute("pendingUserList", pendingUserList);

        List<EventVO> eventList = eventService.getEventList();
        model.addAttribute("eventList", eventList);
        model.addAttribute("adminMode", true);

        List<ExecutiveVO> activeExecutiveList = execManageService.getActiveExecutiveList();
        List<ExecutiveVO> nonactiveExecutiveList = execManageService.getNonActiveExecutiveList();
        List<PositionVO> positionList = staticInfoService.getPositionList();
        model.addAttribute("activeExecList", activeExecutiveList);
        model.addAttribute("nonActiveExecList", nonactiveExecutiveList);
        model.addAttribute("positionList", positionList);

        return ViewSelector.byLayoutType(model, LayoutType.ADMIN);
    }

    @RequestMapping(USER_URI + "/selector")
    public String userSelector(@RequestParam(required = false) String selected, Model model) {
        // Authorization check
        if (!authFacade.getPrincipal().isAdmin())
            throw new UnauthorizedException();

        List<UserInformationVO> userList = userInformationService.getApprovedUserList();
        model.addAttribute("userList", userList);

        if (selected != null && !selected.isEmpty()) {
            List<Integer> selectedList = new ArrayList<>();
            for (String i : selected.split(","))
                selectedList.add(Integer.parseInt(i));
            model.addAttribute("selectedList", selectedList);
        }

        return ViewSelector.layoutOnly(LayoutType.USER_SELECTOR);
    }

}