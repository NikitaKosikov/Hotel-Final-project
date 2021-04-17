package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.Card;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.*;
import by.epam_training.final_task.service.exception.InvalidCardInfo;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ToPayOrder implements Command {
    private static final Logger logger = Logger.getLogger(ToPayOrder.class.getName());

    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String REDIRECT_TO_PAY_ORDER_PAGE = "Controller?command=gotopaymentpage";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String TYPE_OF_CARD_PARAMETER = "type_of_card";
    private static final String ORDER_ID_PARAMETER = "order_id";
    private static final String CARD_NUMBER_PARAMETER = "card_number";
    private static final String CVV_PARAMETER = "CVV";
    private static final String VALIDITY_CARD_PARAMETER = "validity_card";
    private static final String OWNER_CARD_PARAMETER = "owner_card";
    private static final String REDIRECT_TO_ARCHIVE_ORDERS_PAGE = "/Controller?command=gotoarchiveorderspage";
    private static final String ERROR = "error";
    private static final String ERROR_OF_PAYMENT = "error_of_payment";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String SPACE_SEPARATE_FOR_CARD_NUMBER = "\\s+";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String SEPARATE_PARAMETERS = "&";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = UserSession.getUserFromSession(request);
        if (user==null){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }
        logger.info("User wants to pay order");

        String isSaveCard= request.getParameter("is_save_card");
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER).trim());
        String typeOfCard = request.getParameter(TYPE_OF_CARD_PARAMETER).trim();
        String cardNumber = request.getParameter(CARD_NUMBER_PARAMETER).trim().replaceAll(SPACE_SEPARATE_FOR_CARD_NUMBER, "");
        String CVV = request.getParameter(CVV_PARAMETER).trim();
        String validityCard = request.getParameter(VALIDITY_CARD_PARAMETER).trim();
        String ownerCard = request.getParameter(OWNER_CARD_PARAMETER).trim();

        Card cardInfo = new Card();
        cardInfo.setTypeOfCard(typeOfCard);
        cardInfo.setCardNumber(cardNumber);
        cardInfo.setCVV(CVV);
        cardInfo.setValidityCard(validityCard);
        cardInfo.setOwnerCard(ownerCard);

        ServiceProvider provider = ServiceProvider.getInstance();
        OrderService orderService = provider.getOrderService();
        try {
            orderService.toPayOrder(cardInfo, orderId);
            logger.info("Payment was successful");

            response.sendRedirect(REDIRECT_TO_ARCHIVE_ORDERS_PAGE);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while paying the order", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER + ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        } catch (InvalidCardInfo e) {
            logger.log(Level.ERROR, "Entered card info is invalid", e);
            response.sendRedirect(REDIRECT_TO_PAY_ORDER_PAGE + SEPARATE_PARAMETERS + ORDER_ID_PARAMETER +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + orderId + SEPARATE_PARAMETERS + ERROR_OF_PAYMENT +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + e.getMessage());
        }
    }
}
