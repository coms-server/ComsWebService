package coms.kw.ac.kr.server.vo.statics;

import org.apache.ibatis.type.Alias;

@Alias("StatusVO")
public class StatusVO {
    Integer status_idx;
    String status;

    public Integer getStatus_idx() {
        return status_idx;
    }

    public void setStatus_idx(Integer status_idx) {
        this.status_idx = status_idx;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}   