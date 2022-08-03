package coms.kw.ac.kr.server.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import coms.kw.ac.kr.server.controller.CommonHttpResponse;
import coms.kw.ac.kr.server.exception.implement.UnauthorizedException;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.user.ExecutiveManageService;
import coms.kw.ac.kr.server.service.user.NotificationService;
import coms.kw.ac.kr.server.service.user.UserAuthenticationService;
import coms.kw.ac.kr.server.service.user.UserInformationService;
import coms.kw.ac.kr.server.vo.user.ExecutiveVO;
import coms.kw.ac.kr.server.vo.user.NotificationVO;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(AccountAPI.BASE_CONTEXT)
public class AccountAPI {
    public static final String BASE_CONTEXT = "/account";

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(AccountAPI.class);

    private final UserInformationService userInformationService;
    private final UserAuthenticationService userAuthenticationService;
    private final NotificationService notificationService;
    private final ExecutiveManageService executiveManageService;
    private final AuthenticationFacade authFacade;

    @Autowired
    public AccountAPI(UserInformationService userInformationService,
            UserAuthenticationService userAuthenticationService, ExecutiveManageService executiveManageService,
            NotificationService notificationService, AuthenticationFacade authFacade) {
        this.userInformationService = userInformationService;
        this.userAuthenticationService = userAuthenticationService;
        this.notificationService = notificationService;
        this.executiveManageService = executiveManageService;
        this.authFacade = authFacade;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> registerNewAccount(@RequestBody HashMap<String, Object> params) {
        // Parse VO
        UserAuthenticationVO userAuth;
        UserInformationVO userInfo;
        try {
            String authJson = mapper.writeValueAsString(params.get("userAuth"));
            String infoJson = mapper.writeValueAsString(params.get("userInfo"));
            userAuth = mapper.readValue(authJson, UserAuthenticationVO.class);
            userInfo = mapper.readValue(infoJson, UserInformationVO.class);
        } catch (IOException exception) {
            return CommonHttpResponse.BAD_REQUEST;
        }

        // Value check
        boolean invalid = false;
        String alias = userAuth.getAlias();
        String password = userAuth.getPassword();
        String name = userAuth.getName();
        String email_addr = userInfo.getEmail_addr();
        String phone_num = userInfo.getPhone_num();
        String home_addr = userInfo.getHome_addr();
        if (alias == null || alias.isEmpty() || alias.length() < 4)
            invalid = true;
        else if (password == null || password.isEmpty() || password.length() < 8)
            invalid = true;
        else if (name == null || name.isEmpty())
            invalid = true;
        else if (userInfo.getBirth() == null)
            invalid = true;
        else if (userInfo.getTerm() == null)
            invalid = true;
        else if (userInfo.getMajor() == null)
            invalid = true;
        else if (userInfo.getStatus() == null)
            invalid = true;
        else if (email_addr == null || email_addr.isEmpty())
            invalid = true;
        else if (userInfo.getEmail_send() == null)
            invalid = true;
        else if (phone_num == null || phone_num.isEmpty())
            invalid = true;
        else if (userInfo.getSms_send() == null)
            invalid = true;
        else if (home_addr == null || home_addr.isEmpty())
            invalid = true;

        if (invalid)
            return CommonHttpResponse.BAD_REQUEST;

        // Insert
        boolean authInsertionSuccess = userAuthenticationService.insertUserAuthentication(userAuth);
        if (!authInsertionSuccess)
            return CommonHttpResponse.BAD_REQUEST;

        userInfo.setUser_idx(userAuth.getUser_idx());
        userInfo.setName(userAuth.getName());
        userInfo.setJoin_date(LocalDate.now());
        try {
            userInformationService.insertUserInformation(userInfo);
        } catch (IOException exception) {
            // Should not happen
            return CommonHttpResponse.INTERNAL_SERVER_ERROR;
        }

        // Send notification
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("의 가입신청을 확인 해 주세요");
        List<Integer> adminList = executiveManageService.getActiveExecutiveList().stream().map(ExecutiveVO::getUser_idx)
                .collect(Collectors.toList());

        notificationService.createNotification(adminList, 4, userAuth.getUser_idx(), builder.toString(), "/admin/index");

        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = "/is-alias-exist/{alias}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> isAliasExist(@PathVariable String alias) {
        boolean isExist = userAuthenticationService.isAliasExist(alias);
        return CommonHttpResponse.OK(isExist);
    }

    @RequestMapping(value = "/delete/{userIndex}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAccount(@PathVariable int userIndex) {
        // Authorization check
        UserAuthenticationVO performer = authFacade.getPrincipal();
        if (performer.getUser_idx() != userIndex && !performer.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Delete account
        UserAuthenticationVO target = userAuthenticationService.getUserAuthentication(userIndex);
        try {
            userInformationService.deleteUserInformation(userIndex);
            userAuthenticationService.deleteUserAuthentication(userIndex);
        } catch (Exception exception) {
            logger.error("Failed to delete account.", exception);
            return CommonHttpResponse.INTERNAL_SERVER_ERROR;
        }

        logger.info("User account deleted; deleted user_idx={}, name={}; performed by user_idx={}, name={}",
                target.getUser_idx(), target.getName(), performer.getUser_idx(), performer.getName());
        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = "/approval/{userIndex}", method = RequestMethod.PUT)
    public ResponseEntity<Object> pendingUserApproval(@PathVariable int userIndex, @RequestBody boolean approved) {
        // Authorization check
        if (!authFacade.getPrincipal().isAdmin())
            throw new UnauthorizedException();

        // Not for granted account
        UserAuthenticationVO user = userAuthenticationService.getUserAuthentication(userIndex);
        if (user == null || user.getPermission())
            return CommonHttpResponse.BAD_REQUEST;

        // Approve
        if (approved) {
            try {
                userAuthenticationService.grantPermission(userIndex);
                logger.info("Pending user granted; user_idx={}, name={}", userIndex, user.getName());
            } catch (Exception exception) {
                logger.error("Failed to grant pending user.", exception);
                return CommonHttpResponse.INTERNAL_SERVER_ERROR;
            }
        } else {
            try {
                userInformationService.deleteUserInformation(userIndex);
                userAuthenticationService.denyPermission(userIndex);
                logger.info("Pending user denied; user_idx={}, name={}", userIndex, user.getName());
            } catch (Exception exception) {
                logger.error("Failed to deny pending user.", exception);
                return CommonHttpResponse.INTERNAL_SERVER_ERROR;
            }
        }

        // Delete notification
        List<NotificationVO> targetNotiList = notificationService.getNotificationListByRefer(4, userIndex);
        notificationService.deleteNotification(targetNotiList);

        return CommonHttpResponse.OK;
    }

}
