package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.command.ErrorParameter;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.dao.impl.exception.EmailOfUserAlreadyExistException;
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

public class SignUp implements Command {
    private static final Logger logger = Logger.getLogger(SignUp.class.getName());

    private static final String NAME_OF_USER_PARAMETER = "name";
    private static final String SURNAME_OF_USER_PARAMETER = "surname";
    private static final String EMAIL_OF_USER_PARAMETER = "email";
    private static final String PHONE_NUMBER_OF_USER_PARAMETER = "phone_number";
    private static final String PASSWORD_OF_USER_PARAMETER = "password";
    private static final String REPEAT_PASSWORD_OF_USER_PARAMETER = "password_repeat";
    private static final String PASSWORD_ERROR = "password_error";
    private static final String REGISTRATION_ERROR_MESSAGE = "Locale.error.user.already.exist";
    private static final String REDIRECT_TO_REGISTRATION_PAGE = "/Registration";
    private static final String REDIRECT_TO_INDEX_PAGE = "/Controller?command=gotoindexpage";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String USER_EMAIL_ERROR = "user_email_error";
    private static final String USER_PHONE_NUMBER_ERROR = "phone_number_error";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String SPACE_SEPARATE_FOR_PHONE_NUMBER = "\\s+";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String EMPTY_NAME_ERROR = "empty_name_error";
    private static final String EMPTY_SURNAME_ERROR = "empty_surname_error";
    private static final String EMPTY_REPEAT_PASSWORD_ERROR = "empty_repeat_password_error";
    private static final String EMPTY_PASSWORD_ERROR = "empty_password_error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("User wants to register");
        String password = request.getParameter(PASSWORD_OF_USER_PARAMETER).trim();
        String passwordRepeat = request.getParameter(REPEAT_PASSWORD_OF_USER_PARAMETER).trim();
        String name = request.getParameter(NAME_OF_USER_PARAMETER).trim();
        String surname = request.getParameter(SURNAME_OF_USER_PARAMETER).trim();
        String email = request.getParameter(EMAIL_OF_USER_PARAMETER).trim();
        String phoneNumber = request.getParameter(PHONE_NUMBER_OF_USER_PARAMETER).trim().replaceAll(SPACE_SEPARATE_FOR_PHONE_NUMBER,"");

        StringBuffer url = request.getRequestURL();
        System.out.println(url);

        request.setAttribute(NAME_OF_USER_PARAMETER, name);
        request.setAttribute(SURNAME_OF_USER_PARAMETER, surname);
        request.setAttribute(EMAIL_OF_USER_PARAMETER, email);
        request.setAttribute(PHONE_NUMBER_OF_USER_PARAMETER, phoneNumber);
        request.setAttribute(PASSWORD_OF_USER_PARAMETER, password);
        request.setAttribute(REPEAT_PASSWORD_OF_USER_PARAMETER, passwordRepeat);

        RegistrationInfo regInfo = new RegistrationInfo();
        regInfo.setName(name);
        regInfo.setSurname(surname);
        regInfo.setEmail(email);
        regInfo.setPhoneNumber(phoneNumber);
        regInfo.setPassword(password);

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        try {
            User user = userService.registration(regInfo, passwordRepeat);
            if (user==null){
                logger.info("User hasn't registered");
                response.sendRedirect(REDIRECT_TO_REGISTRATION_PAGE + QUESTION_START_PARAMETER +
                        USER_EMAIL_ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + REGISTRATION_ERROR_MESSAGE);
                return;
            }
            logger.info("User has registered");
            UserSession.putUserInSession(request, user);
            response.sendRedirect(REDIRECT_TO_INDEX_PAGE);
        } catch (UserException e) {
            logger.log(Level.ERROR, "Entered user components is invalid", e);
            String errorMessages = ErrorParameter.buildExceptionMessages(e);
            response.sendRedirect(REDIRECT_TO_REGISTRATION_PAGE + QUESTION_START_PARAMETER + errorMessages);
        } catch (ServiceException e) {

            logger.log(Level.FATAL, "A server error occurred while registration user", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER + ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        } catch (EmailOfUserAlreadyExistException e) {
            logger.log(Level.ERROR, "Email is invalid", e);
            response.sendRedirect(REDIRECT_TO_REGISTRATION_PAGE + QUESTION_START_PARAMETER +
                    USER_EMAIL_ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + e.getMessage());
        }
    }
}
