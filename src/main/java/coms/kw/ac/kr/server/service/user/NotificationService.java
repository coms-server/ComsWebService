package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.vo.user.NotificationVO;

import java.util.Collection;
import java.util.List;

// TODO: javadoc
public interface NotificationService {

    public void createNotification(int userIndex, int referFlag, int referIndex, String title, String url);

    public void createNotification(Collection<Integer> userIndices, int referFlag, int referIndex, String title, String url);

    public NotificationVO getNotification(int notiIndex);

    public NotificationVO getNotification(int userIndex, int referFlag, int referIndex);

    public List<NotificationVO> getNotificationList(int userIndex);

    public List<NotificationVO> getNotificationListByRefer(int referFlag, int referIndex);

    public Boolean isNotified(int userIndex, int referFlag, int referIndex);

    public void setAsRead(int notiIndex);

    public void deleteNotification(int notiIndex);

    public void deleteNotification(Collection<NotificationVO> notiIndices);
}