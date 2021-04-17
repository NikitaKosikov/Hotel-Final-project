package by.epam_training.final_task.dao.impl;

import by.epam_training.final_task.dao.DAOException;
import by.epam_training.final_task.dao.RoomDAO;
import by.epam_training.final_task.dao.impl.connection_pool.ConnectionPool;
import by.epam_training.final_task.dao.impl.connection_pool.ConnectionPoolException;
import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.Room;
import by.epam_training.final_task.entity.criteria.Criteria;
import by.epam_training.final_task.entity.criteria.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class SQLRoomDAO implements RoomDAO {
    private static final Logger logger = Logger.getLogger(SQLRoomDAO.class.getName());

    private static final String FIND_ROOMS_BY_STATUS = "SELECT * FROM rooms where status=?";

    private static final String UPDATE_ROOM = "UPDATE rooms SET number_of_beds=?, apartment_class=?, cost_room=? " +
            "WHERE room_id=?";

    private static final String UPDATE_IMAGE_ROOM = "UPDATE rooms SET photo=? WHERE room_id=?";

    private static final String INSERT_ROOM = "INSERT INTO rooms (number_of_beds, apartment_class, cost_room) " +
            "VALUES (?,?,?)";

    private static final String BLOCK_ROOM = "UPDATE rooms SET status='заблокирован' where room_id=?";
    private static final String UNBLOCK_ROOM = "UPDATE rooms SET status='открыт' where room_id=?";

    private static final String FIND_ROOMS_BY_COST_CRITERIA = "SELECT * FROM rooms WHERE cost_room <= ?";

    private static final String FIND_AVAILABLE_ROOM = "SELECT arrival_date, departure_date FROM m2m_order_room " +
            "INNER JOIN rooms r on m2m_order_room.room_id = r.room_id " +
            "INNER JOIN orders o on m2m_order_room.order_id = o.order_id " +
            "WHERE r.room_id=?";

    private static final String FIND_ROOM_BY_ORDER_ID = "SELECT number_of_beds, apartment_class, r.photo, r.cost_room  " +
            "FROM m2m_order_room " +
            "INNER JOIN rooms r on m2m_order_room.room_id = r.room_id " +
            "WHERE order_id=?";

    private static final String FIND_ORDERS_BY_ROOM_ID = "SELECT * " +
            "FROM m2m_order_room " +
            "INNER JOIN orders o on m2m_order_room.order_id = o.order_id " +
            "WHERE room_id=? AND (status='забронирован' OR status='оплачен')";

    private static final String ROOM_ID_FROM_DB = "room_id";
    private static final String NUMBER_OF_BEDS_FROM_DB = "number_of_beds";
    private static final String APARTMENT_CLASS_FROM_DB = "apartment_class";
    private static final String COST_ROOM_FROM_DB = "cost_room";
    private static final String URL_PHOTO_FROM_DB = "photo";
    private static final String ARRIVAL_DATE_FROM_DB = "arrival_date";
    private static final String DEPARTURE_DATE_FROM_DB = "departure_date";
    private static final String STATUS_OPEN = "открыт";
    private static final String PHOTO_PATH = "images/room/";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public List<Room> findRoomsByStatus(String status) throws DAOException{

        List<Room> rooms = new ArrayList<>();
        ResultSet resultSet = null;
        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement  preparedStatement = connection.prepareStatement(FIND_ROOMS_BY_STATUS)){

            preparedStatement.setString(1, status);
            resultSet = preparedStatement.executeQuery();

            logger.info("Find rooms has started");
            while (resultSet.next()){
                int idRoom = resultSet.getInt(ROOM_ID_FROM_DB);
                int numberOfBeds = resultSet.getInt(NUMBER_OF_BEDS_FROM_DB);
                String apartmentClass = resultSet.getString(APARTMENT_CLASS_FROM_DB);
                int costRoom = resultSet.getInt(COST_ROOM_FROM_DB);
                String urlPhoto = resultSet.getString(URL_PHOTO_FROM_DB);

                Room room = new Room();
                room.setRoomId(idRoom);
                room.setNumberOfBeds(numberOfBeds);
                room.setApartmentClass(apartmentClass);
                room.setCost(costRoom);
                room.setUrlPhoto(urlPhoto);
                rooms.add(room);
            }
            logger.info("Search for rooms ended");
        }
        catch (SQLException | ConnectionPoolException e) {
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
        return rooms;
    }

    @Override
    public List<Room> findRoomsByCriteria(Criteria criteriaRoom) throws DAOException {

        PreparedStatement preparedStatement = null;
        PreparedStatement preparedFindAvailableRoom = null;
        ResultSet resultSet = null;
        ResultSet resultRoomsAvailable = null;

        List<Room> rooms = new ArrayList<>();
        Map<String, Object> roomDB = new HashMap<>();
        Map<String, Object> criteriaMap = criteriaRoom.getCriteria();
        try(Connection connection = connectionPool.takeConnection()){

            preparedStatement = connection.prepareStatement(FIND_ROOMS_BY_COST_CRITERIA);


            //select rooms by price for quickness
            if (!"".equals(criteriaMap.get(SearchCriteria.Room.COST.toString()))){
                Integer cost = (Integer) criteriaMap.get(SearchCriteria.Room.COST.toString());
                preparedStatement.setInt(1, cost);
                resultSet = preparedStatement.executeQuery();
                criteriaMap.put(SearchCriteria.Room.COST.toString(), "");
            }else {
                preparedStatement = connection.prepareStatement(FIND_ROOMS_BY_STATUS);
                preparedStatement.setString(1, STATUS_OPEN);
                resultSet = preparedStatement.executeQuery();
            }

            preparedFindAvailableRoom = connection.prepareStatement(FIND_AVAILABLE_ROOM);

            logger.info("Find rooms has started");

            while (resultSet.next()){

                int roomId = resultSet.getInt(ROOM_ID_FROM_DB);
                int numberOfBeds = resultSet.getInt(NUMBER_OF_BEDS_FROM_DB);
                String apartmentClass = resultSet.getString(APARTMENT_CLASS_FROM_DB);
                int costRoom = resultSet.getInt(COST_ROOM_FROM_DB);
                String urlPhoto = resultSet.getString(URL_PHOTO_FROM_DB);


                preparedFindAvailableRoom.setInt(1, roomId);
                resultRoomsAvailable = preparedFindAvailableRoom.executeQuery();

                Date arrivalDateCriteria = (Date) criteriaMap.get(SearchCriteria.Room.ARRIVAL_DATE.toString());
                Date departureDateCriteria = (Date) criteriaMap.get(SearchCriteria.Room.ARRIVAL_DATE.toString());

                if (arrivalDateCriteria!=null && departureDateCriteria!=null){
                    if (!checkRoomForMeetingCriteria(resultRoomsAvailable, arrivalDateCriteria, departureDateCriteria)){
                        continue;
                    }
                }

                roomDB.put(SearchCriteria.Room.NUMBER_OF_BEDS.toString(), numberOfBeds);
                roomDB.put(SearchCriteria.Room.APARTMENT_CLASS.toString(), apartmentClass);
                roomDB.put(SearchCriteria.Room.COST.toString(), costRoom);

                if (checkRoomForMeetingCriteria(criteriaMap, roomDB)){
                    Room room = new Room();
                    room.setNumberOfBeds(numberOfBeds);
                    room.setApartmentClass(apartmentClass);
                    room.setCost(costRoom);
                    room.setUrlPhoto(urlPhoto);
                    room.setRoomId(roomId);

                    rooms.add(room);
                }

            }
            logger.info("Search for rooms ended");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }finally {
            if (resultSet != null) {
                try { resultSet.close(); }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing resultSet", e);
                }
            }
            if (resultRoomsAvailable != null) {
                try { resultRoomsAvailable.close(); }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing resultSet", e);
                }
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing preparedStatement", e);
                }
            }
            if (preparedFindAvailableRoom != null) {
                try { preparedFindAvailableRoom.close(); }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing preparedStatement", e);
                }
            }
        }
            return rooms;
    }

    @Override
    public List<Room> findRoomsByOrders(List<Order> orderRooms) throws DAOException {

        List<Room> rooms = new ArrayList<>();
        ResultSet resultSet = null;
        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROOM_BY_ORDER_ID)){


            logger.info("Find rooms has started");
            for (Order orderRoom : orderRooms){
                preparedStatement.setInt(1, orderRoom.getOrderId());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int numberOfBeds = Integer.parseInt(resultSet.getString(NUMBER_OF_BEDS_FROM_DB));
                    String apartmentClass = resultSet.getString(APARTMENT_CLASS_FROM_DB);
                    String photoUrl = resultSet.getString(URL_PHOTO_FROM_DB);
                    int costRoom = resultSet.getInt(COST_ROOM_FROM_DB);

                    Room room = new Room();
                    room.setNumberOfBeds(numberOfBeds);
                    room.setApartmentClass(apartmentClass);
                    room.setUrlPhoto(photoUrl);
                    room.setCost(costRoom);

                    rooms.add(room);
                }
            }
            logger.info("Search for rooms ended");
        }
        catch (SQLException | ConnectionPoolException e) {
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
        return rooms;
    }

    @Override
    public void addRoom(String numberOfBeds, String apartmentClass, String cost) throws DAOException {

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROOM)){

            preparedStatement.setInt(1, Integer.parseInt(numberOfBeds));
            preparedStatement.setString(2, apartmentClass);
            preparedStatement.setInt(3, Integer.parseInt(cost));
            preparedStatement.executeUpdate();

        }
        catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean changeRoom(String numberOfBeds, String apartmentClass, String cost,int roomId) throws DAOException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try(Connection connection = connectionPool.takeConnection()){

            preparedStatement = connection.prepareStatement(FIND_ORDERS_BY_ROOM_ID);
            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                logger.info("The room has been reserved");
                return false;
            }
            preparedStatement = connection.prepareStatement(UPDATE_ROOM);
            preparedStatement.setInt(1, Integer.parseInt(numberOfBeds));
            preparedStatement.setString(2, apartmentClass);
            preparedStatement.setInt(3, Integer.parseInt(cost));
            preparedStatement.setInt(4, roomId);
            preparedStatement.executeUpdate();

        }
        catch (SQLException | ConnectionPoolException e) {
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
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, "Fatal error when closing preparedStatement", e);
                }
            }
        }
        return true;
    }

    @Override
    public boolean blockRoom(int roomId) throws DAOException {


        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(BLOCK_ROOM)){

            preparedStatement.setInt(1,roomId);
            preparedStatement.executeUpdate();

        }
        catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public void unblockRoom(int roomId) throws DAOException {

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UNBLOCK_ROOM)){

            preparedStatement.setInt(1,roomId);
            preparedStatement.executeUpdate();

        }
        catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updateImageRoom(int roomId, String imagePath) throws DAOException {
        imagePath = PHOTO_PATH + imagePath;

        logger.info("Connecting with database");
        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_IMAGE_ROOM)){

            logger.info("Successfully connecting");

            preparedStatement.setString(1, imagePath);
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();

        }catch (SQLException | ConnectionPoolException e){
            throw new DAOException(e);
        }
    }

    /**
     * Check room for availability.
     *
     * @param resultRooms set of rooms.
     * @param arrivalDateCriteria arrival date in room.
     * @param departureDateCriteria departure date from room.
     * @return True if arrival date earlier than departure date, otherwise false.
     * @throws SQLException if the columnLabel is not valid;
     *              if a database access error occurs or this method is
     *              called on a closed result set
     */
    private boolean checkRoomForMeetingCriteria(ResultSet resultRooms, Date arrivalDateCriteria, Date departureDateCriteria)
            throws SQLException {

        boolean isAvailableRoom = true;
        while (resultRooms.next()){
            Date arrivalDateDB = resultRooms.getDate(ARRIVAL_DATE_FROM_DB);
            Date departureDateDB = resultRooms.getDate(DEPARTURE_DATE_FROM_DB);

            if (arrivalDateCriteria.compareTo(arrivalDateDB) >= 0
                    && arrivalDateCriteria.compareTo(departureDateDB) <= 0){
                isAvailableRoom = false;
            }
            if (departureDateCriteria.compareTo(arrivalDateDB) >= 0
                    && departureDateCriteria.compareTo(departureDateDB) <= 0) {
                isAvailableRoom = false;
            }
        }
        return isAvailableRoom;
    }

    /**
     * Check room for meeting criteria.
     *
     * @param criteriaMap criteria by which we compare.
     * @param roomDB room info.
     * @return True if the room meets all the criteria, otherwise false.
     */
    private boolean checkRoomForMeetingCriteria(Map<String, Object> criteriaMap, Map<String, Object> roomDB){

        for (Map.Entry<String, Object> criteria: criteriaMap.entrySet()) {
            for (Map.Entry<String, Object> roomCriteria: roomDB.entrySet()) {
                if (criteria.getKey().equals(roomCriteria.getKey())){
                    if (!criteria.getValue().equals(roomCriteria.getValue()) && !"".equals(criteria.getValue())){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
