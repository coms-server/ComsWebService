package coms.kw.ac.kr.server.controller.article;

import coms.kw.ac.kr.server.controller.CommonHttpResponse;
import coms.kw.ac.kr.server.service.article.ArticleService;
import coms.kw.ac.kr.server.service.article.AttachmentService;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping(AttachmentAPI.API_CONTEXT)
public class AttachmentAPI {

    public static final String API_CONTEXT = "/attachment";
    public static final String UPLOAD_URI = "/upload";
    
    private static final Logger logger = LoggerFactory.getLogger(AttachmentAPI.class);

    private final ArticleService articleService;
    private final AttachmentService attachmentService;
    private final AuthenticationFacade authFacade;

    public AttachmentAPI(ArticleService articleService, AttachmentService attachmentService,
            AuthenticationFacade authFacade) {
        this.articleService = articleService;
        this.attachmentService = attachmentService;
        this.authFacade = authFacade;
    }

    @RequestMapping(value = UPLOAD_URI, method = RequestMethod.POST)
    public ResponseEntity<Object> uploadAttachment(@RequestParam("attachment") List<MultipartFile> files,
            @RequestParam("article_idx") int articleIndex) {

        // Authorization check
        if (!ArticleTools.isAuthorized(authFacade.getPrincipal(), articleService.getArticle(articleIndex)))
            return CommonHttpResponse.UNAUTHORIZED;

        // Upload attachment
        try {
            attachmentService.clearAttachments(articleIndex);
            for (MultipartFile f : files)
                attachmentService.uploadAttachment(articleIndex, f);
        } catch (Exception exception) {
            logger.error("Failed upload attachment.", exception);
            return CommonHttpResponse.BAD_REQUEST(null);
        }

        return CommonHttpResponse.OK;
    }

}