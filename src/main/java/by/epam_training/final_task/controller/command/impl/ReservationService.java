package by.epam_training.final_task.controller.command.impl;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationService implements Command {
    private static final Logger logger = Logger.getLogger(ReservationService.class.getName());


    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String REDIRECT_TO_ADDITIONAL_SERVICE_PAGE = "Controller?command=gotoadditionalservicespage";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String ERROR = "error";
    private static final String ORDER_ID_PARAMETER = "order_id";
    private static final String SERVICE_ID_PARAMETER = "service_id";
    private static final String FORMAT_ORDER_DATE = "yyyy-MM-dd";
    private static final String DATE_SERVICE = "date_service";
    private static final String ERROR_SERVICE = "error_service";
    private static final String INVALID_DATE = "Locale.invalid.format.date";
    private static final String SUCCESSFUL_MESSAGE = "successful_message";
    private static final String SUCCESSFUL_MESSAGE_ORDER_SERVICE = "Locale.successfully.reservation.service";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String REDIRECT_TO_ACTIVE_ORDERS_PAGE = "/Controller?command=gotoactiveorderspage";
    private static final String SELECT_SERVICE = "select_service";
    private static final String SELECT_SERVICE_MESSAGE = "Locale.hint.select.service";
    private static final String SELECT_ORDER = "select_order";
    private static final String SELECT_ORDER_MESSAGE = "Locale.hint.select.order";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserSession.getUserFromSession(request);
        if (user==null){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("User wants to reservation service");

        String serviceIdStr = request.getParameter(SERVICE_ID_PARAMETER);
        String orderIdStr = request.getParameter(ORDER_ID_PARAMETER);
        String dateServiceStr = request.getParameter(DATE_SERVICE);

        if (serviceIdStr==null){
            response.sendRedirect(REDIRECT_TO_ADDITIONAL_SERVICE_PAGE + SEPARATE_PARAMETERS + ORDER_ID_PARAMETER +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + orderIdStr + SEPARATE_PARAMETERS +
                    SELECT_SERVICE + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SELECT_SERVICE_MESSAGE);
            return;
        }

        if (orderIdStr==null){
            response.sendRedirect(REDIRECT_TO_ACTIVE_ORDERS_PAGE + SEPARATE_PARAMETERS + SERVICE_ID_PARAMETER +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + serviceIdStr + SEPARATE_PARAMETERS +
                    SELECT_ORDER + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SELECT_ORDER_MESSAGE +
                    SEPARATE_PARAMETERS + DATE_SERVICE + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + dateServiceStr);
            return;
        }

        int orderId = Integer.parseInt(orderIdStr);
        int serviceId = Integer.parseInt(serviceIdStr);

        Date dateService;
        try{
            dateService = new SimpleDateFormat(FORMAT_ORDER_DATE).parse(dateServiceStr);
        } catch (ParseException e) {
            logger.log(Level.ERROR, "Entered format date is invalid", e);
            response.sendRedirect(REDIRECT_TO_ADDITIONAL_SERVICE_PAGE + SEPARATE_PARAMETERS + ERROR_SERVICE +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + INVALID_DATE + SEPARATE_PARAMETERS + ORDER_ID_PARAMETER +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + orderId + SEPARATE_PARAMETERS + DATE_SERVICE +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + dateServiceStr);
            return;
        }
        logger.info("Entered order date is valid");

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        AdditionalServiceService additionalServiceService = serviceProvider.getAdditionalService();

        try {
            additionalServiceService.reservationService(orderId, serviceId, dateService);
            logger.info("User has booked a service");
            response.sendRedirect(REDIRECT_TO_ADDITIONAL_SERVICE_PAGE + SEPARATE_PARAMETERS + SUCCESSFUL_MESSAGE +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SUCCESSFUL_MESSAGE_ORDER_SERVICE);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while reservation service", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER + ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        }

    }
}
