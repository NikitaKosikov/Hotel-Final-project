package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import by.epam_training.final_task.service.UserService;
import by.epam_training.final_task.service.exception.InvalidPassword;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangePassword implements Command {
    private static final Logger logger = Logger.getLogger(ChangePassword.class.getName());

    private static final String CURRENT_PASSWORD_OF_USER = "current_password";
    private static final String NEW_PASSWORD_OF_USER = "new_password";
    private static final String REPEAT_PASSWORD_USER = "repeat_password";
    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String REDIRECT_TO_PROFILE_PAGE = "Controller?command=gotoprofilepage";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String PASSWORD_ERROR = "password_error";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String SUCCESSFUL_MESSAGE = "successful_message";
    private static final String LOCALE_SUCCESSFUL_MESSAGE_EDIT_PASSWORD = "Locale.successfully.edit.password";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = UserSession.getUserFromSession(request);
        if (user==null){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("User wants to change password");
        String currentPassword = request.getParameter(CURRENT_PASSWORD_OF_USER).trim();
        String newPassword = request.getParameter(NEW_PASSWORD_OF_USER).trim();
        String repeatPassword = request.getParameter(REPEAT_PASSWORD_USER).trim();

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        try {
            userService.changePassword(user,currentPassword, newPassword, repeatPassword);
            logger.info("Password successfully changed");
            response.sendRedirect(REDIRECT_TO_PROFILE_PAGE + SEPARATE_PARAMETERS +
                    SUCCESSFUL_MESSAGE + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + LOCALE_SUCCESSFUL_MESSAGE_EDIT_PASSWORD);
        } catch (ServiceException e) {

            logger.log(Level.FATAL, "A server error occurred while changing the password", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER +
                    ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        } catch (InvalidPassword e) {
            logger.log(Level.ERROR, "Password is invalid", e);
            response.sendRedirect(REDIRECT_TO_PROFILE_PAGE + SEPARATE_PARAMETERS + PASSWORD_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + e.getMessage());
        }

    }
}
