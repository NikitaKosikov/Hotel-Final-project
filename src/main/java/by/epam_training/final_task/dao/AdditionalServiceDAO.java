package by.epam_training.final_task.dao;

import by.epam_training.final_task.entity.AdditionalService;

import java.util.Date;
import java.util.List;

public interface AdditionalServiceDAO {

    /**
     * Finds additional services with certain status.
     *
     * @param status the status of additional service: open or blocked.
     * @return List of found additional services.
     * @throws DAOException if an SQL error occurs.
     */
    List<AdditionalService> findServicesByStatus(String status) throws DAOException;

    /**
     * Finds additional services for a specific order.
     *
     * @param orderId
     * @return List of found additional services.
     * @throws DAOException if an SQL error occurs.
     */
    List<AdditionalService> findServicesByOrderId(int orderId) throws DAOException;

    /**
     * Reservation additional service.
     *
     * @param orderId
     * @param serviceId
     * @param dateService date of use of the service.
     * @return True if the service was successfully booked otherwise throws an exception.
     * @throws DAOException if an SQL error occurs or date of service is invalid.
     */
    boolean reservationService(int orderId, int serviceId, Date dateService) throws DAOException;

    /**
     * Create new service
     *
     * @param name name of additional service.
     * @param cost cost of additional service.
     * @return True if the service was successfully added, false if this service already exists.
     * @throws DAOException if an SQL error occurs.
     */
    boolean addService(String name, String cost) throws DAOException;

    /**
     * Changing an existing additional service.
     *
     * @param name name of additional service.
     * @param cost cost of additional service.
     * @param serviceId
     * @return True if the service was successfully changed, false if this service was booked.
     * @throws DAOException if an SQL error occurs.
     */
    boolean changeService(String name, String cost, int serviceId) throws DAOException;

    /**
     * Blocking an existing additional service
     *
     * @param serviceId
     * @return True if the service was successfully blocked otherwise throws an exception.
     * @throws DAOException if an SQL error occurs.
     */
    boolean blockService(int serviceId) throws DAOException;

    /**
     * Unblocking an existing additional service
     *
     * @param serviceId
     * @return True if the service was successfully unblocked otherwise  otherwise throws an exception.
     * @throws DAOException if an SQL error occurs.
     */
    void unblockService(int serviceId) throws DAOException;

    /**
     * Updates the image of the additional service.
     *
     * @param serviceId
     * @param imagePath path to image.
     * @throws DAOException if an SQL error occurs.
     */
    void updateImageService(int serviceId, String imagePath) throws DAOException;
}
