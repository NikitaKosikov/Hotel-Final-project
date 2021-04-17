package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.Room;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.OrderService;
import by.epam_training.final_task.service.RoomService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GoToArchiveOrdersPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToArchiveOrdersPage.class.getName());

    private static final String ROOMS = "rooms";
    private static final String ORDERS = "orderRooms";
    private static final String STATUS_ORDER = "оплачен";
    private static final String COUNT_OF_SLIDERS_PAGE_ORDERS = "count_of_sliders_of_orders";
    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String FORWARD_TO_ARCHIVE_ORDERS_PAGE = "/WEB-INF/jsp/archive_orders.jsp";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String FORWARD_TO_ERROR_PAGE = "error.jsp";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String EMPTY_ORDER_LIST = "empty_order_list";
    private static final String EMPTY_ORDER_LIST_MESSAGE = "Locale.empty.list.of.order";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = UserSession.getUserFromSession(request);

        if (user==null){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("User wants to view the paid orders");

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        RoomService roomService = serviceProvider.getRoomService();

        List<Order> orderRooms;
        try {
            orderRooms = orderService.findOrderByStatus(user, STATUS_ORDER);
            if (!orderRooms.isEmpty()){
                Collections.reverse(orderRooms);

                request.setAttribute(ORDERS, orderRooms);
                logger.info("Paid orders fount");
                List<Room> rooms = roomService.findRoomsByOrders(orderRooms);
                request.setAttribute(ROOMS, rooms);
            }else {
                logger.info("Paid orders not found");
                request.setAttribute(EMPTY_ORDER_LIST, EMPTY_ORDER_LIST_MESSAGE);
            }


            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_ARCHIVE_ORDERS_PAGE);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while searching the archive orderRooms", e);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_ERROR_PAGE +
                    QUESTION_START_PARAMETER + ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
            requestDispatcher.forward(request, response);
        }
    }
}
