package coms.kw.ac.kr.server.controller.article;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.exception.implement.DeceptiveRequestRouting;
import coms.kw.ac.kr.server.exception.implement.ResourceNotFoundException;
import coms.kw.ac.kr.server.exception.implement.UnauthorizedException;
import coms.kw.ac.kr.server.service.article.ArticleService;
import coms.kw.ac.kr.server.service.article.AttachmentService;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
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
public class ArticleEditorController {

    public static final String EDITOR_URI = "/editor";
    public static final String ANSWER_URI = "/answer";

    private final ArticleService articleService;
    private final AttachmentService attachmentService;
    private final StaticInformationService staticInformation;
    private final AuthenticationFacade authFacade;

    @Autowired
    public ArticleEditorController(ArticleService articleService, AttachmentService attachmentService,
            StaticInformationService staticInformation, AuthenticationFacade authFacade) {
        this.articleService = articleService;
        this.attachmentService = attachmentService;
        this.staticInformation = staticInformation;
        this.authFacade = authFacade;
    }

    // NOTE: 새로운 글 에디터
    @RequestMapping("/{context}" + EDITOR_URI)
    public String editor(@PathVariable String context, Model model) {
        // Editor not available in root context or unknown context
        Board board = staticInformation.findBoardByContext(context);
        if (board == null || board.isRoot())
            throw new ResourceNotFoundException();

        // Authorization check
        if (!ArticleTools.isAuthorized(authFacade.getPrincipal(), (BoardVO) board))
            throw new UnauthorizedException();

        // Prepare view
        model.addAttribute("is_subarticle", false);

        return getEditorView(board, model);
    }

    // NOTE: 이전에 작성된 글 에디터
    @RequestMapping("/{context}" + EDITOR_URI + "/{articleIndex}")
    public String editor(@PathVariable String context, @PathVariable int articleIndex, Model model) {
        // Get article
        ArticleVO article = articleService.getArticle(articleIndex);
        if (article == null)
            throw new ResourceNotFoundException();

        // Match board information
        Board board = staticInformation.findBoardByContext(context);
        if (board == null || board.isRoot() || article.getBoard_idx() != board.getIndex())
            return editorRedirect(article, false);

        // Authorization check
        if (!ArticleTools.isAuthorized(authFacade.getPrincipal(), article))
            throw new UnauthorizedException();

        // Preapre view
        List<AttachmentVO> attachments = attachmentService.getAttachments(articleIndex);
        model.addAttribute("article", article);
        model.addAttribute("attachments", attachments);
        if (article.getIs_subarticle()) {
            ArticleVO parentArticle = articleService.getArticle(article.getParent_idx());
            model.addAttribute("is_subarticle", true);
            model.addAttribute("parentArticle", parentArticle);
        }

        return getEditorView(board, model);
    }

    // NOTE: QNA게시판 새로운 답글 에디터
    @RequestMapping("/{context}" + ANSWER_URI + "/{articleIndex}")
    public String answer(@PathVariable String context, @PathVariable int articleIndex, Model model) {
        // Get article
        ArticleVO article = articleService.getArticle(articleIndex);
        if (article == null)
            throw new ResourceNotFoundException();

        // Match board information
        Board board = staticInformation.findBoardByContext(context);
        if (board == null || board.isRoot() || article.getBoard_idx() != board.getIndex())
            return editorRedirect(article, true);

        if (!((BoardVO) board).getRoot().getIs_qna())
            throw new DeceptiveRequestRouting();

        // Answer mode does not require authorization check because the original
        // question requires authorization

        // Prepare view
        model.addAttribute("is_subarticle", true);
        model.addAttribute("parentArticle", article);

        return getEditorView(board, model);
    }

    private String getEditorView(Board board, Model model) {
        ArticleTools.addBoardInformation(board, model);
        return ViewSelector.byLayoutType(model, LayoutType.EDITOR);
    }

    private String editorRedirect(ArticleVO article, boolean answerMode) {
        BoardVO originalBoard = staticInformation.findBoardByIndex(article.getBoard_idx());
        StringBuilder builder = new StringBuilder();
        builder.append("redirect:");
        builder.append(ArticleConfiguration.BASE_CONTEXT);
        builder.append("/");
        builder.append(originalBoard.getContext());
        if (answerMode)
            builder.append(ANSWER_URI);
        else
            builder.append(EDITOR_URI);
        builder.append("/");
        builder.append(article.getArticle_idx());
        return builder.toString();
    }

}
