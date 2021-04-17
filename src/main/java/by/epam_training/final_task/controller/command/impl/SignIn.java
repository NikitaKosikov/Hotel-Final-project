package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import by.epam_training.final_task.service.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;


public class SignIn implements Command {
    private static final Logger logger = Logger.getLogger(SignIn.class.getName());

    private static final String EMAIL_OF_USER_FROM_PARAMETER = "email";
    private static final String PASSWORD_OF_USER_FROM_PARAMETER = "password";
    private static final String LOCALE_USER_ERROR = "Locale.incorrectly.user.sign.in";
    private static final String ERROR = "error";
    private static final String FORWARD_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String REDIRECT_TO_INDEX_PAGE = "/Controller?command=gotoindexpage";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("User wants to sign in");
        String email = request.getParameter(EMAIL_OF_USER_FROM_PARAMETER);
        String password = request.getParameter(PASSWORD_OF_USER_FROM_PARAMETER);

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        try {
            User user = userService.authorization(email, password);
            if (user==null) {
                logger.info("There is no such user");
                request.setAttribute(ERROR, LOCALE_USER_ERROR);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_AUTHORIZATION_PAGE);
                requestDispatcher.forward(request, response);
                return;
            }
            logger.info("User exist");
            UserSession.putUserInSession(request, user);
            response.sendRedirect(REDIRECT_TO_INDEX_PAGE);

        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while sign in system", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER + ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        }
    }
}
