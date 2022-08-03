package coms.kw.ac.kr.server.service.article;

import coms.kw.ac.kr.server.dao.ArticleDAO;
import coms.kw.ac.kr.server.dao.UserInformationDAO;
import coms.kw.ac.kr.server.service.article.contentwrapper.Converter;
import coms.kw.ac.kr.server.service.article.contentwrapper.Delta;
import coms.kw.ac.kr.server.service.article.contentwrapper.SerializedContent;
import coms.kw.ac.kr.server.service.tools.DateStringParser;
import coms.kw.ac.kr.server.vo.article.ArticleVO;
import coms.kw.ac.kr.server.vo.statics.Board;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDAO articleDAO;

    private final UserInformationDAO userInformationDAO;
    private final ContentDisassembler disassmbler;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public ArticleServiceImpl(ArticleDAO articleDAO, UserInformationDAO userInformationDAO,
            PlatformTransactionManager transactionManager, @Value("${local-storage.root}") String LOCAL_STORAGE_ROOT) {
        this.articleDAO = articleDAO;
        this.userInformationDAO = userInformationDAO;
        this.disassmbler = new ContentDisassembler(LOCAL_STORAGE_ROOT);
        this.transactionManager = transactionManager;
    }

    // ANCHOR 기본 메서드
    @Override
    public Integer insertArticle(ArticleVO article) {
        // Prepare initial insertion
        UserInformationVO user = userInformationDAO.getUserInformation(article.getUser_idx());
        String content = article.getContent();
        article.setContent(" ");
        article.setAttributes(" ");
        article.setUser_idx(user.getUser_idx());
        article.setName(user.getTerm() + "기 " + user.getName());

        // Isolation
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName("ArticleService insert method");
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            // Initial insertion (article index occupation)
            articleDAO.insertArticle(article);
            // Serialize content
            article.setContent(content);
            serializeContent(article);
            // Update article
            articleDAO.updateArticle(article);
        } catch (Exception exception) {
            transactionManager.rollback(status);
            return null;
        }
        transactionManager.commit(status);

        return article.getArticle_idx();
    }

    @Override
    public ArticleVO getArticle(int articleIndex) {
        ArticleVO article = articleDAO.getArticle(articleIndex);
        if (article == null)
            return null;

        deserializeContent(article);
        DateStringParser.prettifyDateString(article);

        return article;
    }

    @Override
    public void updateArticle(ArticleVO article) {
        serializeContent(article);

        articleDAO.updateArticle(article);
    }

    @Override
    public void deleteArticle(int articleIndex) {
        articleDAO.deleteArticle(articleIndex);
    }

    // ANCHOR 게시글 목록 메서드
    @Override
    public Integer getArticleCount(Board board) {
        if (board.isRoot())
            return articleDAO.articleCountOnRoot(board.getIndex());
        else
            return articleDAO.articleCountOnBoard(board.getIndex());
    }

    @Override
    public List<ArticleVO> getArticleList(Board board, int articlePerPage, int page) {
        int offset = (page - 1) * articlePerPage;

        List<ArticleVO> list;
        if (board.isRoot())
            list = articleDAO.getArticleListFromRoot(board.getIndex(), offset, articlePerPage);
        else
            list = articleDAO.getArticleListFromBoard(board.getIndex(), offset, articlePerPage);

        DateStringParser.prettifyDateString(list);
        return list;
    }

    @Override
    public List<ArticleVO> getNoticeList(int boardIndex) {
        List<ArticleVO> list = articleDAO.getNoticeList(boardIndex);
        DateStringParser.prettifyDateString(list);
        return list;
    }

    // ANCHOR 게시글 뷰어 메서드
    @Override
    public int getRowNumber(Board board, int articleIndex) {
        if (board.isRoot())
            return articleDAO.getRowNumberFromRoot(board.getIndex(), articleIndex);
        else
            return articleDAO.getRowNumberFromBoard(board.getIndex(), articleIndex);
    }

    @Override
    public List<ArticleVO> getSideArticle(Board board, int articleIndex) {
        int rowNumber;
        List<ArticleVO> list;

        if (board.isRoot()) {
            rowNumber = articleDAO.getRowNumberFromRoot(board.getIndex(), articleIndex);
            list = articleDAO.getSideArticlesFromRoot(board.getIndex(), rowNumber);
        } else {
            rowNumber = articleDAO.getRowNumberFromBoard(board.getIndex(), articleIndex);
            list = articleDAO.getSideArticlesFromBoard(board.getIndex(), rowNumber);
        }
        DateStringParser.prettifyDateString(list);

        return list;
    }

    @Override
    public List<ArticleVO> getAnswerList(int parentIndex) {
        List<ArticleVO> answers = articleDAO.getAnswerList(parentIndex);
        DateStringParser.prettifyDateString(answers);
        for (ArticleVO vo : answers)
            deserializeContent(vo);

        return answers;
    }

    // ANCHOR 투표
    @Override
    public Integer vote(int articleIndex, int userIndex, boolean upVote) {
        int prevVote = getVote(articleIndex, userIndex);
        if (prevVote == -1)
            articleDAO.insertVote(articleIndex, userIndex, upVote);
        else {
            boolean prevUpvoted = prevVote == 1 ? true : false;
            if (prevUpvoted != upVote)
                articleDAO.updateVote(articleIndex, userIndex, upVote);
            else
                articleDAO.deleteVote(articleIndex, userIndex);
        }

        return getTotalVote(articleIndex);
    }

    @Override
    public Integer getVote(int articleIndex, int userIndex) {
        Boolean vote = articleDAO.getVote(articleIndex, userIndex);
        if (vote == null) {
            return -1;
        } else {
            return vote ? 1 : 0;
        }
    }

    @Override
    public Integer getTotalVote(int articleIndex) {
        int totVote = 0;
        List<Boolean> votes = articleDAO.getVoteList(articleIndex);
        for (Boolean vote : votes) {
            if (vote) {
                totVote++;
            } else {
                totVote--;
            }
        }

        return totVote;
    }

    private void serializeContent(ArticleVO article) {
        List<Delta> deltas = Converter.jsonToDelta(article.getContent());
        disassmbler.disassembleMedia(deltas, article.getArticle_idx());
        SerializedContent serialized = Converter.deltaToString(deltas);
        article.fromSerializedContent(serialized);
    }

    private void deserializeContent(ArticleVO article) {
        SerializedContent serialized = article.toSerializedContent();
        List<Delta> deltas = Converter.stringToDelta(serialized);
        String content = Converter.deltaToJson(deltas);
        article.setContent(content);
    }

}