package coms.kw.ac.kr.server.controller.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import coms.kw.ac.kr.server.controller.CommonHttpResponse;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.user.NotificationService;
import coms.kw.ac.kr.server.service.user.UserInformationService;
import coms.kw.ac.kr.server.vo.user.NotificationVO;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(NotificationAPI.API_CONTEXT)
public class NotificationAPI {
    public static final String API_CONTEXT = "/notification";

    private static final ObjectMapper mapper = new ObjectMapper();

    private final NotificationService notificationService;
    private final UserInformationService userInformationService;
    private final AuthenticationFacade authFacade;

    @Autowired
    public NotificationAPI(NotificationService notificationService, UserInformationService userInfoService,
            AuthenticationFacade authFacade) {
        this.notificationService = notificationService;
        this.userInformationService = userInfoService;
        this.authFacade = authFacade;
    }

    // NOTE: 알림 생성
    @RequestMapping(value = "", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> pushNotification(@RequestBody HashMap<String, Object> params) {
        // Authorization check
        if (!authFacade.getPrincipal().isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Parse parameter
        Object target = params.get("user_idx");
        if (target == null)
            return CommonHttpResponse.BAD_REQUEST;

        List<Integer> userIndex = new ArrayList<>();
        try {
            ((List<String>) target).stream().forEach(i -> userIndex.add(Integer.parseInt(i)));
        } catch (Exception exception) {
            return CommonHttpResponse.BAD_REQUEST;
        }
        String title = params.get("title").toString();
        String url = params.get("url").toString();

        // Check value
        int size = userIndex.size();
        if (size == 0 || title == null || title.isEmpty())
            return CommonHttpResponse.BAD_REQUEST;

        if (size == 1 && userIndex.get(0) == -1) { // 전체 발송
            List<Integer> userList = userInformationService.getApprovedUserList().stream()
                    .map(UserInformationVO::getUser_idx).collect(Collectors.toList());
            notificationService.createNotification(userList, 0, 0, title, url);
        } else {
            notificationService.createNotification(userIndex, 0, 0, title, url);
        }

        return CommonHttpResponse.OK;
    }

    // NOTE: 알림 목록 가져오기
    @RequestMapping(value = "/{userIndex}", method = RequestMethod.GET)
    public ResponseEntity<String> getNotificationList(@PathVariable int userIndex) {
        // Authorization check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (user.getUser_idx() != userIndex)
            return CommonHttpResponse.UNAUTHORIZED(null);

        // Get notification
        List<NotificationVO> notifications = notificationService.getNotificationList(userIndex);
        if (notifications == null)
            return CommonHttpResponse.OK(null);

        JsonNode node = mapper.valueToTree(notifications);

        return CommonHttpResponse.OK(node.toPrettyString());
    }

    @RequestMapping(value = "/{notiIndex}", method = RequestMethod.PUT)
    public ResponseEntity<Object> setNotificationAsRead(@PathVariable int notiIndex) {
        // Authorization check
        UserAuthenticationVO user = authFacade.getPrincipal();
        NotificationVO noti = notificationService.getNotification(notiIndex);
        if (user.getUser_idx().intValue() != noti.getUser_idx())
            return CommonHttpResponse.UNAUTHORIZED;

        // Set as read
        if (!noti.getIs_read())
            notificationService.setAsRead(notiIndex);
        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = "/{notiIndex}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteNotification(@PathVariable int notiIndex) {
        // Authorization check
        UserAuthenticationVO user = authFacade.getPrincipal();
        NotificationVO noti = notificationService.getNotification(notiIndex);
        if (user.getUser_idx().intValue() != noti.getUser_idx())
            return CommonHttpResponse.UNAUTHORIZED;

        // Delete notification
        notificationService.deleteNotification(notiIndex);
        return CommonHttpResponse.OK;
    }
}