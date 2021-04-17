package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.Room;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GoToActiveOrdersPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToActiveOrdersPage.class.getName());

    private static final String ROOMS = "rooms";
    private static final String ORDERS = "orderRooms";
    private static final String STATUS_ORDER = "забронирован";
    private static final String FORWARD_TO_ACTIVE_ORDERS_PAGE = "/WEB-INF/jsp/active_orders.jsp";
    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String ERROR = "error";
    private static final String EMPTY_ORDER_LIST = "empty_order_list";
    private static final String EMPTY_ORDER_LIST_MESSAGE = "Locale.empty.list.of.order";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String FORWARD_TO_ERROR_PAGE = "error.jsp";
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
        logger.info("User wants to view active orders");
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        RoomService roomService = serviceProvider.getRoomService();

        try {
            List<Order> orderRooms = orderService.findOrderByStatus(user, STATUS_ORDER);
            if (!orderRooms.isEmpty()){
                Collections.reverse(orderRooms);
                request.setAttribute(ORDERS, orderRooms);

                List<Room> rooms = roomService.findRoomsByOrders(orderRooms);
                request.setAttribute(ROOMS, rooms);
                logger.info("Active orders found");
            }else {
                logger.info("Active orders not found");
                request.setAttribute(EMPTY_ORDER_LIST, EMPTY_ORDER_LIST_MESSAGE);
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_ACTIVE_ORDERS_PAGE);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while searching the active orderRooms", e);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_ERROR_PAGE +
                    QUESTION_START_PARAMETER + ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
            requestDispatcher.forward(request, response);
        }
    }
}
