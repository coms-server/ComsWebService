package coms.kw.ac.kr.server.vo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

@Alias("UserInfoVO")
@JsonInclude(Include.NON_NULL)
public class UserInformationVO {
    private Integer user_idx;
    private String name;
    private LocalDate birth;
    private Integer term;
    private Integer major;
    private Integer status;
    private String email_addr;
    private Boolean email_send;
    private String phone_num;
    private Boolean sms_send;
    private String home_addr;
    private LocalDate join_date;
    private String company;
    private String website;
    private String intro;

    // NOTE: 추가정보 필드
    private String major_string;
    private String status_string;

    public Integer getUser_idx() {
        return this.user_idx;
    }

    public void setUser_idx(Integer user_idx) {
        this.user_idx = user_idx;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return this.birth.toString();
    }

    public void setBirth(String birth) {
        this.birth = LocalDate.parse(birth);
    }

    public Integer getTerm() {
        return this.term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getMajor() {
        return this.major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail_addr() {
        return this.email_addr;
    }

    public void setEmail_addr(String email_addr) {
        this.email_addr = email_addr;
    }

    public Boolean getEmail_send() {
        return this.email_send;
    }

    public void setEmail_send(Boolean email_send) {
        this.email_send = email_send;
    }

    public String getPhone_num() {
        return this.phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public Boolean getSms_send() {
        return this.sms_send;
    }

    public void setSms_send(Boolean sms_send) {
        this.sms_send = sms_send;
    }

    public String getHome_addr() {
        return this.home_addr;
    }

    public void setHome_addr(String home_addr) {
        this.home_addr = home_addr;
    }

    public String getJoin_date() {
        return this.join_date.toString();
    }

    public void setJoin_date(LocalDate join_date) {
        this.join_date = join_date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMajor_string() {
        return major_string;
    }

    public void setMajor_string(String major_string) {
        this.major_string = major_string;
    }

    public String getStatus_string() {
        return status_string;
    }

    public void setStatus_string(String status_string) {
        this.status_string = status_string;
    }

}