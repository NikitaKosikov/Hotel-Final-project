package by.epam_training.final_task.service;

import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.Room;
import by.epam_training.final_task.entity.criteria.Criteria;
import by.epam_training.final_task.service.exception.RoomException;

import java.util.List;

public interface RoomService {

    List<Room> findRoomsByStatus(String status) throws ServiceException;
    List<Room> findRoomsByCriteria(Criteria criteriaRoom) throws ServiceException, RoomException;
    List<Room> findRoomsByOrders(List<Order> orderRooms) throws ServiceException;
    void addRoom(String numberOfBeds, String apartmentClass, String cost) throws ServiceException, RoomException;
    boolean changeRoom(String numberOfBeds, String apartmentClass, String cost, int roomId) throws ServiceException, RoomException;
    boolean blockRoom(int roomId) throws ServiceException;
    void unblockRoom(int roomId) throws ServiceException;
    void updateImageRoom(int roomId, String imagePath) throws ServiceException;
}
