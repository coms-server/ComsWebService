package coms.kw.ac.kr.server.service.article;

import coms.kw.ac.kr.server.dao.CommentDAO;
import coms.kw.ac.kr.server.dao.UserInformationDAO;
import coms.kw.ac.kr.server.service.tools.DateStringParser;
import coms.kw.ac.kr.server.vo.article.CommentVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;
    private final UserInformationDAO userInformationDAO;

    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO, UserInformationDAO userInformationDAO ) {
        this.commentDAO = commentDAO;
        this.userInformationDAO = userInformationDAO;
    }

    @Override
    public void insertComment(CommentVO comment) {
        UserInformationVO user = userInformationDAO.getUserInformation(comment.getUser_idx());
        comment.setName(user.getName());

        commentDAO.insertComment(comment);
    }

    @Override
    public CommentVO getComment(int commentIndex) {
        return commentDAO.getComment(commentIndex);
    }

    @Override
    public void updateComment(CommentVO comment) {
        commentDAO.updateComment(comment);
    }

    @Override
    public void deleteComment(int commentIndex) {
        commentDAO.deleteComment(commentIndex);
    }

    @Override
    public List<CommentVO> getAllComments(int articleIndex) {
        List<CommentVO> comments = commentDAO.getAllComments(articleIndex);
        DateStringParser.prettifyDateString(comments);

        return comments;
    }

    @Override
    public Integer getParentArticleIndex(int commentIndex) {
        return commentDAO.getParentArticleIndex(commentIndex);
    }

}