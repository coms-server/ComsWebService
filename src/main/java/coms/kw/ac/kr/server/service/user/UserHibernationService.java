package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;

// TODO: javadoc
public interface UserHibernationService {
    public void restoreHibernationState(UserAuthenticationVO user);
}