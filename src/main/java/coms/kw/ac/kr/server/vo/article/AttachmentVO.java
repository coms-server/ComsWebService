package coms.kw.ac.kr.server.vo.article;

import org.apache.ibatis.type.Alias;

@Alias("AttachmentVO")
public class AttachmentVO {
    private Integer attach_idx;
    private Integer article_idx;
    private String filename;
    private Integer filesize;

    // 추가 정보
    private String unit;
    private String directory;

    public AttachmentVO() {
    }

    public AttachmentVO(int article_idx, String filename, int filesize) {
        this.article_idx = article_idx;
        this.filename = filename;
        this.filesize = filesize;
    }

    public Integer getAttach_idx() {
        return this.attach_idx;
    }

    public void setAttach_idx(Integer attach_idx) {
        this.attach_idx = attach_idx;
    }

    public Integer getArticle_idx() {
        return this.article_idx;
    }

    public void setArticle_idx(Integer article_idx) {
        this.article_idx = article_idx;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getFilesize() {
        return this.filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}