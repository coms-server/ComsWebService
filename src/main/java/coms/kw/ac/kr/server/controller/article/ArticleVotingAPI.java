package coms.kw.ac.kr.server.controller.article;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import coms.kw.ac.kr.server.controller.CommonHttpResponse;
import coms.kw.ac.kr.server.service.article.ArticleService;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.vo.article.ArticleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(ArticleVotingAPI.API_CONTEXT)
public class ArticleVotingAPI {

    public static final String API_CONTEXT = "/vote";

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ArticleVotingAPI.class);

    private final ArticleService articleService;
    private final AuthenticationFacade authFacade;

    @Autowired
    public ArticleVotingAPI(ArticleService articleService, AuthenticationFacade authFacade) {
        this.articleService = articleService;
        this.authFacade = authFacade;
    }

    // Get votes
    @RequestMapping(value = "/{articleIndex}", method = RequestMethod.GET)
    public final ResponseEntity<String> getVote(@PathVariable int articleIndex) {
        JsonNode node;
        try {
            int totalVote = articleService.getTotalVote(articleIndex);
            int userVote = articleService.getVote(articleIndex, authFacade.getUserIndex());
            node = mapper.createObjectNode().put("totalVote", totalVote).put("userVote", userVote);
        } catch (Exception exception) {
            logger.error("Failed to get vote.", exception);
            return CommonHttpResponse.BAD_REQUEST(null);
        }

        return CommonHttpResponse.OK(node.toPrettyString());
    }

    // Vote
    @RequestMapping(value = "/{articleIndex}", method = RequestMethod.POST)
    public final ResponseEntity<Integer> vote(@PathVariable int articleIndex, @RequestBody boolean upVote) {
        int userIndex = authFacade.getUserIndex();

        // Self-voting
        if (ArticleConfiguration.BLOCK_SELF_VOTING) {
            ArticleVO article = articleService.getArticle(articleIndex);
            int writer = article.getUser_idx();
            if (writer == userIndex)
                return CommonHttpResponse.BAD_REQUEST(Integer.class);
        }

        // Vote
        int totalVote;
        try {

            articleService.vote(articleIndex, userIndex, upVote);
            totalVote = articleService.getTotalVote(articleIndex);
        } catch (Exception exception) {
            logger.error("Failed to get vote.", exception);
            return CommonHttpResponse.BAD_REQUEST(null);
        }

        return CommonHttpResponse.OK(totalVote);
    }

}
