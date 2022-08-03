package coms.kw.ac.kr.server.dao;

import coms.kw.ac.kr.server.vo.article.ArticleVO;

import java.util.List;

public interface ArticleDAO {

    // 기본 메서드
    public void insertArticle(ArticleVO vo);

    public ArticleVO getArticle(int article_idx);

    public void updateArticle(ArticleVO vo);

    public void deleteArticle(int article_idx);

    // 게시글 목록 메서드
    public Integer articleCountOnBoard(int board_idx);

    public Integer articleCountOnRoot(int root_idx);

    public List<ArticleVO> getArticleListFromBoard(int board_idx, int offset, int count);

    public List<ArticleVO> getArticleListFromRoot(int root_idx, int offset, int count);

    public List<ArticleVO> getNoticeList(int board_idx);

    // 게시글 뷰어 메서드
    public Integer getRowNumberFromBoard(int board_idx, int article_idx);

    public Integer getRowNumberFromRoot(int root_idx, int article_idx);

    public List<ArticleVO> getSideArticlesFromBoard(int board_idx, int row_number);

    public List<ArticleVO> getSideArticlesFromRoot(int root_idx, int row_number);

    public List<ArticleVO> getAnswerList(int parent_idx);

    // 투표
    public void insertVote(int article_idx, int user_idx, Boolean up_vote);

    public Boolean getVote(int article_idx, int user_idx);

    public List<Boolean> getVoteList(int article_idx);

    public void updateVote(int article_idx, int user_idx, Boolean up_vote);

    public void deleteVote(int article_idx, int user_idx);

}