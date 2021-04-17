package by.epam_training.final_task.dao;

import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.Room;
import by.epam_training.final_task.entity.criteria.Criteria;

import java.util.List;

public interface RoomDAO {

    /**
     * Finds rooms with certain status.
     *
     * @param status the status of additional service: open or blocked.
     * @return List of found rooms.
     * @throws DAOException if an SQL error occurs.
     */
    List<Room> findRoomsByStatus(String status) throws DAOException;

    /**
     * Finds rooms using criteria.
     *
     * @param criteriaRoom criteria by which we search for rooms.
     * @return List of found rooms.
     * @throws DAOException if an SQL error occurs.
     */
    List<Room> findRoomsByCriteria(Criteria criteriaRoom) throws DAOException;

    /**
     * Finds rooms by orders.
     *
     * @param orderRooms list of rooms ordered.
     * @return List of found rooms.
     * @throws DAOException if an SQL error occurs.
     */
    List<Room> findRoomsByOrders(List<Order> orderRooms) throws DAOException;

    /**
     * Create new room
     *
     * @param numberOfBeds number of beds in the room.
     * @param apartmentClass class apartments rooms.
     * @param cost cost of room.
     * @throws DAOException if an SQL error occurs.
     */
    void addRoom(String numberOfBeds, String apartmentClass, String cost) throws DAOException;

    /**
     * Changing an existing room.
     *
     * @param numberOfBeds number of beds in the room.
     * @param apartmentClass class apartments rooms.
     * @param cost cost of room.
     * @param roomId
     * @return True if the room was successfully changed, false if this room was booked.
     * @throws DAOException if an SQL error occurs.
     */
    boolean changeRoom(String numberOfBeds, String apartmentClass, String cost, int roomId) throws DAOException;

    /**
     * Blocking an existing room.
     *
     * @param roomId
     * @return True if the room was successfully blocked otherwise false.
     * @throws DAOException if an SQL error occurs.
     */
    boolean blockRoom(int roomId) throws DAOException;

    /**
     * Unblocking an existing room.
     *
     * @param roomId
     * @throws DAOException if an SQL error occurs.
     */
    void unblockRoom(int roomId) throws DAOException;

    /**
     * Updates the image of the room.
     *
     * @param roomId
     * @param imagePath path to image.
     * @throws DAOException if an SQL error occurs.
     */
    void updateImageRoom(int roomId, String imagePath) throws DAOException;
}
