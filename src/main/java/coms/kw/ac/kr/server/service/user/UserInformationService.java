package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.vo.event.EventVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

// TODO: javadoc
public interface UserInformationService {

    void insertUserInformation(UserInformationVO userInfo) throws IOException;

    UserInformationVO getUserInformation(int userIndex);

    void updateUserInformation(UserInformationVO userInfo);

    void deleteUserInformation(int userIndex);

    List<EventVO> getUserParticipatedEventList(int userIndex);

    void setDefaultProfileImage(int userIndex) throws IOException;

    void updateProfileImage(int userIndex, InputStream image) throws IOException;

    void updateUserIntro(UserInformationVO userInfo);

    List<UserInformationVO> getPendingUserList();

    List<UserInformationVO> getApprovedUserList();

}