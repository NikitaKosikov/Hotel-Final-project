package by.epam_training.final_task.controller.command.impl.admin;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.AdditionalServiceService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockService implements Command {
    private static final Logger logger = Logger.getLogger(ChangeRoom.class.getName());

    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String SERVICE_ID_PARAMETER = "service_id";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String REDIRECT_TO_ADDITIONAL_SERVICE_PAGE = "/Controller?command=gotoadditionalservicespage";
    private static final String SUCCESSFULLY_DELETED_SERVICE = "deleted_service";
    private static final String SUCCESSFULLY_DELETED_SERVICE_MESSAGE = "Locale.successfully.block.service";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String ERROR_DELETED_SERVICE = "deleted_service_error";
    private static final String ERROR_DELETED_SERVICE_MESSAGE = "Locale.error.delete.service";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserSession.getUserFromSession(request);
        if (user == null && user.getRoleId()!=2) {
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("Admin wants to block additional service");

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        AdditionalServiceService additionalServiceService = serviceProvider.getAdditionalService();

        int serviceId = Integer.parseInt(request.getParameter(SERVICE_ID_PARAMETER));

        try {
            if(additionalServiceService.blockService(serviceId)){
                logger.info("Additional service successfully blocked");
                response.sendRedirect(REDIRECT_TO_ADDITIONAL_SERVICE_PAGE + SEPARATE_PARAMETERS + SUCCESSFULLY_DELETED_SERVICE +
                        EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SUCCESSFULLY_DELETED_SERVICE_MESSAGE);
                return;
            }
            logger.info("Additional service not blocked");
            response.sendRedirect(REDIRECT_TO_ADDITIONAL_SERVICE_PAGE + SEPARATE_PARAMETERS + ERROR_DELETED_SERVICE +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + ERROR_DELETED_SERVICE_MESSAGE);

        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while blocking a additional service", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER +
                    ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        }

    }
}
