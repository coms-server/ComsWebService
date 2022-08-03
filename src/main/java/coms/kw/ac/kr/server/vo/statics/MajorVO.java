package coms.kw.ac.kr.server.vo.statics;

import org.apache.ibatis.type.Alias;

@Alias("MajorVO")
public class MajorVO {
    
    private Integer major_idx;
    private String major;
    private String college;

    public Integer getMajor_idx() {
        return major_idx;
    }

    public void setMajor_idx(Integer major_idx) {
        this.major_idx = major_idx;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}