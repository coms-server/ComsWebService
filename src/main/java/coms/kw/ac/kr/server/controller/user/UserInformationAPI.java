package coms.kw.ac.kr.server.controller.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import coms.kw.ac.kr.server.controller.CommonHttpResponse;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.statics.StaticInformationService;
import coms.kw.ac.kr.server.service.user.UserInformationService;
import coms.kw.ac.kr.server.vo.statics.MajorVO;
import coms.kw.ac.kr.server.vo.statics.PositionVO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping(UserInformationAPI.API_CONTEXT)
public class UserInformationAPI {
    public static final String API_CONTEXT = "/user";
    public static final String PROFILE_IMAGE_URI = "/profile-image";
    public static final String INTRO_URI = "/intro";
    public static final String INFORMATION_URI = "/information";

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(UserInformationAPI.class);

    private final UserInformationService userInformationService;
    private final StaticInformationService staticInformation;
    private final AuthenticationFacade authFacade;

    @Autowired
    public UserInformationAPI(UserInformationService uInfoService, AuthenticationFacade authFacade,
            StaticInformationService staticInfoService) {
        this.userInformationService = uInfoService;
        this.authFacade = authFacade;
        this.staticInformation = staticInfoService;
    }

    @RequestMapping(value = PROFILE_IMAGE_URI + "/{userIndex}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProfileImage(@PathVariable int userIndex, @RequestBody MultipartFile image)
            throws IOException {
        // Authorization check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (user.getUser_idx() != userIndex && !user.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Update profile image
        try (InputStream imageStream = image.getInputStream()) {
            userInformationService.updateProfileImage(userIndex, imageStream);
        } catch (IOException exception) {
            logger.error("Failed to update profile image.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }

        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = PROFILE_IMAGE_URI + "/{userIndex}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteProfileImage(@PathVariable int userIndex) throws IOException {
        // Authorization check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (user.getUser_idx() != userIndex && !user.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Reset profile image
        try {
            userInformationService.setDefaultProfileImage(userIndex);
        } catch (IOException exception) {
            logger.error("Failed to delete profile image.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }

        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = INTRO_URI, method = RequestMethod.PUT)
    public ResponseEntity<Object> updateUserIntro(@RequestBody UserInformationVO userInfo) {
        // Authorization check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (user.getUser_idx().intValue() != userInfo.getUser_idx())
            return CommonHttpResponse.UNAUTHORIZED;

        // Update user intro
        try {
            userInformationService.updateUserIntro(userInfo);
        } catch (Exception exception) {
            logger.error("Failed to update user intro.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }

        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = INFORMATION_URI, method = RequestMethod.PUT)
    public ResponseEntity<Object> updatePersonalInformation(@RequestBody UserInformationVO userInfo) {
        // Authorization check
        UserAuthenticationVO user = authFacade.getPrincipal();
        if (!(user.getUser_idx().intValue() == userInfo.getUser_idx() || user.isAdmin()))
            return CommonHttpResponse.UNAUTHORIZED;

        // Update user information
        try {
            UserInformationVO newUserInfo;
            if (user.isAdmin()) {
                UserInformationVO originUserInfo = userInformationService.getUserInformation(userInfo.getUser_idx());
                originUserInfo.setStatus(userInfo.getStatus());

                newUserInfo = originUserInfo;
            } else
                newUserInfo = userInfo;

            userInformationService.updateUserInformation(newUserInfo);
        } catch (Exception exception) {
            logger.error("Failed to update user information.", exception);
            return CommonHttpResponse.BAD_REQUEST;
        }

        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = "/get-major-list", method = RequestMethod.GET)
    public ResponseEntity<String> getMajorList() {

        JsonNode node;
        try {
            List<MajorVO> majorList = staticInformation.getMajorList();
            node = mapper.valueToTree(majorList);
        } catch (Exception exception) {
            logger.error("Failed to load majorList.", exception);
            return CommonHttpResponse.BAD_REQUEST(String.class);
        }
        return CommonHttpResponse.OK(node.toPrettyString());
    }

    @RequestMapping(value = "/get-position-list", method = RequestMethod.GET)
    public ResponseEntity<String> getPositionList() {

        JsonNode node;
        try {
            List<PositionVO> positionList = staticInformation.getPositionList();
            node = mapper.valueToTree(positionList);
        } catch (Exception exception) {
            logger.error("Failed to load positionList.", exception);
            return CommonHttpResponse.BAD_REQUEST(String.class);
        }
        return CommonHttpResponse.OK(node.toPrettyString());
    }
}