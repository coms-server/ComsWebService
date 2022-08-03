package coms.kw.ac.kr.server.controller.user;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.service.user.UserInformationService;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(UserListController.BASE_CONTEXT)
public class UserListController {
    public static final String BASE_CONTEXT = "/user";

    private final UserInformationService userInformationService;

    @Autowired
    public UserListController(UserInformationService uInfoService) {
        this.userInformationService = uInfoService;
    }

    @RequestMapping("/list")
    public String userList(Model model) {
        List<UserInformationVO> userList = userInformationService.getApprovedUserList();
        model.addAttribute("userList", userList);

        return ViewSelector.byLayoutType(model, LayoutType.USER_LIST);
    }
}
