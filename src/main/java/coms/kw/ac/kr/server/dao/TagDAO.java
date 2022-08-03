package coms.kw.ac.kr.server.dao;

import java.util.List;

import coms.kw.ac.kr.server.vo.article.TagVO;

public interface TagDAO {

    // 기본 메서드
    public void insertTag(TagVO tag);

    public void deleteTag(int tag_idx);

    public TagVO getTagList();

    // 게시글 종속 메서드
    public List<TagVO> getTagfromArticle(int article_idx);

    public void deleteTagFromArticle(int article_idx);
}
