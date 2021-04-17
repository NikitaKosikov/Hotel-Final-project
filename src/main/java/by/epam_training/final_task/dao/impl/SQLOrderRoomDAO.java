package by.epam_training.final_task.dao.impl;

import by.epam_training.final_task.dao.DAOException;
import by.epam_training.final_task.dao.OrderDAO;
import by.epam_training.final_task.dao.impl.connection_pool.ConnectionPool;
import by.epam_training.final_task.dao.impl.connection_pool.ConnectionPoolException;
import by.epam_training.final_task.entity.Card;
import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.ReservationDate;
import by.epam_training.final_task.entity.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SQLOrderRoomDAO implements OrderDAO {

    private static final Logger logger = Logger.getLogger(SQLOrderRoomDAO.class.getName());

    private static final String FIND_ROOM_BY_ID_ORDERS = "SELECT room_id FROM m2m_order_room WHERE order_id=?";

    private static final String FIND_COST_OF_ROOM_BY_ID ="SELECT cost_room FROM rooms WHERE room_id=?";

    private static final String FIND_ORDER_ID = "SELECT order_id FROM orders WHERE date_order=? AND user_id=?";

    private static final String RESERVATION_ROOM = "" +
            "SELECT orders.date_order, orders.arrival_date, orders.departure_date, orders.cost, orders.user_id, orders.status, m2m_order_room.room_id " +
            "FROM orders\n" +
            "INNER JOIN m2m_order_room ON orders.order_id = m2m_order_room.order_id "+
            "WHERE room_id = ?";

    private static final String INSERT_ORDER = "INSERT INTO orders " +
            "(date_order, arrival_date, departure_date, cost, user_id, status) VALUES (?,?,?,?,?,'забронирован')";

    private static final String BIND_ROOM_IN_ORDER = "INSERT INTO m2m_order_room (order_id, room_id) values (?,?)";

    private static final String UPDATE_ORDERS_STATUS_SQL = "UPDATE orders SET status = ? WHERE order_id=?";

    private static final String UPDATE_ORDER = "UPDATE orders " +
            "SET date_order=?, arrival_date=?, departure_date=?, cost=?  WHERE order_id=?";

    private static final String FIND_ORDERS_BY_STATUS = "SELECT * FROM orders WHERE user_id=? AND status=?";

    private static final String FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id=?";

    private static final String ROOM_ID_FROM_DB = "room_id";
    private static final String ORDER_ID_FROM_DB = "order_id";
    private static final String USER_ID_FROM_DB = "user_id";
    private static final String RETURN_STATUS_ORDER = "отменен";
    private static final String TO_PAY_STATUS_ORDER = "оплачен";
    private static final String STATUS_FROM_DB = "status";
    private static final String ARRIVAL_DATE_FROM_DB = "arrival_date";
    private static final String DEPARTURE_DATE_FROM_DB = "departure_date";
    private static final String DATE_ORDER_FROM_DB = "date_order";
    private static final String COST_FROM_DB = "cost";
    private static final String COST_ROOM_FROM_DB = "cost_room";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public boolean reservationOrder(ReservationDate resDate, User user, int roomId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(FIND_COST_OF_ROOM_BY_ID);

            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();
            int costRoom = findCostRoom(resultSet);
            logger.info("Cost room received");

            preparedStatement = connection.prepareStatement(RESERVATION_ROOM);

            preparedStatement.setInt(1,roomId);
            resultSet = preparedStatement.executeQuery();
            int totalCost;
            if(checkAvailableRoom(resultSet, resDate)){
                int countDaysReservation = findCountDaysReservation(resDate);
                totalCost = countDaysReservation * costRoom;
            }else {
                logger.info("Room unavailable");
                return false;
            }
            logger.info("Room access");

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(INSERT_ORDER);
            preparedStatement.setObject(1, resDate.getOrderDate());
            preparedStatement.setObject(2, resDate.getArrivalDate());
            preparedStatement.setObject(3, resDate.getDepartureDate());
            preparedStatement.setInt(4, totalCost);
            preparedStatement.setInt(5, user.getUserId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(FIND_ORDER_ID);
            preparedStatement.setObject(1, resDate.getOrderDate());
            preparedStatement.setInt(2, user.getUserId());
            resultSet = preparedStatement.executeQuery();
            int orderId = findOrderId(resultSet);

            preparedStatement = connection.prepareStatement(BIND_ROOM_IN_ORDER);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

        }catch (SQLException | ConnectionPoolException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ignored) {

            }
            throw new DAOException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing resultSet", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing preparedStatement", e);
                }
            }
            if (connectionPool !=null ){
                connectionPool.releaseConnection(connection);
            }
        }
        return true;
    }

    @Override
    public boolean changeOrder(ReservationDate resDate, int orderId) throws DAOException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try (Connection connection = connectionPool.takeConnection()){

            preparedStatement = connection.prepareStatement(FIND_ROOM_BY_ID_ORDERS);

            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();
            int roomId = Integer.MIN_VALUE;
            if (resultSet.next()){
                roomId = Integer.parseInt(resultSet.getString(ROOM_ID_FROM_DB));
            }
            logger.info("Room id received");
            preparedStatement = connection.prepareStatement(FIND_COST_OF_ROOM_BY_ID);
            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();
            int costRoom = findCostRoom(resultSet);
            logger.info("Cost room received");

            preparedStatement = connection.prepareStatement(RESERVATION_ROOM);
            preparedStatement.setInt(1,roomId);
            resultSet = preparedStatement.executeQuery();
            int totalCost;
            if (checkAvailableRoom(resultSet,resDate)){
                logger.info("Room available");
                int countDaysReservation = findCountDaysReservation(resDate);
                totalCost = countDaysReservation * costRoom;
            }else {
                logger.info("Room unavailable");
                return false;
            }

            preparedStatement = connection.prepareStatement(UPDATE_ORDER);

            preparedStatement.setObject(1, resDate.getOrderDate());
            preparedStatement.setObject(2, resDate.getArrivalDate());
            preparedStatement.setObject(3, resDate.getDepartureDate());
            preparedStatement.setInt(4, totalCost);
            preparedStatement.setInt(5, orderId);
            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing resultSet", e);
                }
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing preparedStatement", e);
                }
            }
        }
        return true;
    }

    @Override
    public void returnOrder(int orderId) throws DAOException{

        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDERS_STATUS_SQL)) {

            preparedStatement.setString(1,RETURN_STATUS_ORDER);
            preparedStatement.setInt(2,orderId);
            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Order> findOrdersByStatus(User user, String status) throws DAOException {

        List<Order> orderRooms = new ArrayList<>();
        ResultSet resultSet = null;
        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDERS_BY_STATUS)){


            preparedStatement.setInt(1,user.getUserId());
            preparedStatement.setString(2, status);
            resultSet = preparedStatement.executeQuery();

            logger.info("Find orderRooms has started");
            while (resultSet.next()){

                int orderId = resultSet.getInt(ORDER_ID_FROM_DB);
                Date arrivalDate= resultSet.getDate(ARRIVAL_DATE_FROM_DB);
                Date departureDate= resultSet.getDate(DEPARTURE_DATE_FROM_DB);
                int cost = resultSet.getInt(COST_FROM_DB);
                int userId = resultSet.getInt(USER_ID_FROM_DB);
                Date dateOrder = resultSet.getDate(DATE_ORDER_FROM_DB);

                Order orderRoom = new Order();
                orderRoom.setOrderId(orderId);
                orderRoom.setArrivalDate(arrivalDate);
                orderRoom.setDepartureDate(departureDate);
                orderRoom.setCost(cost);
                orderRoom.setStatus(status);
                orderRoom.setUserId(userId);
                orderRoom.setOrderDate(dateOrder);

                orderRooms.add(orderRoom);
            }
            logger.info("Search for rooms ended");

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
        return orderRooms;
    }

    @Override
    public Order findOrderById(int orderId) throws DAOException {

        Order orderRoom = new Order();
        ResultSet resultSet = null;

        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement   preparedStatement = connection.prepareStatement(FIND_ORDER_BY_ID)) {

            preparedStatement.setInt(1,orderId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Date arrivalDate= resultSet.getDate(ARRIVAL_DATE_FROM_DB);
                Date departureDate= resultSet.getDate(DEPARTURE_DATE_FROM_DB);
                int cost = resultSet.getInt(COST_FROM_DB);
                int userId = resultSet.getInt(USER_ID_FROM_DB);
                Date dateOrder = resultSet.getDate(DATE_ORDER_FROM_DB);

                orderRoom.setOrderId(orderId);
                orderRoom.setArrivalDate(arrivalDate);
                orderRoom.setDepartureDate(departureDate);
                orderRoom.setCost(cost);
                orderRoom.setUserId(userId);
                orderRoom.setOrderDate(dateOrder);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
        return orderRoom;
    }


    @Override
    public void toPayOrder(Card cardInfo, int orderId) throws DAOException {


        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDERS_STATUS_SQL)){

            preparedStatement.setString(1,TO_PAY_STATUS_ORDER);
            preparedStatement.setInt(2,orderId);
            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Finds order id.
     *
     * @param resultSet set of orders.
     * @return Order id.
     * @throws SQLException if the columnLabel is not valid;
     *              if a database access error occurs or this method is
     *              called on a closed result set
     */
    public int findOrderId(ResultSet resultSet) throws SQLException {
        int orderId = 0;
        if (resultSet.next()){
            orderId = resultSet.getInt(ORDER_ID_FROM_DB);
        }
        return orderId;
    }

    /**
     * Finds room cost.
     *
     * @param resultSet set of room.
     * @return room cost.
     * @throws SQLException if the columnLabel is not valid;
     *              if a database access error occurs or this method is
     *              called on a closed result set
     */
    public int findCostRoom(ResultSet resultSet) throws SQLException {
        int costRoom = Integer.MAX_VALUE;
            if (resultSet.next()){
                costRoom = resultSet.getInt(COST_ROOM_FROM_DB);
                return costRoom;
            }
        return costRoom;
    }

    /**
     * Checks the room for availability on the specified date.
     *
     * @param resultSet set of rooms.
     * @param resDate the date by which we check.
     * @return True if room is available, false if room is unavailable.
     * @throws SQLException if the columnLabel is not valid;
     *              if a database access error occurs or this method is
     *              called on a closed result set
     */
    public boolean checkAvailableRoom(ResultSet resultSet, ReservationDate resDate) throws SQLException {

        while (resultSet.next()){
            String status = resultSet.getString(STATUS_FROM_DB);
            if (RETURN_STATUS_ORDER.equals(status)){
                continue;
            }
            Date arrivalDateDB = resultSet.getDate(ARRIVAL_DATE_FROM_DB);
            Date departureDateDB = resultSet.getDate(DEPARTURE_DATE_FROM_DB);

            java.util.Date arrivalDateOrder = resDate.getArrivalDate();
            java.util.Date departureDateOrder = resDate.getDepartureDate();

            if (arrivalDateOrder.compareTo(arrivalDateDB) >= 0 && arrivalDateOrder.compareTo(departureDateDB) <= 0){
                return false;
            }else if (departureDateOrder.compareTo(arrivalDateDB) >= 0 && departureDateOrder.compareTo(departureDateDB) <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the number of days for which the room is booked.
     *
     * @param resDate the date by which the number of days is searched.
     * @return The number of days for which the room is booked.
     */
    public int findCountDaysReservation(ReservationDate resDate){

        Calendar calendarForArrival = Calendar.getInstance();
        Calendar calendarForDeparture = Calendar.getInstance();

        java.util.Date arrivalDate = resDate.getArrivalDate();
        java.util.Date departureDate = resDate.getDepartureDate();

        calendarForArrival.setTime(arrivalDate);
        calendarForDeparture.setTime(departureDate);

        int countDays;
        int dayOfYears = calendarForArrival.getActualMaximum(Calendar.DAY_OF_YEAR);

        int arrivalYear = calendarForArrival.get(Calendar.YEAR);
        int departureYear = calendarForDeparture.get(Calendar.YEAR);

        int arrivalDay = calendarForArrival.get(Calendar.DAY_OF_YEAR);
        int departureDay = calendarForDeparture.get(Calendar.DAY_OF_YEAR);


        if (arrivalYear!=departureYear){
            int countYears = departureYear - arrivalYear;
            countDays = dayOfYears * countYears - arrivalDay + departureDay;
        }else {
            countDays = departureDay - arrivalDay;
        }

        return countDays;
    }

}
