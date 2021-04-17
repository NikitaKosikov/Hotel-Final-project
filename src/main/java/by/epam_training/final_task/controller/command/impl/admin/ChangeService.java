package by.epam_training.final_task.controller.command.impl.admin;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.command.ErrorParameter;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.AdditionalServiceService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import by.epam_training.final_task.service.exception.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeService implements Command {
    private static final Logger logger = Logger.getLogger(ChangeService.class.getName());

    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String REDIRECT_TO_ADDITIONAL_SERVICE_PAGE = "/Controller?command=gotoadditionalservicespage";
    private static final String REDIRECT_TO_CHANGE_SERVICE_PAGE = "/Controller?command=gotochangeservicepage";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String SUCCESSFULLY_CHANGED_SERVICE = "changed_service";
    private static final String SUCCESSFULLY_CHANGED_SERVICE_MESSAGE = "Locale.successfully.changed.service";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String SERVICE_NAME_PARAMETER = "name";
    private static final String SERVICE_COST_PARAMETER = "cost";
    private static final String SERVICE_ID_PARAMETER = "service_id";
    private static final String ERROR_CHANGE_SERVICE = "change_service_error";
    private static final String ERROR_CHANGE_SERVICE_MESSAGE = "Locale.error.changed.service";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserSession.getUserFromSession(request);
        if (user==null && user.getRoleId()!=2){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("Admin wants to change additional service");

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        AdditionalServiceService additionalServiceService = serviceProvider.getAdditionalService();

        String name = request.getParameter(SERVICE_NAME_PARAMETER).trim();
        String cost = request.getParameter(SERVICE_COST_PARAMETER).trim();
        int serviceId = Integer.parseInt(request.getParameter(SERVICE_ID_PARAMETER).trim());

        try {
            if (additionalServiceService.changeService(name, cost,serviceId)){
                logger.info("Additional service successfully changed");
                response.sendRedirect(REDIRECT_TO_ADDITIONAL_SERVICE_PAGE + SEPARATE_PARAMETERS + SUCCESSFULLY_CHANGED_SERVICE +
                        EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SUCCESSFULLY_CHANGED_SERVICE_MESSAGE);
                return;
            }
            logger.info("Additional service not changed");
            response.sendRedirect(REDIRECT_TO_ADDITIONAL_SERVICE_PAGE + SEPARATE_PARAMETERS + ERROR_CHANGE_SERVICE +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + ERROR_CHANGE_SERVICE_MESSAGE);
        } catch (AdditionalServiceException e) {

            logger.log(Level.ERROR, "Entered data for changing additional service is invalid", e);
            String errorMessages = ErrorParameter.buildExceptionMessages(e);
            response.sendRedirect(REDIRECT_TO_CHANGE_SERVICE_PAGE + SEPARATE_PARAMETERS + SERVICE_ID_PARAMETER +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + serviceId + SEPARATE_PARAMETERS + errorMessages);
        } catch (ServiceException e) {

            logger.log(Level.FATAL, "A server error occurred while changing the additional service", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER +
                    ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        }
    }
}
