package coms.kw.ac.kr.server.controller.article;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.exception.implement.ResourceNotFoundException;
import coms.kw.ac.kr.server.service.article.ArticleService;
import coms.kw.ac.kr.server.service.article.AttachmentService;
import coms.kw.ac.kr.server.service.statics.StaticInformationService;
import coms.kw.ac.kr.server.vo.article.ArticleVO;
import coms.kw.ac.kr.server.vo.article.AttachmentVO;
import coms.kw.ac.kr.server.vo.statics.Board;
import coms.kw.ac.kr.server.vo.statics.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(ArticleConfiguration.BASE_CONTEXT)
public class ArticleReaderController {

    public static final String READER_URI = "/reader";
    public static final String COMMENT_URI = "/comment";

    private final ArticleService articleService;
    private final AttachmentService attachmentService;
    private final StaticInformationService staticInformation;

    @Autowired
    public ArticleReaderController(ArticleService articleService, AttachmentService attachmentService,
            StaticInformationService staticInformation) {
        this.articleService = articleService;
        this.attachmentService = attachmentService;
        this.staticInformation = staticInformation;
    }

    // NOTE: 글 리더
    @RequestMapping("/{context}" + READER_URI + "/{articleIndex}")
    public String reader(@PathVariable String context, @PathVariable int articleIndex, Model model) {
        // Get article
        ArticleVO article = articleService.getArticle(articleIndex);
        if (article == null)
            throw new ResourceNotFoundException();

        // Reader is not available on sub-article
        if (article.getIs_subarticle())
            return readerRedirect(context, article.getParent_idx());

        // Match board information
        Board board = staticInformation.findBoardByContext(context);
        BoardVO originalBoard = staticInformation.findBoardByIndex(article.getBoard_idx());
        if (board == null)
            return readerRedirect(article); // Board does not exist

        if (board.isRoot() && board.getIndex() != originalBoard.getRoot_idx())
            return readerRedirect(article); // Wrong root context provided

        if (!board.isRoot() && board.getIndex() != article.getBoard_idx())
            return readerRedirect(article); // Wrong board context provided

        // Prepare view
        int page = ((articleService.getRowNumber(board, articleIndex) - 1) / ArticleConfiguration.ARTICLE_PER_PAGE) + 1;
        List<AttachmentVO> attachments = attachmentService.getAttachments(articleIndex);
        List<ArticleVO> sideArticles = articleService.getSideArticle(board, articleIndex);

        model.addAttribute("mainArticle", article);
        model.addAttribute("page", page);
        model.addAttribute("attachments", attachments);
        model.addAttribute("sideArticles", sideArticles);
        if (board.isQna()) {
            List<ArticleVO> answers = articleService.getAnswerList(articleIndex);
            model.addAttribute("qnaMode", true);
            model.addAttribute("answers", answers);
        }
        ArticleTools.addBoardInformation(board, model);

        return ViewSelector.byLayoutType(model, LayoutType.READER);
    }

    private String readerRedirect(ArticleVO article) {
        Board originalBoard = staticInformation.findBoardByIndex(article.getBoard_idx());
        return readerRedirect(originalBoard.getContext(), article.getArticle_idx());
    }

    private String readerRedirect(String context, int articleIndex) {
        return "redirect:" + getReaderURL(context, articleIndex);
    }

    public static String getReaderURL(String context, int articleIndex) {
        StringBuilder builder = new StringBuilder();
        builder.append(ArticleConfiguration.BASE_CONTEXT);
        builder.append("/");
        builder.append(context);
        builder.append(READER_URI);
        builder.append("/");
        builder.append(articleIndex);
        return builder.toString();
    }

}
