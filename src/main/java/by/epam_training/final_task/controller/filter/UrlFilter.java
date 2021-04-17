package by.epam_training.final_task.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UrlFilter implements Filter {
    private static final String URL = "url";
    private static final String COMMAND = "command";
    private static final String COMMAND_NAME_CHANGE_LOCALE = "changelocale";
    private static final String GET_METHOD = "GET";
    private static final String QUESTION_START_PARAMETER = "?";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String method = request.getMethod();
        String command = request.getParameter(COMMAND);

        if (GET_METHOD.equals(method) && !COMMAND_NAME_CHANGE_LOCALE.equalsIgnoreCase(command)){
            String requestUrl = getRequestUrl(request);
            HttpSession session = request.getSession();
            session.setAttribute(URL, requestUrl);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private static String getRequestUrl(HttpServletRequest request) {
        String parameters = request.getQueryString();
        String uri = request.getRequestURI();

        return uri + (parameters==null ? "" : (QUESTION_START_PARAMETER + parameters));
    }
}
