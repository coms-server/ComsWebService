package coms.kw.ac.kr.server.vo.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import coms.kw.ac.kr.server.service.tools.DateStringParser;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("EventVO")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EventVO {

    //이벤트 정보
    private Integer event_idx;
    private String title;
    private String event_desc;
    private LocalDateTime begin_date;
    private LocalDateTime end_date;
    private Boolean is_important;
    private String url;

    //참가자 정보
    private Integer user_idx;
    private Boolean is_participated;
    private String note;

    public Integer getEvent_idx() {
        return this.event_idx;
    }

    public void setEvent_idx(Integer event_idx) {
        this.event_idx = event_idx;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvent_desc() {
        return this.event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getBegin_date() {
        return this.begin_date.format(DateStringParser.FORMATTER_TYPE_DB);
    }

    public void setBegin_date(LocalDateTime begin_date) {
        this.begin_date = begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = LocalDateTime.parse(begin_date, DateStringParser.FORMATTER_TYPE_DB);
    }

    public String getEnd_date() {
        return this.end_date.format(DateStringParser.FORMATTER_TYPE_DB);
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = LocalDateTime.parse(end_date, DateStringParser.FORMATTER_TYPE_DB);
    }

    public Boolean getIs_important() {
        return this.is_important;
    }

    public void setIs_important(Boolean is_important) {
        this.is_important = is_important;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUser_idx() {
        return this.user_idx;
    }

    public void setUser_idx(Integer user_idx) {
        this.user_idx = user_idx;
    }

    public Boolean getIs_participated() {
        return this.is_participated;
    }

    public void setIs_participated(Boolean is_participated) {
        this.is_participated = is_participated;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}