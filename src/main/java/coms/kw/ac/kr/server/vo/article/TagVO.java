package coms.kw.ac.kr.server.vo.article;

import org.apache.ibatis.type.Alias;

@Alias("TagVO")
public class TagVO {
    private Integer tag_idx;
    private Integer article_idx;
    private Integer tag;
    
    private String tag_desc;
    
    public TagVO() {
    }
    
    public Integer getTag_idx() {
        return this.tag_idx;
    }
    
    public void setTag_idx(Integer tag_idx) {
        this.tag_idx = tag_idx;
    }
    
    public Integer getArticle_idx() {
        return this.article_idx;
    }
    
    public void setArticle_idx(Integer article_idx) {
        this.article_idx = article_idx;
    }
    
    public Integer getTag() {
        return this.tag;
    }
    
    public void setTag(Integer tag) {
        this.tag = tag;
    }
    
    public String getTag_Desc() {
        return this.tag_desc;
    }
    
    public void setTag_desc(String tag_desc) {
        this.tag_desc = tag_desc;
    }
}
