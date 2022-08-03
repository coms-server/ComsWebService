package coms.kw.ac.kr.server.service.user;

import coms.kw.ac.kr.server.dao.UserHibernationDAO;
import coms.kw.ac.kr.server.dao.UserInformationDAO;
import coms.kw.ac.kr.server.service.tools.ClassPathResourceLoader;
import coms.kw.ac.kr.server.service.tools.DateStringParser;
import coms.kw.ac.kr.server.service.tools.MailSenderProvider.MailSender;
import coms.kw.ac.kr.server.service.tools.ScheduledTask;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserHibernationServiceImpl implements UserHibernationService, ScheduledTask {
    private final UserInformationDAO informationDAO;
    private final UserHibernationDAO hibernationDAO;
    private final MailSender mailSender;

    private static final String SUBJECT = "[광운대학교 COM'S] 휴면계정 개인정보 분리보관 안내";
    private final String HIBERNATING_ALERT;
    private final String HIBERNATED_ALERT;

    private static final Logger logger = LoggerFactory.getLogger(UserHibernationService.class);

    @Autowired
    public UserHibernationServiceImpl(UserInformationDAO informationDAO, UserHibernationDAO hibernationDAO,
            MailSender mailSender) {
        this.informationDAO = informationDAO;
        this.hibernationDAO = hibernationDAO;
        this.mailSender = mailSender;

        this.HIBERNATING_ALERT = ClassPathResourceLoader.getAsString("static/string/hibernating_alert.txt");
        this.HIBERNATED_ALERT = ClassPathResourceLoader.getAsString("static/string/hibernated_alert.txt");
    }

    @Override
    public UpdateRate getUpdateRate() {
        return UpdateRate.ONCE_A_DAY;
    }

    @Override
    public int getUpdatePriority() {
        return 0;
    }

    @Override
    public void scheduledUpdate() {
        notifyHibernation();
        handleHibernation();
    }

    @Override
    public void restoreHibernationState(UserAuthenticationVO user) {
        if (user.getIs_hibernating())
            restoreFromHibernate(user);
    }

    private void moveToHibernate(UserAuthenticationVO user) {
        int userIndex = user.getUser_idx();
        hibernationDAO.updateHibernatingFlag(userIndex, true);
        hibernationDAO.moveToHibernate(userIndex);
        informationDAO.deleteUserInformation(userIndex);
        logger.info("User information moved to hibernate db; user_idx={}, name={}", userIndex, user.getName());
    }

    private void restoreFromHibernate(UserAuthenticationVO user) {
        int userIndex = user.getUser_idx();
        hibernationDAO.updateHibernatingFlag(userIndex, false);
        hibernationDAO.restoreFromHibernate(userIndex);
        hibernationDAO.deleteUserHibernate(userIndex);
        logger.info("User information restored from hibernate db; user_idx={}, name={}", userIndex, user.getName());
    }

    private void notifyHibernation() {
        List<UserAuthenticationVO> users = hibernationDAO.getAllUsersLastLogin();

        LocalDate today = LocalDate.now();
        for (UserAuthenticationVO u : users) {
            if (u.getIs_hibernating())
                continue;

            LocalDate lastLogin;
            if(u.getLast_login() != null) {
                lastLogin= DateStringParser.parse(u.getLast_login()).toLocalDate();
            } else {
                UserInformationVO uInfo = informationDAO.getUserInformation(u.getUser_idx());
                lastLogin = DateStringParser.parse(uInfo.getJoin_date()).toLocalDate();
            }

            if (lastLogin.plusMonths(11).isEqual(today)) {
                // Notify user
                UserInformationVO info = informationDAO.getUserInformation(u.getUser_idx());
                mailSender.sendMail(info.getEmail_addr(), SUBJECT, HIBERNATING_ALERT);
            }
        }
    }

    private void handleHibernation() {
        List<UserAuthenticationVO> users = hibernationDAO.getAllUsersLastLogin();

        LocalDate today = LocalDate.now();
        for (UserAuthenticationVO u : users) {
            if (u.getIs_hibernating())
                continue;

            LocalDate lastLogin = DateStringParser.parse(u.getLast_login()).toLocalDate();
            if (lastLogin.plusYears(1).isBefore(today)) {
                moveToHibernate(u);

                // Notify user
                UserInformationVO info = informationDAO.getUserInformation(u.getUser_idx());
                mailSender.sendMail(info.getEmail_addr(), SUBJECT, HIBERNATED_ALERT);
            }
        }
    }

}