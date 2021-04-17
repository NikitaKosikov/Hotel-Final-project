package by.epam_training.final_task.service;

import by.epam_training.final_task.entity.Card;
import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.ReservationDate;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.exception.InvalidCardInfo;
import by.epam_training.final_task.service.exception.InvalidFormatDate;

import java.util.List;

public interface OrderService {
    boolean reservationOrder(ReservationDate resInfo, User user, int roomId) throws ServiceException, InvalidFormatDate;
    boolean changeOrder(ReservationDate resDate, int orderId) throws ServiceException, InvalidFormatDate;
    void returnOrder(int orderId) throws ServiceException;
    List<Order> findOrderByStatus(User user, String status) throws ServiceException;
    Order findOrderById(int orderId) throws ServiceException;
    void toPayOrder(Card cardInfo, int orderId) throws ServiceException, InvalidCardInfo;
}
