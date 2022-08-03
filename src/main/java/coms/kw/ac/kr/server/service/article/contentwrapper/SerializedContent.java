package coms.kw.ac.kr.server.service.article.contentwrapper;

public class SerializedContent {
    private String insert;
    private String attributes;

    public SerializedContent() {
    }

    public SerializedContent(String insert, String attributes) {
        this.insert = insert;
        this.attributes = attributes;
    }

    public String getInsert() {
        return this.insert;
    }

    public void setInsert(String insert) {
        this.insert = insert;
    }

    public String getAttributes() {
        return this.attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

}
