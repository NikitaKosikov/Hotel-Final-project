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

public class AddNewService implements Command {
    private static final Logger logger = Logger.getLogger(AddNewService.class.getName());

    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String COST_PARAMETER = "cost";
    private static final String NAME_PARAMETER = "name";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String REDIRECT_TO_ADDITIONAL_SERVICE_PAGE = "/Controller?command=gotoadditionalservicespage";
    private static final String REDIRECT_TO_ADD_SERVICE_PAGE = "/Controller?command=gotoaddservicepage";
    private static final String EXIST_SERVICE = "exist_service";
    private static final String EXIST_SERVICE_MESSAGE = "Service already exist";
    private static final String SUCCESSFULLY_ADDED_SERVICE = "added_service";
    private static final String SUCCESSFULLY_ADDED_SERVICE_MESSAGE = "Service added";
    private static final String SEPARATE_PARAMETERS = "&";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserSession.getUserFromSession(request);
        if (user==null && user.getRoleId()!=2){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("Admin wants to add additional service");

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        AdditionalServiceService additionalServiceService = serviceProvider.getAdditionalService();

        String name = request.getParameter(NAME_PARAMETER).trim();
        String cost = request.getParameter(COST_PARAMETER).trim();

        try {
            if (additionalServiceService.addService(name, cost)){
                logger.info("Additional service successfully added");
                response.sendRedirect(REDIRECT_TO_ADDITIONAL_SERVICE_PAGE + SEPARATE_PARAMETERS + SUCCESSFULLY_ADDED_SERVICE +
                        EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SUCCESSFULLY_ADDED_SERVICE_MESSAGE);
                return;
            }
            logger.info("Additional service not added");
            response.sendRedirect(REDIRECT_TO_ADDITIONAL_SERVICE_PAGE + SEPARATE_PARAMETERS + EXIST_SERVICE +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + EXIST_SERVICE_MESSAGE);
        }catch (AdditionalServiceException e) {
            logger.log(Level.ERROR, "Entered data for adding additional service is invalid", e);
            String errorMessages = ErrorParameter.buildExceptionMessages(e);
            response.sendRedirect(REDIRECT_TO_ADD_SERVICE_PAGE+ SEPARATE_PARAMETERS + errorMessages);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while add additional service", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER +
                    ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        }
    }
}
