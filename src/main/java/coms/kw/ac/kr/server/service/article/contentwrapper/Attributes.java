package coms.kw.ac.kr.server.service.article.contentwrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Attributes {

    private String background;
    private Boolean bold;
    private String color;
    private String font;
    private String code;
    private Boolean italic;
    private String link;
    private String size;
    private Boolean strike;
    private String script;
    private Boolean underline;
    private String blockquote;
    private Integer header;
    private Integer indent;
    private String list;
    private String align;
    private String direction;
    @JsonProperty("code-block")
    private Boolean code_block;

    // Quill Image Resizer
    private Integer width;

    public Attributes() {
    }

    public Attributes(String data) {
        if (data == null || data.length() < 3)
            return;

        String[] attrs = data.split(";");
        for (String attr : attrs) {
            String code = attr.substring(0, 2);
            String value = attr.substring(2);

            switch (code) {
                case "al":
                    this.align = value;
                    break;
                case "bg":
                    this.background = value;
                    break;
                case "bo":
                    this.bold = value.equals("1");
                    break;
                case "bq":
                    this.blockquote = value;
                    break;
                case "cb":
                    this.code_block = value.equals("1");
                    break;
                case "cd":
                    this.code = value;
                    break;
                case "cl":
                    this.color = value;
                    break;
                case "dr":
                    this.direction = value;
                    break;
                case "ft":
                    this.font = value;
                    break;
                case "hd":
                    this.header = Integer.parseInt(value);
                    break;
                case "id":
                    this.indent = Integer.parseInt(value);
                    break;
                case "it":
                    this.italic = value.equals("1");
                    break;
                case "lk":
                    this.link = value;
                    break;
                case "ls":
                    this.list = value;
                    break;
                case "sc":
                    this.script = value;
                    break;
                case "st":
                    this.strike = value.equals("1");
                    break;
                case "sz":
                    this.size = value;
                    break;
                case "ul":
                    this.underline = value.equals("1");
                    break;
                case "wi":
                    this.width = Integer.parseInt(value);
                default:
                    break;
            }
        }
    }

    public String stringfy() {
        StringBuilder builder = new StringBuilder(64);

        if (background != null)
            builder.append("bg" + background + ";");
        if (bold != null)
            builder.append("bo" + (bold.booleanValue() ? "1" : "0") + ";");
        if (color != null)
            builder.append("cl" + color + ";");
        if (font != null)
            builder.append("ft" + font + ";");
        if (code != null)
            builder.append("cd" + code + ";");
        if (italic != null)
            builder.append("it" + (italic.booleanValue() ? "1" : "0") + ";");
        if (link != null)
            builder.append("lk" + link + ";");
        if (size != null)
            builder.append("sz" + size + ";");
        if (strike != null)
            builder.append("st" + (strike.booleanValue() ? "1" : "0") + ";");
        if (script != null)
            builder.append("sc" + script + ";");
        if (underline != null)
            builder.append("ul" + (underline.booleanValue() ? "1" : "0") + ";");
        if (blockquote != null)
            builder.append("bq" + blockquote + ";");
        if (header != null)
            builder.append("hd" + header.intValue() + ";");
        if (indent != null)
            builder.append("id" + indent.intValue() + ";");
        if (list != null)
            builder.append("ls" + list + ";");
        if (align != null)
            builder.append("al" + align + ";");
        if (direction != null)
            builder.append("dr" + direction + ";");
        if (code_block != null)
            builder.append("cb" + (code_block.booleanValue() ? "1" : "0") + ";");

        if (width != null)
            builder.append("wi" + width.intValue() + ";");

        return builder.toString();
    }

    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Boolean getBold() {
        return this.bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getItalic() {
        return this.italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getStrike() {
        return this.strike;
    }

    public void setStrike(Boolean strike) {
        this.strike = strike;
    }

    public String getScript() {
        return this.script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Boolean getUnderline() {
        return this.underline;
    }

    public void setUnderline(Boolean underline) {
        this.underline = underline;
    }

    public String getBlockquote() {
        return this.blockquote;
    }

    public void setBlockquote(String blockquote) {
        this.blockquote = blockquote;
    }

    public Integer getHeader() {
        return this.header;
    }

    public void setHeader(Integer header) {
        this.header = header;
    }

    public Integer getIndent() {
        return this.indent;
    }

    public void setIndent(Integer indent) {
        this.indent = indent;
    }

    public String getList() {
        return this.list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getAlign() {
        return this.align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Boolean getCode_block() {
        return this.code_block;
    }

    public void setCode_block(Boolean code_block) {
        this.code_block = code_block;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

}