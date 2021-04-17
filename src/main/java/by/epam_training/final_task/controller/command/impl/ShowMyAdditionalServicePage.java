package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.AdditionalService;
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
import java.util.List;

public class ShowMyAdditionalServicePage implements Command {
    private static final Logger logger = Logger.getLogger(ShowMyAdditionalServicePage.class.getName());

    private static final String FORWARD_TO_MY_ADDITIONAL_SERVICES_PAGE = "/WEB-INF/jsp/my_additional_services.jsp";
    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String ORDER_ID_PARAMETER = "order_id";
    private static final String ADDITIONAL_SERVICES = "additional_services";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String FORWARD_TO_ERROR_PAGE = "error.jsp";
    private static final String EMPTY_SERVICE_LIST = "empty_service_list";
    private static final String EMPTY_ORDER_LIST_MESSAGE = "Locale.empty.list.of.order";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserSession.getUserFromSession(request);

        if (user==null){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("User wants to view their ordered services");

        int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        AdditionalServiceService additionalServiceService = serviceProvider.getAdditionalService();

        try {
            List<AdditionalService> additionalServices = additionalServiceService.findServicesByOrderId(orderId);
            if (!additionalServices.isEmpty()){
                logger.info("Services found");
                request.setAttribute(ADDITIONAL_SERVICES, additionalServices);
                request.setAttribute(EMPTY_SERVICE_LIST, EMPTY_ORDER_LIST_MESSAGE);
            }else {
                logger.info("Services not found");
                request.setAttribute(ADDITIONAL_SERVICES, additionalServices);
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_MY_ADDITIONAL_SERVICES_PAGE);
            requestDispatcher.forward(request, response);

        } catch (ServiceException e) {

            logger.log(Level.FATAL, "A server error occurred while searching the user additional services", e);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_ERROR_PAGE +
                    QUESTION_START_PARAMETER + ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
            requestDispatcher.forward(request, response);
        }


    }
}
