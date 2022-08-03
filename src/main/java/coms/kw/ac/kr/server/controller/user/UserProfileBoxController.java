package coms.kw.ac.kr.server.controller.user;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.service.user.UserAuthenticationService;
import coms.kw.ac.kr.server.service.user.UserInformationService;
import coms.kw.ac.kr.server.vo.event.EventVO;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(UserProfileBoxController.BASE_CONETXT)
public class UserProfileBoxController {
    public static final String BASE_CONETXT = "/user";

    private final UserAuthenticationService userAuthenticationService;
    private final UserInformationService userInformationService;

    @Autowired
    public UserProfileBoxController(UserAuthenticationService userAuthenticationService,
            UserInformationService userInformationService) {
        this.userAuthenticationService = userAuthenticationService;
        this.userInformationService = userInformationService;
    }

    @RequestMapping("/profile/{userIndex}")
    public String profileViewer(@PathVariable int userIndex, Model model) {
        bigProfileBox(userIndex, model);
        model.addAttribute("standAlone", true);

        return ViewSelector.byLayoutType(model, LayoutType.PROFILE);
    }

    // ProfileViewer without index wrapping
    @RequestMapping("/big-profile/{userIndex}")
    public String bigProfileBox(@PathVariable int userIndex, Model model) {
        UserInformationVO userInfo = userInformationService.getUserInformation(userIndex);
        List<EventVO> eventList = userInformationService.getUserParticipatedEventList(userIndex);

        if (userInfo == null) {
            // Try to find user from authentication
            UserAuthenticationVO userAuth = userAuthenticationService.getUserAuthentication(userIndex);
            if (userAuth == null) {
                UserInformationVO temp = new UserInformationVO();
                temp.setUser_idx(-1);
                temp.setName("");
                model.addAttribute("userInfo", temp);
            } else {
                model.addAttribute("userInfo", userAuth);
            }
            model.addAttribute("noInformation", true);
        } else {
            model.addAttribute("userInfo", userInfo);
        }
        model.addAttribute("eventList", eventList);

        return ViewSelector.layoutOnly(LayoutType.PROFILE);
    }

    @RequestMapping("/small-profile/{userIndex}")
    public String smallProfileBox(@PathVariable int userIndex, Model model) {
        UserInformationVO userInfo = userInformationService.getUserInformation(userIndex);
        if (userInfo == null) {
            // Try to find user from authentication
            UserAuthenticationVO userAuth = userAuthenticationService.getUserAuthentication(userIndex);
            if (userAuth == null) {
                UserInformationVO temp = new UserInformationVO();
                temp.setUser_idx(-1);
                temp.setName("");
                model.addAttribute("userInfo", temp);
            } else {
                model.addAttribute("userInfo", userAuth);
            }
        } else {
            model.addAttribute("userInfo", userInfo);
        }

        return ViewSelector.layoutOnly(LayoutType.SMALL_PROFILE);
    }

}
