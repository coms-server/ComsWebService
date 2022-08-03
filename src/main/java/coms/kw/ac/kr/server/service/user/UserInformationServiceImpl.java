package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.dao.UserInformationDAO;
import coms.kw.ac.kr.server.service.statics.StaticInformationService;
import coms.kw.ac.kr.server.vo.event.EventVO;
import coms.kw.ac.kr.server.vo.statics.MajorVO;
import coms.kw.ac.kr.server.vo.statics.StatusVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@Service
public class UserInformationServiceImpl implements UserInformationService {

    private static final Logger logger = LoggerFactory.getLogger(UserInformationServiceImpl.class);

    private final UserInformationDAO userInfoDAO;
    private final StaticInformationService staticInformation;
    private final ProfileImageHandler profileImage;

    @Autowired
    public UserInformationServiceImpl(UserInformationDAO userInfoDAO, StaticInformationService staticInformation,
            @Value("${local-storage.root}") String LOCAL_STORAGE_ROOT) {
        this.userInfoDAO = userInfoDAO;
        this.staticInformation = staticInformation;
        this.profileImage = new ProfileImageHandler(LOCAL_STORAGE_ROOT);
    }

    @Override
    public void insertUserInformation(UserInformationVO userInfo) throws IOException {
        userInfoDAO.insertUserInformation(userInfo);
        profileImage.createDefault(userInfo.getUser_idx());

        logger.info("New user information inserted; user_idx={}, name={}", userInfo.getUser_idx(), userInfo.getName());
    }

    @Override
    public UserInformationVO getUserInformation(int userIndex) {
        UserInformationVO user = userInfoDAO.getUserInformation(userIndex);
        patchInformation(user);
        return user;
    }

    @Override
    public void updateUserInformation(UserInformationVO userInfo) {
        userInfoDAO.updateUserInformation(userInfo);
        UserInformationVO user = userInfoDAO.getUserInformation(userInfo.getUser_idx());
        logger.info("User information updated; user_idx={}, name={}", user.getUser_idx(), user.getName());
    }

    @Override
    public void deleteUserInformation(int userIndex) {
        UserInformationVO user = userInfoDAO.getUserInformation(userIndex);
        userInfoDAO.deleteUserInformation(userIndex);
        logger.info("User information deleted; user_idx={}, name={}", user.getUser_idx(), user.getName());
    }

    @Override
    public List<EventVO> getUserParticipatedEventList(int userIndex) {
        return userInfoDAO.getUserParticipatedEventList(userIndex);
    }

    @Override
    public void setDefaultProfileImage(int userIndex) throws IOException {
        profileImage.createDefault(userIndex);
    }

    @Override
    public void updateProfileImage(int userIndex, InputStream image) throws IOException {
        profileImage.updateImage(userIndex, image);
    }
    
    @Override
    public void updateUserIntro(UserInformationVO userInfo) {
        userInfoDAO.updateUserIntro(userInfo);
    }

    @Override
    public List<UserInformationVO> getPendingUserList() {
        List<UserInformationVO> list = userInfoDAO.getPendingUserList();
        patchInformation(list);
        return list;
    }

    @Override
    public List<UserInformationVO> getApprovedUserList() {
        List<UserInformationVO> list = userInfoDAO.getApprovedUserList();
        patchInformation(list);
        return list;
    }

    private void patchInformation(Collection<UserInformationVO> users) {
        if (users == null)
            return;
        for (UserInformationVO u : users)
            patchInformation(u);
    }

    private void patchInformation(UserInformationVO user) {
        if (user == null)
            return;
        MajorVO major = staticInformation.findMajorByIndex(user.getMajor());
        StatusVO status = staticInformation.findStatusByIndex(user.getStatus());
        user.setMajor_string(major.getMajor());
        user.setStatus_string(status.getStatus());

        String homeAddr = user.getHome_addr().replace("$", " ");
        user.setHome_addr(homeAddr);
    }
}