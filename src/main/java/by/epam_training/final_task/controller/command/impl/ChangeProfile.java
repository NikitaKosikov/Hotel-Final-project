package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.command.ErrorParameter;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.RegistrationInfo;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import by.epam_training.final_task.service.UserService;
import by.epam_training.final_task.service.exception.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeProfile implements Command {
    private static final Logger logger = Logger.getLogger(ChangeProfile.class.getName());

    private static final String NAME_OF_USER = "name";
    private static final String SURNAME_OF_USER = "surname";
    private static final String EMAIL_OF_USER = "email";
    private static final String PHONE_NUMBER_OF_USER = "phone_number";
    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String SPACE_SEPARATE_FOR_PHONE_NUMBER = "\\s+";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String ERROR = "error";
    private static final String USER_EMAIL_ERROR_ATTRIBUTE = "user_email_error";
    private static final String USER_EMAIL_ERROR_MESSAGE = "Locale.error.email.already.exist";
    private static final String SUCCESSFUL_MESSAGE = "successful_message";
    private static final String SUCCESSFUL_MESSAGE_EDIT_PROFILE = "Locale.successfully.edit.profile";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String REDIRECT_TO_PROFILE_PAGE = "Controller?command=gotoprofilepage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = UserSession.getUserFromSession(request);
        if (user==null){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("User wants to change personal data");
        String name = request.getParameter(NAME_OF_USER).trim();
        String surname = request.getParameter(SURNAME_OF_USER).trim();
        String email = request.getParameter(EMAIL_OF_USER).trim();
        String phoneNumber = request.getParameter(PHONE_NUMBER_OF_USER).trim().replaceAll(SPACE_SEPARATE_FOR_PHONE_NUMBER,"");

        RegistrationInfo regInfo = new RegistrationInfo();
        regInfo.setName(name);
        regInfo.setSurname(surname);
        regInfo.setEmail(email);
        regInfo.setPhoneNumber(phoneNumber);

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        try {
            user = userService.updateUser(user, regInfo);
            if (user == null){
                logger.info("Personal data not changed");
                response.sendRedirect(REDIRECT_TO_PROFILE_PAGE + SEPARATE_PARAMETERS +
                        USER_EMAIL_ERROR_ATTRIBUTE + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + USER_EMAIL_ERROR_MESSAGE);
                return;
            }
            logger.info("Personal data successfully changed");
            response.sendRedirect(REDIRECT_TO_PROFILE_PAGE + SEPARATE_PARAMETERS +
                    SUCCESSFUL_MESSAGE + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SUCCESSFUL_MESSAGE_EDIT_PROFILE);
        } catch (UserException e) {

            logger.log(Level.ERROR, "Entered data for changing personal data is invalid", e);
            String errorMessages = ErrorParameter.buildExceptionMessages(e);
            response.sendRedirect(REDIRECT_TO_PROFILE_PAGE + SEPARATE_PARAMETERS + errorMessages);
        } catch (ServiceException e) {

            logger.log(Level.FATAL, "A server error occurred while changing the profile");
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER +
                    ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        }
    }
}
