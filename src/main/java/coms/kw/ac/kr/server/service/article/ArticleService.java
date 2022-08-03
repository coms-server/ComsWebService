package coms.kw.ac.kr.server.service.article;

import coms.kw.ac.kr.server.vo.article.ArticleVO;
import coms.kw.ac.kr.server.vo.statics.Board;

import java.util.List;

// TODO: javadoc 완성
public interface ArticleService {

    /* -------------------------------------------------------------------------- */
    /*                             Article CRUD Method                            */
    /* -------------------------------------------------------------------------- */

    /**
     * INSERT the given {@code ArticleVO} to the {@code b_article} table and return
     * the {@code article_idx} of the corresponding record.
     * 
     * @param article {@code ArticleVO} object that contains article data
     * @return Inserted record's {@code article_idx} or {@code null} for whatever
     *         reason it fails
     */
    public Integer insertArticle(ArticleVO article);

    /**
     * SELECT and return the article with the given {@code article_idx} in the
     * {@code b_article} table. Return null if {@code is_deleted} flag is true or no
     * record found.
     * 
     * @param articleIndex {@code article_idx} of target article
     * @return {@code ArticleVO} if a record exists, {@code null} if no record
     *         exists
     */
    public ArticleVO getArticle(int articleIndex);

    /**
     * UPDATE the record corresponding to a given {@code article_idx} in
     * {@code b_article} table.
     * 
     * @param article {@code ArticleVO} object that contains article data.
     * @return {@code true} on successful UPDATE or {@code false} for whatever
     *         reason it fails
     */
    public void updateArticle(ArticleVO article);

    /**
     * Set the record's {@code is_deleted} flag to true. This action does not
     * actually DELETE the record.
     * 
     * @param articleIndex {@code article_idx} of target article
     * @return {@code true} on successful change of {@code is_deleted} flag,
     *         {@code false} for whatever reason it fails
     */
    public void deleteArticle(int articleIndex);


    /* -------------------------------------------------------------------------- */
    /*                           Article Listing Method                           */
    /* -------------------------------------------------------------------------- */

    /**
     * Get the COUNT(*) in {@code b_article} table where {@code is_deleted} flag is
     * false and specified by given {@link Board}.
     * 
     * @param board Target {@code Board} object
     * @return Row count
     */
    public Integer getArticleCount(Board board);

    public List<ArticleVO> getArticleList(Board board, int articlePerPage, int page);

    public List<ArticleVO> getNoticeList(int boardIndex);

    /* ------------------------------- 게시글 뷰어 메서드 ------------------------------- */

    public int getRowNumber(Board board, int articleIndex);

    public List<ArticleVO> getSideArticle(Board board, int articleIndex);

    public List<ArticleVO> getAnswerList(int parentIndx);

    /* ----------------------------------- 투표 ----------------------------------- */

    public Integer vote(int articleIndex, int userIndex, boolean upVote);

    public Integer getVote(int articleIndex, int userIndex);

    public Integer getTotalVote(int articleIndex);

}