package coms.kw.ac.kr.server.controller.security;

import coms.kw.ac.kr.server.controller.CommonHttpResponse;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.tools.MailSenderProvider.MailSender;
import coms.kw.ac.kr.server.service.user.UserAuthenticationService;
import coms.kw.ac.kr.server.service.user.UserInformationService;
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

@Controller
@RequestMapping(PasswordAPI.API_CONTEXT)
public class PasswordAPI {
    public static final String API_CONTEXT = "/account";
    public static final String PASSWORD_URI = "/password";

    private static final char[] CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz"
            .toCharArray();
    private static final int MAX_INDEX = CHAR_SET.length - 1;
    private static final int RANDOM_PASSWORD_LENGTH = 16;

    private static final Logger logger = LoggerFactory.getLogger(PasswordAPI.class);

    private final UserAuthenticationService userAuthenticationService;
    private final UserInformationService userInformationService;
    private final AuthenticationFacade authFacade;
    private final MailSender mailSender;

    @Autowired
    public PasswordAPI(UserAuthenticationService userAuthenticationService,
            UserInformationService userInformationService, AuthenticationFacade authFacade, MailSender mailSender) {
        this.userAuthenticationService = userAuthenticationService;
        this.userInformationService = userInformationService;
        this.authFacade = authFacade;
        this.mailSender = mailSender;
    }

    @RequestMapping(value = PASSWORD_URI + "/update/{userIndex}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updatePassword(@PathVariable int userIndex,
            @RequestBody UserAuthenticationVO form) {
        // Authorization check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (user.getUser_idx() != userIndex)
            return CommonHttpResponse.UNAUTHORIZED;

        // Update password
        boolean updateSuccess;
        try{
            updateSuccess = userAuthenticationService.updateUserPassword(userIndex, form.getPassword());
        }catch(Exception exception) {
            logger.error("Failed to update password.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }
        
        if (updateSuccess)
            return CommonHttpResponse.OK;
        else
            return CommonHttpResponse.BAD_REQUEST;
    }

    @RequestMapping(PASSWORD_URI + "/reset/{userIndex}")
    public ResponseEntity<Object> resetPassword(@PathVariable int userIndex) {
        // Authorization check
        UserAuthenticationVO performer = authFacade.getPrincipal();
        if (!performer.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Get target
        UserInformationVO target = userInformationService.getUserInformation(userIndex);
        if (target == null)
            return CommonHttpResponse.BAD_REQUEST;

        // Create random password
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < RANDOM_PASSWORD_LENGTH; i++)
            builder.append(CHAR_SET[(int) (MAX_INDEX * Math.random())]);
        String randomPassword = builder.toString();

        // Send mail
        try{
            userAuthenticationService.updateUserPassword(target.getUser_idx(), randomPassword);
            String alias = userAuthenticationService.getUserAuthentication(target.getUser_idx()).getAlias();
            String content = "id: " + alias + " / pw: " + randomPassword;
            mailSender.sendMail(target.getEmail_addr(), "COM'S 계정 비밀번호 재설정 안내", content);
        }catch(Exception exception) {
            logger.error("Failed to reset password.", exception);
            return CommonHttpResponse.INTERNAL_SERVER_ERROR;
        }

        logger.info("User's password reset; target {}({}), issued by {}({})", target.getName(), target.getUser_idx(),
                performer.getName(), performer.getUser_idx());
                
        return CommonHttpResponse.OK;
    }

}
