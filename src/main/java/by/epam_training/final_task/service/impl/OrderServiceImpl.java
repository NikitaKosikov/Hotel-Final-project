package by.epam_training.final_task.service.impl;

import by.epam_training.final_task.dao.DAOException;
import by.epam_training.final_task.dao.DAOProvider;
import by.epam_training.final_task.dao.OrderDAO;
import by.epam_training.final_task.entity.Card;
import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.ReservationDate;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.OrderService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.exception.InvalidCardInfo;
import by.epam_training.final_task.service.exception.InvalidFormatDate;
import by.epam_training.final_task.service.validation.CardValidator;
import by.epam_training.final_task.service.validation.OrderDateValidator;

import java.util.Date;
import java.util.List;


public class OrderServiceImpl implements OrderService{
    private static final String LOCALE_INVALID_ENTERED_DATE = "Locale.error.entered.date";
    private static final String LOCALE_INVALID_CARD_INFO = "Locale.error.card.info";


    @Override
    public boolean reservationOrder(ReservationDate resDate, User user, int roomId) throws ServiceException, InvalidFormatDate {

        Date arrivalDate = resDate.getArrivalDate();
        Date departureDate = resDate.getDepartureDate();
        if (!OrderDateValidator.isValidOrderDate(arrivalDate, departureDate)){
            throw new InvalidFormatDate(LOCALE_INVALID_ENTERED_DATE);
        }

        DAOProvider provider = DAOProvider.getInstance();
        OrderDAO orderDAO = provider.getOrderDAO();

        try {
            return orderDAO.reservationOrder(resDate, user, roomId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeOrder(ReservationDate resDate, int orderId) throws ServiceException, InvalidFormatDate {

        Date arrivalDate = resDate.getArrivalDate();
        Date departureDate = resDate.getDepartureDate();
        if (!OrderDateValidator.isValidOrderDate(arrivalDate, departureDate)){
            throw new InvalidFormatDate(LOCALE_INVALID_ENTERED_DATE);
        }

        DAOProvider provider = DAOProvider.getInstance();
        OrderDAO orderDAO = provider.getOrderDAO();

        try {
            return orderDAO.changeOrder(resDate, orderId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void returnOrder(int orderId) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        OrderDAO orderDAO = provider.getOrderDAO();

        try {
            orderDAO.returnOrder(orderId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findOrderByStatus(User user, String status) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        OrderDAO orderDAO = provider.getOrderDAO();

        List<Order> orderRoom;
        try {
            orderRoom = orderDAO.findOrdersByStatus(user, status);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
        return orderRoom;
    }

    @Override
    public Order findOrderById(int orderId) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        OrderDAO orderDAO = provider.getOrderDAO();

        Order orderRoom;
        try {
            orderRoom = orderDAO.findOrderById(orderId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
        return orderRoom;
    }

    @Override
    public void toPayOrder(Card cardInfo, int orderId) throws ServiceException, InvalidCardInfo {
        DAOProvider provider = DAOProvider.getInstance();
        OrderDAO orderDAO = provider.getOrderDAO();

        if (!CardValidator.isValidCard(cardInfo)){
            throw new InvalidCardInfo(LOCALE_INVALID_CARD_INFO);
        }

        try {
            orderDAO.toPayOrder(cardInfo, orderId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }
}
