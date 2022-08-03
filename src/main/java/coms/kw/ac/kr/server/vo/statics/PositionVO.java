package coms.kw.ac.kr.server.vo.statics;

import org.apache.ibatis.type.Alias;

@Alias("PositionVO")
public class PositionVO {
    Integer position_idx;
    String position;

    public Integer getPosition_idx() {
        return position_idx;
    }

    public void setPosition_idx(Integer position_idx) {
        this.position_idx = position_idx;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}