package coms.kw.ac.kr.server.vo.user;

import org.apache.ibatis.type.Alias;

@Alias("ExecutiveVO")
public class ExecutiveVO {
    private Integer exec_idx;
    private Integer user_idx;
    private Integer nth;
    private Integer position;
    private Boolean active;

    // 추가 정보
    private String name;
    private Integer term;

    public ExecutiveVO() {
    }

    public ExecutiveVO(Integer user_idx, Integer nth, Integer position, Boolean active) {
        this.user_idx = user_idx;
        this.nth = nth;
        this.position = position;
        this.active = active;
    }

    public Integer getExec_idx() {
        return this.exec_idx;
    }

    public void setExec_idx(int exec_idx) {
        this.exec_idx = exec_idx;
    }

    public Integer getUser_idx() {
        return this.user_idx;
    }

    public void setUser_idx(Integer user_idx) {
        this.user_idx = user_idx;
    }

    public Integer getNth() {
        return this.nth;
    }

    public void setNth(Integer nth) {
        this.nth = nth;
    }

    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTerm() {
        return this.term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

}