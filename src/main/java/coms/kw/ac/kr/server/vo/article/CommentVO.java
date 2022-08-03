package coms.kw.ac.kr.server.vo.article;

import coms.kw.ac.kr.server.service.tools.DateStringContainer;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/***
 * 'b_comment'테이블의 필드 데이터 클래스.
 */
@Alias("CommentVO")
public class CommentVO implements DateStringContainer, Comparable<CommentVO> {
    // 기본 DB 정보
    private Integer comment_idx;
    private Integer parent_idx;
    private Integer article_idx;
    private String content;
    private Integer user_idx;
    private String name;
    private LocalDateTime write_date;
    private Boolean is_modified;
    private Boolean is_deleted;

    // 추가 정보
    private String write_date_string;

    public Integer getComment_idx() {
        return this.comment_idx;
    }

    public void setComment_idx(Integer comment_idx) {
        this.comment_idx = comment_idx;
    }

    public Integer getParent_idx() {
        return this.parent_idx;
    }

    public void setParent_idx(Integer parent_idx) {
        this.parent_idx = parent_idx;
    }

    public Integer getArticle_idx() {
        return this.article_idx;
    }

    public void setArticle_idx(Integer article_idx) {
        this.article_idx = article_idx;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUser_idx() {
        return this.user_idx;
    }

    public void setUser_idx(Integer user_idx) {
        this.user_idx = user_idx;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getWrite_date() {
        return this.write_date;
    }

    public void setWrite_date(LocalDateTime write_date) {
        this.write_date = write_date;
    }

    public Boolean getIs_modified() {
        return this.is_modified;
    }

    public void setIs_modified(Boolean is_modified) {
        this.is_modified = is_modified;
    }

    public Boolean getIs_deleted() {
        return this.is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getWrite_date_string() {
        return this.write_date_string;
    }

    @Override
    public int compareTo(CommentVO cmt) {
        int parentIndex1 = this.parent_idx == null ? 0 : this.parent_idx;
        int parentIndex2 = cmt.parent_idx == null ? 0 : cmt.parent_idx;
        if (parentIndex1 == parentIndex2) {
            return this.comment_idx.intValue() > cmt.comment_idx.intValue() ? 1 : -1;
        } else {
            if (parentIndex1 == 0)
                return this.comment_idx.intValue() > parentIndex2 ? 1 : -1;
            else if (parentIndex2 == 0)
                return parentIndex1 >= cmt.comment_idx.intValue() ? 1 : -1;
            else
                return parentIndex1 > parentIndex2 ? 1 : -1;
        }
    }

    @Override
    public List<LocalDateTime> getDates() {
        return Arrays.asList(this.write_date);
    }

    @Override
    public void setDateStrings(List<String> strings) {
        this.write_date_string = strings.get(0);
    }

}