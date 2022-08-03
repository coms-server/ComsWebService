package coms.kw.ac.kr.server.controller;

import coms.kw.ac.kr.server.config.security.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageController implements ErrorController {
    public static final String ERROR_PATH = "/error";
    public static final String VIEW_PATH_PREFIX = "error/";

    private static final Logger logger = LoggerFactory.getLogger(ErrorPageController.class);

    @RequestMapping(value = ERROR_PATH)
    public String error(HttpServletRequest request, Exception exception) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());
            logger.info(generateErrorString(request, statusCode));

            if (statusCode == HttpStatus.NOT_FOUND.value())
                return VIEW_PATH_PREFIX + GlobalExceptionHandler.NOT_FOUND_VIEW_NAME;

            if (statusCode == HttpStatus.FORBIDDEN.value())
                return VIEW_PATH_PREFIX + GlobalExceptionHandler.NOT_FOUND_VIEW_NAME;

        }
        return VIEW_PATH_PREFIX + GlobalExceptionHandler.DEFAULT_ERROR_VIEW_NAME;

    }

    private final String generateErrorString(HttpServletRequest request, int statusCode) {
        StringBuilder builder = new StringBuilder();
        String alias = request.getRemoteUser();
        builder.append("Error from ");
        if (alias == null) {
            builder.append("non-login user");
        } else {
            builder.append("authentcated user, alias=");
            builder.append(alias);
        }
        builder.append("; ip_addr=");
        builder.append(request.getRemoteAddr());
        builder.append("; with status code: ");
        builder.append(statusCode);
        return builder.toString();
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}