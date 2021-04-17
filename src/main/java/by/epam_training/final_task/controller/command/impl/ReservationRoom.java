package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.ReservationDate;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.OrderService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import by.epam_training.final_task.service.exception.InvalidFormatDate;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReservationRoom implements Command {
    private static final Logger logger = Logger.getLogger(ReservationRoom.class.getName());

    private static final String ROOM_ID_FROM_PARAMETER = "room_id";
    private static final String FORMAT_ORDER_DATE = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_ARRIVAL_DATE = "yyyy-MM-dd";
    private static final String FORMAT_DEPARTURE_DATE = "yyyy-MM-dd";
    private static final String ARRIVAL_DATE_ORDER = "arrival_date";
    private static final String DEPARTURE_DATE_ORDER = "departure_date";
    private static final String ERROR_DATE = "error_date";
    private static final String INVALID_DATE = "Locale.error.format.date";
    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String REDIRECT_TO_INDEX_PAGE = "/Controller?command=gotoindexpage";
    private static final String REDIRECT_TO_ACTIVE_ORDERS_PAGE = "/Controller?command=gotoactiveorderspage";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String ACCESS_ROOM = "access_room";
    private static final String ACCESS_ROOM_MESSAGE = "Locale.unavailable.room";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        User user = UserSession.getUserFromSession(request);
        if (user==null){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }
        logger.info("User wants to reservation room");
        int roomId = Integer.parseInt(request.getParameter(ROOM_ID_FROM_PARAMETER));

        ReservationDate resDate = new ReservationDate();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_ORDER_DATE);
            Date orderDate = dateFormat.parse(dateFormat.format(new Date()));

            Date arrivalDate = new SimpleDateFormat(FORMAT_ARRIVAL_DATE).parse(request.getParameter(ARRIVAL_DATE_ORDER));
            Date departureDate = new SimpleDateFormat(FORMAT_DEPARTURE_DATE).parse(request.getParameter(DEPARTURE_DATE_ORDER));

            resDate.setOrderDate(orderDate);
            resDate.setArrivalDate(arrivalDate);
            resDate.setDepartureDate(departureDate);
        } catch (ParseException e) {
            logger.log(Level.ERROR, "Entered format date is invalid", e);
            response.sendRedirect(REDIRECT_TO_INDEX_PAGE + SEPARATE_PARAMETERS + ERROR_DATE +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + INVALID_DATE);
            return;
        }
        logger.info("Entered order date is valid");
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();

        try {
            if (orderService.reservationOrder(resDate, user, roomId)){
                logger.info("User has booked a room");
                response.sendRedirect(REDIRECT_TO_ACTIVE_ORDERS_PAGE);
            }else {
                logger.info("User did not book a room");
                response.sendRedirect(REDIRECT_TO_INDEX_PAGE + SEPARATE_PARAMETERS + ACCESS_ROOM +
                        EQUALLY_BETWEEN_PARAMETER_AND_VALUE + ACCESS_ROOM_MESSAGE);
            }

        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while reservation order", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER + ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        } catch (InvalidFormatDate e) {
            logger.log(Level.ERROR, "Entered order date is invalid", e);
            response.sendRedirect(REDIRECT_TO_INDEX_PAGE + SEPARATE_PARAMETERS + ERROR_DATE +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + e.getMessage());
        }
    }
}
