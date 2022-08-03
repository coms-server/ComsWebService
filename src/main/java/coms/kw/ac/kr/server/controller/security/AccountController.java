package coms.kw.ac.kr.server.controller.security;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.service.statics.StaticInformationService;
import coms.kw.ac.kr.server.vo.statics.MajorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(AccountController.BASE_CONTEXT)
public class AccountController {

    public static final String BASE_CONTEXT = "/account";

    private final StaticInformationService staticInformation;

    @Autowired
    public AccountController(StaticInformationService staticInfoService) {
        this.staticInformation = staticInfoService;
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        List<MajorVO> majorList = staticInformation.getMajorList();
        List<String> collegeList = new ArrayList<>();
        List<List<MajorVO>> collegeMatrix = new ArrayList<>();

        for (MajorVO major : majorList) {
            if (collegeList.indexOf(major.getCollege()) < 0) {
                collegeList.add(major.getCollege());

                collegeMatrix.add(new ArrayList<>());
            }

            collegeMatrix.get(collegeList.indexOf(major.getCollege())).add(major);
        }

        model.addAttribute("collegeMatrix", collegeMatrix);

        return ViewSelector.byLayoutType(model, LayoutType.SIGNUP);
    }
}