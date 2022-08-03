package coms.kw.ac.kr.server.service.article.contentwrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Delta {

    private Attributes attributes;
    private Object insert;
    private boolean isEmbed;

    public Delta(Attributes attributes, JsonNode insert) {
        this.attributes = attributes;
        this.insert = insert;
        this.isEmbed = true;
    }

    public Delta(Attributes attributes, String insert) {
        this.attributes = attributes;
        this.insert = insert;
        this.isEmbed = false;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    public Object getInsert() {
        return this.insert;
    }

    public boolean isEmbed() {
        return this.isEmbed;
    }

    public boolean hasImage() {
        return this.isEmbed && ((JsonNode) this.insert).has("image");
    }

    public String getImageData() {
        if (!hasImage())
            return null;

        return ((JsonNode) this.insert).get("image").asText();
    }

    public void replaceImageData(String embedURL) {
        if (!hasImage())
            return;

        ((ObjectNode) this.insert).put("image", embedURL);
    }

    public boolean hasVideo() {
        return this.isEmbed && ((JsonNode) this.insert).has("video");
    }

    public boolean isVideoURLValid() {
        if (this.isEmbed && ((JsonNode) this.insert).has("video")) {
            JsonNode node = ((JsonNode) this.insert);
            String url = node.get("video").asText();

            return url.matches("(https://www.youtube.com/embed/)([a-zA-Z0-9_-]+)(.+)");
        }

        return true;
    }

}