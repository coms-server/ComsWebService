package coms.kw.ac.kr.server.service.article;

import coms.kw.ac.kr.server.vo.article.CommentVO;

import java.util.List;

public interface CommentService {

    public void insertComment(CommentVO comment);

    public CommentVO getComment(int commentIndex);

    public void updateComment(CommentVO comment);

    public void deleteComment(int commentIndex);

    public List<CommentVO> getAllComments(int articleIndex);

    public Integer getParentArticleIndex(int commentIndex);

}