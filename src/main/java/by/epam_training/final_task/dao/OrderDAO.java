package by.epam_training.final_task.dao;

import by.epam_training.final_task.entity.Card;
import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.ReservationDate;
import by.epam_training.final_task.entity.User;

import java.util.List;

public interface OrderDAO {

    /**
     * Finds user orders with certain status.
     *
     * @param status the status of additional service: open or blocked.
     * @return List of found orders.
     * @throws DAOException if an SQL error occurs.
     */
    List<Order> findOrdersByStatus(User user, String status) throws DAOException;

    /**
     * Finds user order with specific id.
     *
     * @param orderId
     * @return Found order.
     * @throws DAOException if an SQL error occurs.
     */
    Order findOrderById(int orderId) throws DAOException;

    /**
     * Reservation a room by the user.
     *
     * @param resDate info about date of order.
     * @param user the user who reservation the order
     * @param roomId the room that is being reservation
     * @return True if the order was successfully booked, false if the room is unavailable on this date.
     * @throws DAOException if an SQL error occurs.
     */
    boolean reservationOrder(ReservationDate resDate, User user, int roomId) throws DAOException;

    /**
     * Changes the order date.
     *
     * @param resDate info about date of order.
     * @param orderId
     * @return True if the order was successfully changed, false if the room is unavailable on this date.
     * @throws DAOException if an SQL error occurs.
     */
    boolean changeOrder(ReservationDate resDate, int orderId) throws DAOException;

    /**
     * Return the user's order.
     *
     * @param orderId
     * @throws DAOException if an SQL error occurs.
     */
    void returnOrder(int orderId) throws DAOException;

    /**
     * Payment for the order.
     *
     * @param cardInfo info about user's card.
     * @param orderId
     * @throws DAOException if an SQL error occurs.
     */
    void toPayOrder(Card cardInfo, int orderId) throws DAOException;
}
