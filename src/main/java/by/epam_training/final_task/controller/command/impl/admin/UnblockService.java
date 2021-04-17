package by.epam_training.final_task.controller.command.impl.admin;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.AdditionalServiceService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnblockService implements Command {
    private static final Logger logger = Logger.getLogger(UnblockService.class.getName());

    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String REDIRECT_TO_BLOCKED_SERVICES_PAGE = "/Controller?command=gotoblockedservicespage";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String ERROR = "error";
    private static final String FORWARD_TO_ERROR_PAGE = "error.jsp";
    private static final String SERVICE_ID_PARAMETER = "service_id";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String SUCCESSFUL_UNBLOCK = "successful_unblock";
    private static final String SUCCESSFUL_UNBLOCK_MESSAGE = "Locale.successful.unblock";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserSession.getUserFromSession(request);
        if (user==null && user.getRoleId()!=2){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("User wants to unblock service");

        int serviceId = Integer.parseInt(request.getParameter(SERVICE_ID_PARAMETER));
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        AdditionalServiceService additionalServiceService = serviceProvider.getAdditionalService();
        try {
            additionalServiceService.unblockService(serviceId);
            logger.info("Service unblocked");
            response.sendRedirect(REDIRECT_TO_BLOCKED_SERVICES_PAGE + SEPARATE_PARAMETERS +
                    SUCCESSFUL_UNBLOCK + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SUCCESSFUL_UNBLOCK_MESSAGE);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while unblocking service", e);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_ERROR_PAGE +
                    QUESTION_START_PARAMETER + ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
            requestDispatcher.forward(request, response);
        }
    }
}
