package coms.kw.ac.kr.server.dao;

import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;

import java.util.List;

public interface UserHibernationDAO {

    public List<UserAuthenticationVO> getAllUsersLastLogin();

    public void updateHibernatingFlag(int user_idx, boolean is_hibernating);

    public void moveToHibernate(int user_idx);

    public void restoreFromHibernate(int user_idx);

    public void deleteUserHibernate(int user_idx);
}
