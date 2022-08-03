package coms.kw.ac.kr.server.dao;

import coms.kw.ac.kr.server.vo.event.EventVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;

import java.util.List;

public interface UserInformationDAO {

    void insertUserInformation(UserInformationVO userInfo);

    UserInformationVO getUserInformation(int user_idx);

    void updateUserInformation(UserInformationVO userInfo);

    void deleteUserInformation(int user_idx);

    List<EventVO> getUserParticipatedEventList(int user_idx);

    void updateUserIntro(UserInformationVO userInfo);

    List<UserInformationVO> getPendingUserList();

    List<UserInformationVO> getApprovedUserList();
}