package coms.kw.ac.kr.server.vo.article;

import coms.kw.ac.kr.server.service.article.contentwrapper.SerializedContent;
import coms.kw.ac.kr.server.service.tools.DateStringContainer;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 'b_articles'테이블의 필드 데이터 클래스.
 */
@Alias("ArticleVO")
public class ArticleVO implements DateStringContainer {
    // b_article
    private Integer article_idx;
    private Integer parent_idx;
    private Integer board_idx;
    private String title;
    private String content;
    private String attributes;
    private Integer user_idx;
    private String name;
    private LocalDateTime write_date;
    private Boolean is_subarticle;
    private Boolean is_notice;
    private Boolean is_deleted;

    // 추가 정보
    private Integer row_number;
    private String write_date_string;

    public ArticleVO() {
    }

    public Integer getArticle_idx() {
        return this.article_idx;
    }

    public void setArticle_idx(Integer article_idx) {
        this.article_idx = article_idx;
    }

    public Integer getParent_idx() {
        return this.parent_idx;
    }

    public void setParent_idx(Integer parent_idx) {
        this.parent_idx = parent_idx;
    }

    public Integer getBoard_idx() {
        return this.board_idx;
    }

    public void setBoard_idx(Integer board_idx) {
        this.board_idx = board_idx;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttributes() {
        return this.attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
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

    public Boolean getIs_subarticle() {
        if (is_subarticle != null)
            return this.is_subarticle;
        else
            return false;
    }

    public void setIs_subarticle(Boolean is_subarticle) {
        this.is_subarticle = is_subarticle;
    }

    public Boolean getIs_notice() {
        return this.is_notice;
    }

    public void setIs_notice(Boolean is_notice) {
        this.is_notice = is_notice;
    }

    public Boolean getIs_deleted() {
        return this.is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Integer getRow_number() {
        return this.row_number;
    }

    public void setRow_number(Integer row_number) {
        this.row_number = row_number;
    }

    public String getWrite_date_string() {
        return this.write_date_string;
    }

    @Override
    public List<LocalDateTime> getDates() {
        return Arrays.asList(write_date);
    }

    @Override
    public void setDateStrings(List<String> strings) {
        this.write_date_string = strings.get(0);
    }

    public void fromSerializedContent(SerializedContent serializedContent) {
        this.content = serializedContent.getInsert();
        this.attributes = serializedContent.getAttributes();
    }

    public SerializedContent toSerializedContent() {
        return new SerializedContent(this.content, this.attributes);
    }

}