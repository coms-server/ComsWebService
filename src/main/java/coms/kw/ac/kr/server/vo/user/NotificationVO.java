package coms.kw.ac.kr.server.vo.user;

import coms.kw.ac.kr.server.service.tools.DateStringContainer;
import coms.kw.ac.kr.server.service.tools.DateStringParser;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Alias("NotificationVO")
public class NotificationVO implements DateStringContainer {
    private Integer noti_idx;
    private Integer user_idx;
    private Integer refer_flag;
    private Integer refer_idx;
    private String title;
    private String url;
    private LocalDateTime date;
    private Boolean is_read;

    // 추가정보
    private String date_string;

    public Integer getNoti_idx() {
        return this.noti_idx;
    }

    public void setNoti_idx(Integer noti_idx) {
        this.noti_idx = noti_idx;
    }

    public Integer getUser_idx() {
        return this.user_idx;
    }

    public void setUser_idx(Integer user_idx) {
        this.user_idx = user_idx;
    }

    public Integer getRefer_flag() {
        return this.refer_flag;
    }

    public void setRefer_flag(Integer refer_flag) {
        this.refer_flag = refer_flag;
    }

    public Integer getRefer_idx() {
        return this.refer_idx;
    }

    public void setRefer_idx(Integer refer_idx) {
        this.refer_idx = refer_idx;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return this.date.format(DateStringParser.FORMATTER_TYPE_DB);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDate(String date) {
        this.date = LocalDateTime.parse(date, DateStringParser.FORMATTER_TYPE_DB);
    }

    public Boolean getIs_read() {
        return this.is_read;
    }

    public void setIs_read(Boolean is_read) {
        this.is_read = is_read;
    }

    @Override
    public List<LocalDateTime> getDates() {
        return Arrays.asList(this.date);
    }

    @Override
    public void setDateStrings(List<String> strings) {
        this.date_string = strings.get(0);
    }

    public String getDate_string() {
        return this.date_string;
    }
}