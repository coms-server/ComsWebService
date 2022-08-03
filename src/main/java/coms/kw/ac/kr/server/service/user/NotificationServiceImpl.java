package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.dao.NotificationDAO;
import coms.kw.ac.kr.server.dao.UserInformationDAO;
import coms.kw.ac.kr.server.service.tools.DateStringParser;
import coms.kw.ac.kr.server.vo.user.NotificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    public static final Integer NORMAL_NOTI = 0;
    public static final Integer ARTICLE_NOTI = 1;
    public static final Integer COMMENT_NOTI = 2;
    public static final Integer REPLY_NOTI = 3;
    public static final Integer SIGNUP_SUBMIT_NOTI = 4;

    private final UserInformationDAO userInformationDAO;
    private final NotificationDAO notificationDAO;

    @Autowired
    public NotificationServiceImpl(UserInformationDAO userInformationDAO, NotificationDAO notificationDAO) {
        this.userInformationDAO = userInformationDAO;
        this.notificationDAO = notificationDAO;
    }

    @Override
    public void createNotification(int userIndex, int referFlag, int referIndex, String title, String url) {
        if(userInformationDAO.getUserInformation(userIndex) == null)
            return;
        
        if(isNotified(userIndex, referFlag, referIndex)) {
            deleteNotification(getNotification(userIndex, referFlag, referIndex).getNoti_idx());
        }

        NotificationVO notification = new NotificationVO();
        notification.setUser_idx(userIndex);
        notification.setRefer_flag(referFlag);
        notification.setRefer_idx(referIndex);
        notification.setTitle(title);
        notification.setUrl(url);
        notification.setDate(LocalDateTime.now());
        
        notificationDAO.createNotification(notification);
    }

    @Override
    public void createNotification(Collection<Integer> userIndices, int referFlag, int referIndex, String title, String url) {
        NotificationVO notification = new NotificationVO();
        notification.setRefer_flag(referFlag);
        notification.setRefer_idx(referIndex);
        notification.setTitle(title);
        notification.setUrl(url);
        notification.setDate(LocalDateTime.now());

        for (int i : userIndices) {
            notification.setUser_idx(i);
            notificationDAO.createNotification(notification);
        }
    }
    
    @Override
    public NotificationVO getNotification(int notiIndex) {
        NotificationVO noti = notificationDAO.getNotification(notiIndex);
        DateStringParser.prettifyDateString(noti);

        return noti;
    }

    @Override
    public NotificationVO getNotification(int userIndex, int referFlag, int referIndex) {
        return notificationDAO.getNotificationByRefer(userIndex, referFlag, referIndex);
    }

    @Override
    public List<NotificationVO> getNotificationList(int userIndex) {
        List<NotificationVO> list = notificationDAO.getNotificationList(userIndex);
        DateStringParser.prettifyDateString(list);

        return list;
    }

    @Override
    public List<NotificationVO> getNotificationListByRefer(int referFlag, int referIndex) {
        List<NotificationVO> list = notificationDAO.getNotificationListByRefer(referFlag, referIndex);
        DateStringParser.prettifyDateString(list);

        return list;
    }

    @Override
    public Boolean isNotified(int userIndex, int referFlag, int referIndex) {
        if(notificationDAO.getNotificationByRefer(userIndex, referFlag, referIndex) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setAsRead(int notiIndex) {
        notificationDAO.setAsRead(notiIndex);
    }

    @Override
    public void deleteNotification(int notiIndex) {
        notificationDAO.deleteNotification(notiIndex);
    }

    @Override
    public void deleteNotification(Collection<NotificationVO> notiIndices) {
        for(int i : notiIndices.stream().map(NotificationVO::getNoti_idx).collect(Collectors.toList())) {
            notificationDAO.deleteNotification(i);
        }
    }
}