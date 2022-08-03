package coms.kw.ac.kr.server.controller.article;

import coms.kw.ac.kr.server.vo.article.ArticleVO;
import coms.kw.ac.kr.server.vo.article.CommentVO;
import coms.kw.ac.kr.server.vo.statics.Board;
import coms.kw.ac.kr.server.vo.statics.BoardVO;
import coms.kw.ac.kr.server.vo.statics.RootBoardVO;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import org.springframework.ui.Model;

class ArticleTools {
    
    static void addBoardInformation(Board board, Model model) {
        model.addAttribute("context", board.getContext());
        model.addAttribute("board_idx", board.getIndex());
        model.addAttribute("boardName", board.getName());
        model.addAttribute("is_important", board.isImportant());
    }

    static boolean isQnaBoard(Board board) {
        if (board.isRoot())
            return ((RootBoardVO) board).getIs_qna();
        else
            return ((BoardVO) board).getRoot().getIs_qna();
    }

    static boolean isAuthorized(UserAuthenticationVO user, Board board) {
        return user.isAdmin() || !board.isImportant();
    }

    static boolean isAuthorized(UserAuthenticationVO user, ArticleVO article) {
        if(user.isAdmin() || article.getUser_idx().equals(user.getUser_idx())) 
            return true;
        else
            return false;
    }

    static boolean isAuthorized(UserAuthenticationVO user, CommentVO comment) {
        if(user.isAdmin() || comment.getUser_idx().equals(user.getUser_idx()))
            return true;
        else
            return false;
    }

}
