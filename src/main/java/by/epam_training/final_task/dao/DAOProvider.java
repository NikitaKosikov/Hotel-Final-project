package by.epam_training.final_task.dao;

import by.epam_training.final_task.dao.impl.*;

public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();

    private final UserDAO userDAO = new SQLUserDAO();
    private final OrderDAO orderDAO = new SQLOrderRoomDAO();
    private final RoomDAO roomDAO = new SQLRoomDAO();
    private final AdditionalServiceDAO additionalServiceDAO = new SQLAdditionalServiceDAO();

    private DAOProvider() {}

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public RoomDAO getRoomDAO() {
        return roomDAO;
    }

    public AdditionalServiceDAO getAdditionalServiceDAO() {
        return additionalServiceDAO;
    }

}
