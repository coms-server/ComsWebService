package coms.kw.ac.kr.server.config.security;

import coms.kw.ac.kr.server.exception.BadRequestException;
import coms.kw.ac.kr.server.exception.ForbiddenException;
import coms.kw.ac.kr.server.exception.InternalServerErrorException;
import coms.kw.ac.kr.server.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Map.Entry;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW_NAME = "default_error";
    public static final String BAD_REQUEST_VIEW_NAME = "400_bad_request";
    public static final String NOT_FOUND_VIEW_NAME = "404_not_found";
    public static final String SERVER_ERROR_VIEW_NAME = "503_internal_server_error";

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ BadRequestException.class, ClassCastException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBadRequest(HttpServletRequest request, BadRequestException exception) {
        logException(request, exception);
        return getErrorPageView(BAD_REQUEST_VIEW_NAME);
    }

    @ExceptionHandler({ ForbiddenException.class, NotFoundException.class, NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFound(HttpServletRequest request, Exception exception) {
        logException(request, exception);
        return getErrorPageView(NOT_FOUND_VIEW_NAME);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleServerError(HttpServletRequest request, InternalServerErrorException exception) {
        logException(request, exception);
        return getErrorPageView(SERVER_ERROR_VIEW_NAME);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleDefault(HttpServletRequest request, Exception exception) {
        // Default uncaught exception handling is 404 not found.
        logException(request, exception);
        return getErrorPageView(NOT_FOUND_VIEW_NAME);
    }

    public static void logException(HttpServletRequest request, Throwable exception) {
        if (!logger.isErrorEnabled())
            return;

        StringBuilder builder = new StringBuilder();
        String alias = request.getRemoteUser();
        String client;
        if (alias == null)
            client = "non-login user; ip_addr=" + request.getRemoteAddr();
        else
            client = "authenticated user; alias=" + alias;
        String requestURI = request.getRequestURI();
        Map<String, String[]> params = request.getParameterMap();

        builder.append("Exception occured by " + client + System.lineSeparator());
        builder.append("Reqeusted URI:" + requestURI + System.lineSeparator());
        builder.append("Parameters:" + System.lineSeparator());
        for (Entry<String, String[]> param : params.entrySet()) {
            if (param.getValue() == null)
                builder.append("\t" + param.getKey() + ":null" + System.lineSeparator());
            else if (param.getValue().length == 1) {
                builder.append("\t" + param.getKey() + ":" + param.getValue()[0] + System.lineSeparator());
            } else {
                builder.append("\t" + param.getKey() + ":{");
                for (String val : param.getValue()) {
                    builder.append(System.lineSeparator() + "\t\t" + val + ",");
                }
                builder.deleteCharAt(builder.length() - 1);
                builder.append(System.lineSeparator() + "\t}" + System.lineSeparator());
            }
        }
        
        if(exception != null) {
            StackTraceElement[] stack = exception.getStackTrace();
            builder.append("Exception:" + exception.toString() + System.lineSeparator());
            for (int i = 0; i < 6; i++)
                builder.append("\t" + stack[i].toString() + System.lineSeparator());
        }

        logger.error(builder.toString());
    }

    public static ModelAndView getErrorPageView(String viewname) {
        ModelAndView view = new ModelAndView();
        view.setViewName("/error/" + viewname);
        return view;
    }
}