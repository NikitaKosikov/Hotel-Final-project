package by.epam_training.final_task.service;

import by.epam_training.final_task.entity.AdditionalService;

import java.util.Date;
import java.util.List;

public interface AdditionalServiceService {
    List<AdditionalService> findServicesByStatus(String status) throws ServiceException;
    List<AdditionalService> findServicesByOrderId(int orderId) throws ServiceException;
    boolean reservationService(int orderId, int serviceId, Date dateService) throws ServiceException;
    boolean addService(String name, String cost) throws ServiceException;
    boolean changeService(String name, String cost, int serviceId) throws ServiceException;
    boolean blockService(int serviceId) throws ServiceException;
    void unblockService(int serviceId) throws ServiceException;
    void updateImageService(int serviceId, String imagePath) throws ServiceException;
}
