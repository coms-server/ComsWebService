package coms.kw.ac.kr.server.dao;

import coms.kw.ac.kr.server.vo.user.NotificationVO;

import java.util.List;

public interface NotificationDAO {

    public void createNotification(NotificationVO notification);

    public NotificationVO getNotification(int noti_idx);

    public NotificationVO getNotificationByRefer(int user_idx, int refer_flag, int refer_idx);

    public List<NotificationVO> getNotificationList(int user_idx);

    public List<NotificationVO> getNotificationListByRefer(int refer_flag, int refer_idx);

    public void setAsRead(int noti_idx);

    public void deleteNotification(int noti_idx);
}