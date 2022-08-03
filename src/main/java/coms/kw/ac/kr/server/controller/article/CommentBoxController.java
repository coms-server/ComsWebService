package coms.kw.ac.kr.server.controller.article;

import coms.kw.ac.kr.server.controller.LayoutType;
import coms.kw.ac.kr.server.controller.ViewSelector;
import coms.kw.ac.kr.server.service.article.CommentService;
import coms.kw.ac.kr.server.vo.article.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(CommentBoxController.BASE_CONTEXT)
public class CommentBoxController {

    public static final String BASE_CONTEXT = "/comment";

    private final CommentService commentService;

    @Autowired
    public CommentBoxController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Comment Box Frag
    @RequestMapping("/box/{articleIndex}")
    public String commentBox(@PathVariable int articleIndex, Model model) {
        List<CommentVO> comments = commentService.getAllComments(articleIndex);
        comments.sort(Comparator.naturalOrder());
        model.addAttribute("comments", comments);
        model.addAttribute("article_idx", articleIndex);

        return ViewSelector.layoutOnly(LayoutType.COMMENT);
    }
}
