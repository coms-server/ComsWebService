package coms.kw.ac.kr.server.config.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

public class XSSFilteredRequestWrapper extends HttpServletRequestWrapper {
    private static final int FLAG_CI = Pattern.CASE_INSENSITIVE;
    private static final int FLAG_DEFAULT = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL;

    private static final Pattern PATTERN_SCRIPT         = Pattern.compile("<script>(.*?)</script>", FLAG_CI);
    private static final Pattern PATTERN_SRC1           = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", FLAG_DEFAULT);
    private static final Pattern PATTERN_SRC2           = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", FLAG_DEFAULT);
    private static final Pattern PATTERN_CLOSING_SCRIPT = Pattern.compile("</script>", FLAG_CI);
    private static final Pattern PATTERN_INNER_SCRIPT   = Pattern.compile("<script(.*?)>", FLAG_DEFAULT);
    private static final Pattern PATTERN_EVAL           = Pattern.compile("eval\\((.*?)\\)", FLAG_DEFAULT);
    private static final Pattern PATTERN_EXPRESSION     = Pattern.compile("expression\\((.*?)\\)", FLAG_DEFAULT);
    private static final Pattern PATTERN_JAVASCRIPT     = Pattern.compile("javascript:", FLAG_CI);

    public XSSFilteredRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        return xssFilter(header);
    }

    @Override
    public String getParameter(String parameter) {
        String param = super.getParameter(parameter);

        return xssFilter(param);
    }

    @Override
    public String[] getParameterValues(String parameter) {

        String[] params = super.getParameterValues(parameter);

        if (params == null)
            return null;

        int count = params.length;
        String[] filteredParams = new String[count];
        for (int i = 0; i < count; i++) 
            filteredParams[i] = xssFilter(params[i]);

        return filteredParams;
    }

    private String xssFilter(String value) {
        if(value != null){
            //Null char clear
            value = value.replaceAll("", "");

            //XSS Filter
            value = PATTERN_SCRIPT.matcher(value).replaceAll("");
            value = PATTERN_SRC1.matcher(value).replaceAll("");
            value = PATTERN_SRC2.matcher(value).replaceAll("");
            value = PATTERN_CLOSING_SCRIPT.matcher(value).replaceAll("");
            value = PATTERN_INNER_SCRIPT.matcher(value).replaceAll("");
            value = PATTERN_EVAL.matcher(value).replaceAll("");
            value = PATTERN_EXPRESSION.matcher(value).replaceAll("");
            value = PATTERN_JAVASCRIPT.matcher(value).replaceAll("");

            // //Escape HTML
            // value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("&", "&amp;").replaceAll("\"", "&quot;")
            //         .replaceAll("\'", "&apos;");
        }
        return value;
    }

}