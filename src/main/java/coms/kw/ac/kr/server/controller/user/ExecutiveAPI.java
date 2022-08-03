package coms.kw.ac.kr.server.controller.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import coms.kw.ac.kr.server.controller.CommonHttpResponse;
import coms.kw.ac.kr.server.service.security.AuthenticationFacade;
import coms.kw.ac.kr.server.service.statics.StaticInformationService;
import coms.kw.ac.kr.server.service.user.ExecutiveManageService;
import coms.kw.ac.kr.server.service.user.UserInformationService;
import coms.kw.ac.kr.server.vo.user.ExecutiveVO;
import coms.kw.ac.kr.server.vo.user.UserAuthenticationVO;
import coms.kw.ac.kr.server.vo.user.UserInformationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(ExecutiveAPI.API_CONTEXT)
public class ExecutiveAPI {
    public static final String API_CONTEXT = "/executive";

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ExecutiveAPI.class);

    private final UserInformationService userInformationService;
    private final ExecutiveManageService executiveManageService;
    private final StaticInformationService staticInformation;
    private final AuthenticationFacade authFacade;

    @Autowired
    public ExecutiveAPI(UserInformationService userInformationService, ExecutiveManageService execManageService,
            StaticInformationService staticInfoService, AuthenticationFacade authFacade) {
        this.userInformationService = userInformationService;
        this.executiveManageService = execManageService;
        this.staticInformation = staticInfoService;
        this.authFacade = authFacade;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> insertExecutive(@RequestBody ExecutiveVO exec) {
        // Authorization check
        UserAuthenticationVO performer = authFacade.getPrincipal();
        if (!performer.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Value check
        Integer userIndex = exec.getUser_idx();
        if (userIndex == null)
            return CommonHttpResponse.BAD_REQUEST;
        UserInformationVO target = userInformationService.getUserInformation(userIndex);
        if (target == null)
            return CommonHttpResponse.BAD_REQUEST;
        if (exec.getNth() == null)
            return CommonHttpResponse.BAD_REQUEST;
        Integer position = exec.getPosition();
        if (position == null || staticInformation.findPositionByIndex(position) == null)
            return CommonHttpResponse.BAD_REQUEST;
        if (exec.getActive() == null)
            return CommonHttpResponse.BAD_REQUEST;

        // Insert executive
        try {
            executiveManageService.createNewExecutive(exec);
        } catch (Exception exception) {
            logger.error("Failed to add new executive.", exception);
            return CommonHttpResponse.INTERNAL_SERVER_ERROR;
        }

        logger.info("New executive added; new exec user_idx={}, name={}, active={}; granted by user_idx={}, name={}",
                target.getUser_idx(), target.getName(), exec.getActive(), performer.getUser_idx(), performer.getName());
        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateExecutive(@RequestBody ExecutiveVO exec) {
        // Authorization check
        UserAuthenticationVO performer = authFacade.getPrincipal();
        if (!performer.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Value check
        Integer execIndex = exec.getExec_idx();
        if (execIndex == null)
            return CommonHttpResponse.BAD_REQUEST;
        ExecutiveVO original = executiveManageService.getExecutiveInformation(execIndex);
        if (original == null)
            return CommonHttpResponse.BAD_REQUEST;

        if (exec.getNth() == null)
            return CommonHttpResponse.BAD_REQUEST;
        Integer position = exec.getPosition();
        if (position == null || staticInformation.findPositionByIndex(position) == null)
            return CommonHttpResponse.BAD_REQUEST;
        if (exec.getActive() == null)
            return CommonHttpResponse.BAD_REQUEST;

        // Check self-demote
        if (performer.getUser_idx().intValue() == original.getUser_idx()) {
            if (original.getActive() && !exec.getActive())
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        // Update executive
        try {
            executiveManageService.updateExecutiveInformation(exec);
        } catch (Exception exception) {
            logger.error("Failed to update executive.", exception);
            return CommonHttpResponse.INTERNAL_SERVER_ERROR;
        }

        UserInformationVO target = userInformationService.getUserInformation(original.getUser_idx());
        logger.info(
                "Executive information updated; target user_idx={}, name={}, active={}; performed by user_idx={}, name={}",
                target.getUser_idx(), target.getName(), exec.getActive(), performer.getUser_idx(), performer.getName());

        return CommonHttpResponse.OK;
    }

    @RequestMapping(value = "/{execIndex}", method = RequestMethod.GET)
    public ResponseEntity<String> getExecutive(@PathVariable int execIndex) {

        JsonNode node;
        try {
            ExecutiveVO executiveInfo = executiveManageService.getExecutiveInformation(execIndex);
            node = mapper.valueToTree(executiveInfo);
        } catch (Exception exception) {
            logger.error("Failed to load executiveInfo.", exception);
            return CommonHttpResponse.BAD_REQUEST(String.class);
        }
        return CommonHttpResponse.OK(node.toPrettyString());
    }

    @RequestMapping(value = "/{execIndex}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteExecutive(@PathVariable int execIndex) {
        // Authorization check
        UserAuthenticationVO performer = authFacade.getPrincipal();
        if (!performer.isAdmin())
            return CommonHttpResponse.UNAUTHORIZED;

        // Value check
        ExecutiveVO original = executiveManageService.getExecutiveInformation(execIndex);
        if (original == null)
            return CommonHttpResponse.BAD_REQUEST;
        if (performer.getUser_idx().intValue() == original.getUser_idx())
            return CommonHttpResponse.BAD_REQUEST;

        // Check self-demote
        if (performer.getUser_idx().intValue() == original.getUser_idx() && original.getActive())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        // Delete executive
        try {
            executiveManageService.deleteExecutiveInformation(execIndex);
        } catch (Exception exception) {
            logger.error("Failed to update executive.", exception);
            return CommonHttpResponse.INTERNAL_SERVER_ERROR;
        }

        UserInformationVO target = userInformationService.getUserInformation(original.getUser_idx());
        logger.info("Executive information deleted; target user_idx={}, name={}; performed by user_idx={}, name={}",
                target.getUser_idx(), target.getName(), performer.getUser_idx(), performer.getName());

        return CommonHttpResponse.OK;
    }
}
