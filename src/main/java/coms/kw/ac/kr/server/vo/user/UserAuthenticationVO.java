package coms.kw.ac.kr.server.vo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import coms.kw.ac.kr.server.config.WebSecurityConfig.Authority;
import coms.kw.ac.kr.server.service.tools.DateStringParser;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Alias("UserAuthVO")
@JsonInclude(Include.NON_NULL)
public class UserAuthenticationVO implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = 1L;

    private Integer user_idx;
    private String alias;
    private String password;
    private String name;
    private String authority;
    private Boolean permission;
    private Boolean is_hibernating;
    private LocalDateTime last_login;

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    public boolean isAdmin() {
        return getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Authority.ADMIN.getValue()));
    }

    @Override
    public boolean equals(Object target) {
        if (target instanceof UserAuthenticationVO) {
            return user_idx.equals(((UserAuthenticationVO) target).user_idx);
        }
        return false;
    }

    public Integer getUser_idx() {
        return this.user_idx;
    }

    public void setUser_idx(Integer user_idx) {
        this.user_idx = user_idx;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getUsername() {
        return this.alias;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPermission() {
        return this.permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    public Boolean getIs_hibernating(){
        return this.is_hibernating;
    }

    public void setIs_hibernating(Boolean is_hibernating){
        this.is_hibernating = is_hibernating;
    }

    public String getLast_login() {
        if(this.last_login == null) {
            return "-";
        }
        return this.last_login.format(DateStringParser.FORMATTER_TYPE_DB);
    }

    public void setLast_login(String last_login) {
        this.last_login = LocalDateTime.parse(last_login, DateStringParser.FORMATTER_TYPE_DB);
    }

    public void setLast_login(LocalDateTime last_login) {
        this.last_login = last_login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        if (authority != null && !authority.isEmpty())
            for (String s : authority.split("$"))
                auth.add(new SimpleGrantedAuthority(s));

        return sortAuthorities(auth);
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return permission;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
        for (GrantedAuthority grantedAuthority : authorities) {
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = 1L;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

}