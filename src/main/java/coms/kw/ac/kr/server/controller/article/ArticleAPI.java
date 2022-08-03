package coms.kw.ac.kr.server.controller.article;

import coms.kw.ac.kr.server.controller.CommonHttpResponse;
import coms.kw.ac.kr.server.service.article.ArticleService;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.statics.StaticInformationService;
import coms.kw.ac.kr.server.service.user.NotificationService;
import coms.kw.ac.kr.server.vo.article.ArticleVO;
import coms.kw.ac.kr.server.vo.statics.Board;
import coms.kw.ac.kr.server.vo.statics.BoardVO;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/***
 * 게시판의 글 쓰기, 수정, 삭제 API.
 * <p>
 * 경로 : {@literal *}/article
 */
@Controller
@RequestMapping(ArticleAPI.API_CONTEXT)
public class ArticleAPI {

    public static final String API_CONTEXT = "/article";
    public static final String ARTICLE_URI = "";

    private static final Logger logger = LoggerFactory.getLogger(ArticleAPI.class);

    private final ArticleService articleService;
    private final StaticInformationService staticInformation;
    private final NotificationService notificationService;
    private final AuthenticationFacade authFacade;

    @Autowired
    public ArticleAPI(ArticleService articleService, StaticInformationService staticInformation,
            NotificationService notificationService, AuthenticationFacade authFacade) {
        this.articleService = articleService;
        this.staticInformation = staticInformation;
        this.notificationService = notificationService;
        this.authFacade = authFacade;
    }

    // 새 게시글 등록
    @RequestMapping(value = ARTICLE_URI, method = RequestMethod.POST)
    public final ResponseEntity<Integer> insertArticle(ArticleVO article) {
        // Value check
        Integer boardIndex = article.getBoard_idx();
        if (boardIndex == null)
            return CommonHttpResponse.BAD_REQUEST(Integer.class);

        Board board = staticInformation.findBoardByIndex(boardIndex);
        if (board == null || board.isRoot())
            return CommonHttpResponse.BAD_REQUEST(Integer.class);

        // Authoriazation check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (!ArticleTools.isAuthorized(user, (BoardVO) board))
            return CommonHttpResponse.UNAUTHORIZED(Integer.class);

        if (article.getIs_notice() && !user.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED(Integer.class);

        // Insert article
        Integer articleIndex;
        try {
            article.setUser_idx(authFacade.getUserIndex());
            article.setIs_deleted(false);
            articleIndex = articleService.insertArticle(article);
            if (articleIndex == null)
                CommonHttpResponse.BAD_REQUEST(Integer.class);
        } catch (Exception exception) {
            logger.error("Failed to insert article.", exception);
            return CommonHttpResponse.BAD_REQUEST(null);
        }

        // Send notification
        if (article.getIs_subarticle()) {
            int parentIndex = article.getParent_idx();
            int parentWriter = articleService.getArticle(parentIndex).getUser_idx();

            StringBuilder builder = new StringBuilder();
            builder.append("답변:");
            if (article.getTitle().length() < 10)
                builder.append(article.getTitle());
            else
                builder.append(article.getTitle().substring(0, 10));

            String context = staticInformation.findBoardByIndex(boardIndex).getContext();
            String url = ArticleReaderController.getReaderURL(context, articleIndex);
            notificationService.createNotification(parentWriter, 3, parentIndex, builder.toString(), url);
        }

        return CommonHttpResponse.OK(articleIndex.intValue());
    }

    // 게시글 업데이트
    @RequestMapping(value = ARTICLE_URI, method = RequestMethod.PUT)
    public final ResponseEntity<Integer> updateArticle(@RequestBody ArticleVO article) {
        // Value check
        Integer articleIndex = article.getArticle_idx();
        if (articleIndex == null)
            return CommonHttpResponse.BAD_REQUEST(Integer.class);

        ArticleVO originalArticle = articleService.getArticle(articleIndex);
        if (originalArticle == null)
            return CommonHttpResponse.BAD_REQUEST(Integer.class);

        // Authorization check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (!ArticleTools.isAuthorized(user, originalArticle))
            return CommonHttpResponse.UNAUTHORIZED(Integer.class);
        else if (article.getIs_notice() && !user.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED(Integer.class);

        // Update article
        try {
            articleService.updateArticle(article);
        } catch (Exception exception) {
            logger.error("Failed to update article.", exception);
            return CommonHttpResponse.BAD_REQUEST(null);
        }

        return CommonHttpResponse.OK(articleIndex.intValue());
    }

    // 게시글 삭제
    @RequestMapping(value = ARTICLE_URI, method = RequestMethod.DELETE)
    public final ResponseEntity<Object> deleteArticle(@RequestParam(name = "article_idx") int articleIndex) {
        // Value check
        ArticleVO article = articleService.getArticle(articleIndex);
        if (article == null)
            return CommonHttpResponse.BAD_REQUEST;

        // Authorization check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (!ArticleTools.isAuthorized(user, article))
            return CommonHttpResponse.UNAUTHORIZED;

        // Delete article
        try {
            articleService.deleteArticle(articleIndex);

            // Delete notification
            if(article.getIs_subarticle()) {
                int parentIndex = article.getParent_idx();
                int parentWriter = articleService.getArticle(parentIndex).getUser_idx();
                int target = notificationService.getNotification(parentWriter, 3, parentIndex).getNoti_idx();

                notificationService.deleteNotification(target);
            }
        } catch (Exception exception) {
            logger.error("Failed to delete article.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }

        return CommonHttpResponse.OK;
    }

}