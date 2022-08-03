package coms.kw.ac.kr.server.dao;

import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;

public interface UserAuthenticationDAO {

    public void insertUserAuthentication(UserAuthenticationVO userAuth);

    public UserAuthenticationVO getUserAuthentication(int user_idx);

    public UserAuthenticationVO selectUserByAlias(String alias);

    public void updateUserPassword(int user_idx, String password);

    public void deleteUserAuthentication(int user_idx, String id);

    public String isAliasExists(String alias);

    public void updateUserLastLogin(int user_idx, String last_login);

    public void grantPermission(int user_idx, String default_auth);

    public void denyPermission(int user_idx);
}