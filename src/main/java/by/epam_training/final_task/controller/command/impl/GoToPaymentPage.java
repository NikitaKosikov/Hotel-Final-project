package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.OrderService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPaymentPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToPaymentPage.class.getName());

    private static final String ORDER_ID_FROM_PARAMETER = "order_id";
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String COST_ATTRIBUTE = "cost";
    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String FORWARD_TO_PAYMENT_PAGE = "/WEB-INF/jsp/payment.jsp";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String ERROR = "error";
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

        int orderId = Integer.parseInt(request.getParameter(ORDER_ID_FROM_PARAMETER));

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();

        Order orderRoom = null;
        try {
            orderRoom = orderService.findOrderById(orderId);
            int cost = orderRoom.getCost();
            request.setAttribute(COST_ATTRIBUTE, cost);

        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while searching order by id", e);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_ERROR_PAGE +
                    QUESTION_START_PARAMETER + ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
            requestDispatcher.forward(request, response);
        }

            request.setAttribute(ORDER_ATTRIBUTE, orderRoom);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_PAYMENT_PAGE);
        requestDispatcher.forward(request, response);
    }
}
