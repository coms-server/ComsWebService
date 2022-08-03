package coms.kw.ac.kr.server.controller.article;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.exception.implement.ResourceNotFoundException;
import coms.kw.ac.kr.server.service.article.ArticleService;
import coms.kw.ac.kr.server.service.statics.StaticInformationService;
import coms.kw.ac.kr.server.vo.article.ArticleVO;
import coms.kw.ac.kr.server.vo.statics.Board;
import coms.kw.ac.kr.server.vo.statics.RootBoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(ArticleConfiguration.BASE_CONTEXT)
public class ArticleListingController {

    public static final String LIST_URI = "/list";

    private final ArticleService articleService;
    private final StaticInformationService staticInformation;

    @Autowired
    public ArticleListingController(ArticleService articleService, StaticInformationService staticInformation) {
        this.articleService = articleService;
        this.staticInformation = staticInformation;
    }

    @RequestMapping("/")
    public String rootRedirect() {
        return ViewSelector.REDIRECT_TO_INDEX;
    }

    @RequestMapping(value = { "/{context}/", "/{context}" + LIST_URI + "/" })
    public String rootRedirect(@PathVariable String context) {
        return listRedirect(context);
    }

    // NOTE: 글 목록
    @RequestMapping("/{context}" + LIST_URI + "/{page}")
    public String list(@PathVariable String context, @PathVariable int page, Model model) {
        // Get board information
        Board board = staticInformation.findBoardByContext(context);
        if (board == null)
            throw new ResourceNotFoundException();

        // Redirect to first page if page is out of range
        int articleCount = articleService.getArticleCount(board);
        int maxPage = (int) ((articleCount - 1) / ArticleConfiguration.ARTICLE_PER_PAGE) + 1;
        if (page > maxPage || page < 1)
            return listRedirect(context);

        // List notice on first page
        if(page == 1){
            List<ArticleVO> notices;
            if(board.isRoot()) {
                notices = new ArrayList<>();
                RootBoardVO root = (RootBoardVO) board; 
                root.getChild_boards().stream().forEach(b->notices.addAll(articleService.getNoticeList(b.getIndex())));
            } else {
                notices = articleService.getNoticeList(board.getIndex());
            }
            model.addAttribute("notices", notices);
        }

        // Prepare view
        List<ArticleVO> articles = articleService.getArticleList(board, ArticleConfiguration.ARTICLE_PER_PAGE, page);
        Pager pager = new Pager(articleCount, ArticleConfiguration.ARTICLE_PER_PAGE, page);
        boolean is_root = board.isRoot();
        model.addAttribute("articles", articles);
        model.addAttribute("pager", pager);
        model.addAttribute("is_root", is_root);
        ArticleTools.addBoardInformation(board, model);

        return ViewSelector.byLayoutType(model, LayoutType.BOARD);
    }

    private String listRedirect(String context) {
        StringBuilder builder = new StringBuilder();
        builder.append("redirect:");
        builder.append(ArticleConfiguration.BASE_CONTEXT);
        builder.append("/");
        builder.append(context);
        builder.append(LIST_URI);
        builder.append("/1");
        return builder.toString();
    }

}