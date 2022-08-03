package coms.kw.ac.kr.server.controller;

import coms.kw.ac.kr.server.service.article.ArticleService;
import coms.kw.ac.kr.server.vo.article.ArticleVO;
import coms.kw.ac.kr.server.vo.statics.RootBoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainPageController {

    private static final int ARTICLE_PER_PAGE = 6;

    private final ArticleService articleService;
    private final String PROJECT_VERSION;

    @Autowired
    public MainPageController(ArticleService articleService, @Value("${version}") String PROJECT_VERSION) {
        this.articleService = articleService;
        this.PROJECT_VERSION = PROJECT_VERSION;
    }

    @RequestMapping("/")
    public String rootRedirect() {
        return ViewSelector.REDIRECT_TO_INDEX;
    }

    @RequestMapping("/index")
    public String mainPage(Model model) {
        RootBoardVO notice = new RootBoardVO();
        notice.setRoot_idx(1);
        RootBoardVO community = new RootBoardVO();
        community.setRoot_idx(2);
        RootBoardVO qna = new RootBoardVO();
        qna.setRoot_idx(3);
        RootBoardVO activity = new RootBoardVO();
        activity.setRoot_idx(5);

        List<ArticleVO> articleNotice = articleService.getArticleList(notice, ARTICLE_PER_PAGE, 1);
        List<ArticleVO> articleCommunity = articleService.getArticleList(community, ARTICLE_PER_PAGE, 1);
        List<ArticleVO> articleQna = articleService.getArticleList(qna, ARTICLE_PER_PAGE, 1);
        List<ArticleVO> articleActivity = articleService.getArticleList(activity, ARTICLE_PER_PAGE, 1);

        model.addAttribute("showWriteDate", true);
        model.addAttribute("articleNotice", articleNotice);
        model.addAttribute("articleCommunity", articleCommunity);
        model.addAttribute("articleQna", articleQna);
        model.addAttribute("articleActivity", articleActivity);

        return ViewSelector.byLayoutType(model, LayoutType.MAIN);
    }

    // NOTE: 이런 페이지들 모아서 별도의 페이지 컨트롤러로 분리할지 고려해볼것
    @RequestMapping("/about-coms")
    public String aboutComsPage(Model model) {
        model.addAttribute("version", PROJECT_VERSION);
        return ViewSelector.byLayoutType(model, LayoutType.ABOUT_COMS);
    }

    @RequestMapping("/information-policy")
    public String informationPoicyPage(Model model) {
        return ViewSelector.byLayoutType(model, LayoutType.INFORMATION_POLICY);
    }

}