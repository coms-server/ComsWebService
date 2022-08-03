package coms.kw.ac.kr.server.controller.article;

import coms.kw.ac.kr.server.controller.CommonHttpResponse;
import coms.kw.ac.kr.server.service.article.ArticleService;
import coms.kw.ac.kr.server.service.article.CommentService;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.statics.StaticInformationService;
import coms.kw.ac.kr.server.service.user.NotificationService;
import coms.kw.ac.kr.server.vo.article.ArticleVO;
import coms.kw.ac.kr.server.vo.article.CommentVO;
import coms.kw.ac.kr.server.vo.statics.BoardVO;
import coms.kw.ac.kr.server.vo.user.NotificationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(CommentAPI.API_CONTEXT)
public class CommentAPI {

    public static final String API_CONTEXT = "/comment";

    private static final Logger logger = LoggerFactory.getLogger(CommentAPI.class);

    private final ArticleService articleService;
    private final CommentService commentService;
    private final StaticInformationService staticInformation;
    private final NotificationService notificationService;
    private final AuthenticationFacade authFacade;

    @Autowired
    public CommentAPI(ArticleService articleService, CommentService commentService,
            StaticInformationService staticInformation, NotificationService notificationService,
            AuthenticationFacade authFacade) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.staticInformation = staticInformation;
        this.notificationService = notificationService;
        this.authFacade = authFacade;
    }

    // 신규 댓글 작성
    @RequestMapping(value = "", method = RequestMethod.POST)
    public final ResponseEntity<Object> addComment(CommentVO comment) {
        // Value check
        Integer articleIndex = comment.getArticle_idx();
        if (articleIndex == null)
            return CommonHttpResponse.BAD_REQUEST;

        ArticleVO article = articleService.getArticle(articleIndex);
        if (article == null)
            return CommonHttpResponse.BAD_REQUEST;

        // Commenting on important board(or notice) is not allowed
        BoardVO board = staticInformation.findBoardByIndex(article.getBoard_idx());
        if (board == null || board.isImportant() || article.getIs_notice())
            return CommonHttpResponse.BAD_REQUEST;

        // Insert comment
        try {
            comment.setUser_idx(authFacade.getUserIndex());
            commentService.insertComment(comment);
        } catch (Exception exception) {
            logger.error("Failed to insert comment.", exception);
            return CommonHttpResponse.BAD_REQUEST(null);
        }

        // Send notification
        int targetUser = -1;
        int referFlag = 0;
        int referIndex = 0;
        if (article.getUser_idx() != comment.getUser_idx()) {
            targetUser = article.getUser_idx();
            referFlag = 1;
            referIndex = articleIndex;
        } else if (comment.getParent_idx() != null) {
            int parentCommentIndex = comment.getParent_idx();
            int parentWriter = commentService.getComment(parentCommentIndex).getUser_idx();
            if (parentWriter != comment.getUser_idx()) {
                targetUser = parentWriter;
                referFlag = 2;
                referIndex = parentCommentIndex;
            }
        }
        if (targetUser != -1) {
            StringBuilder builder = new StringBuilder();
            builder.append("댓글: ");
            if (comment.getContent().length() < 10)
                builder.append(comment.getContent());
            else
                builder.append(comment.getContent().substring(0, 10));
            String url = ArticleReaderController.getReaderURL(board.getContext(), articleIndex);

            notificationService.createNotification(targetUser, referFlag, referIndex, builder.toString(), url);
        }

        return CommonHttpResponse.OK;
    }

    // 댓글 업데이트
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public final ResponseEntity<Object> updateComment(@RequestBody CommentVO comment) {
        // Value check
        Integer commentIndex = comment.getComment_idx();
        if (commentIndex == null)
            return CommonHttpResponse.BAD_REQUEST;

        CommentVO originalComment = commentService.getComment(commentIndex);
        if (originalComment == null)
            return CommonHttpResponse.BAD_REQUEST;

        // Authorization check
        if (!ArticleTools.isAuthorized(authFacade.getPrincipal(), originalComment))
            return CommonHttpResponse.UNAUTHORIZED;

        // Update comment
        try {
            commentService.updateComment(comment);
        } catch (Exception exception) {
            logger.error("Failed to update comment.", exception);
            return CommonHttpResponse.BAD_REQUEST(null);
        }

        return CommonHttpResponse.OK;
    }

    // 댓글 삭제
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public final ResponseEntity<Object> deleteComment(@RequestParam(name = "comment_idx") int commentIndex) {
        // Value check
        CommentVO originalComment = commentService.getComment(commentIndex);
        if (originalComment == null)
        return CommonHttpResponse.BAD_REQUEST;
        
        // Authorization check
        if (!ArticleTools.isAuthorized(authFacade.getPrincipal(), originalComment))
        return CommonHttpResponse.UNAUTHORIZED;
        
        try {
            // Delete comment
            commentService.deleteComment(commentIndex);
        } catch (Exception exception) {
            logger.error("Failed to delete comment.", exception);
            return CommonHttpResponse.BAD_REQUEST(null);
        }
        
        // Delete noti
        NotificationVO targetNoti;
        int referIndex;
        int targetUser;
        
        if(originalComment.getParent_idx() == null) {
            referIndex = originalComment.getArticle_idx();
            targetUser = articleService.getArticle(referIndex).getUser_idx();
            targetNoti = notificationService.getNotification(targetUser, 1, referIndex);
        } else {
            referIndex = originalComment.getParent_idx();
            targetUser = commentService.getComment(referIndex).getUser_idx();
            targetNoti = notificationService.getNotification(targetUser, 2, referIndex);
        }
        
        notificationService.deleteNotification(targetNoti.getNoti_idx());

        return CommonHttpResponse.OK;
    }
}
