package by.epam_training.final_task.service.impl;

import by.epam_training.final_task.dao.AdditionalServiceDAO;
import by.epam_training.final_task.dao.DAOException;
import by.epam_training.final_task.dao.DAOProvider;
import by.epam_training.final_task.entity.AdditionalService;
import by.epam_training.final_task.service.AdditionalServiceService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.exception.*;
import by.epam_training.final_task.service.validation.RoomValidator;
import by.epam_training.final_task.service.validation.ServiceValidator;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdditionalServiceImpl implements AdditionalServiceService {
    private static final String LOCALE_EMPTY_NAME = "Locale.empty.name";
    private static final String LOCALE_EMPTY_COST = "Locale.empty.cost";
    private static final String LOCALE_INVALID_NAME_SERVICE = "Locale.error.name.service";
    private static final String LOCALE_INVALID_COST_SERVICE = "Locale.error.cost.service";

    @Override
    public List<AdditionalService> findServicesByStatus(String status) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        AdditionalServiceDAO additionalServiceDAO = daoProvider.getAdditionalServiceDAO();
        List<AdditionalService> additionalServices;
        try {
            additionalServices = additionalServiceDAO.findServicesByStatus(status);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return additionalServices;
    }

    @Override
    public List<AdditionalService> findServicesByOrderId(int orderId) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        AdditionalServiceDAO additionalServiceDAO = daoProvider.getAdditionalServiceDAO();
        List<AdditionalService> additionalServices;
        try {
            additionalServices = additionalServiceDAO.findServicesByOrderId(orderId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return additionalServices;
    }

    @Override
    public boolean reservationService(int orderId, int serviceId, Date dateService) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        AdditionalServiceDAO additionalServiceDAO = daoProvider.getAdditionalServiceDAO();

        try {
            return additionalServiceDAO.reservationService(orderId, serviceId, dateService);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addService(String name, String cost) throws ServiceException{
        DAOProvider daoProvider = DAOProvider.getInstance();
        AdditionalServiceDAO additionalServiceDAO = daoProvider.getAdditionalServiceDAO();

        List<Exception> serviceExceptionList = new ArrayList<>();

        if ("".equals(name)){
            serviceExceptionList.add(new EmptyNameException(LOCALE_EMPTY_NAME));
        }
        if ("".equals(cost)){
            serviceExceptionList.add(new EmptyCostException(LOCALE_EMPTY_COST));
        }

        if (!ServiceValidator.isValidNameService(name)){
            serviceExceptionList.add(new InvalidNameException(LOCALE_INVALID_NAME_SERVICE));
        }

        if (!RoomValidator.isValidCost(cost)){
            serviceExceptionList.add(new InvalidCost(LOCALE_INVALID_COST_SERVICE));
        }

        if (!serviceExceptionList.isEmpty()){
            throw new AdditionalServiceException("Additional service data format error", serviceExceptionList);
        }

        try {
            return additionalServiceDAO.addService(name, cost);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeService(String name, String cost,int serviceId) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        AdditionalServiceDAO additionalServiceDAO = daoProvider.getAdditionalServiceDAO();

        List<Exception> serviceExceptionList = new ArrayList<>();

        if ("".equals(name)){
            serviceExceptionList.add(new EmptyNameException(LOCALE_EMPTY_NAME));
        }
        if ("".equals(cost)){
            serviceExceptionList.add(new EmptyCostException(LOCALE_EMPTY_COST));
        }

        if (!ServiceValidator.isValidNameService(name)){
            serviceExceptionList.add(new InvalidNameException(LOCALE_INVALID_NAME_SERVICE));
        }

        if (!RoomValidator.isValidCost(cost)){
            serviceExceptionList.add(new InvalidCost(LOCALE_INVALID_COST_SERVICE));
        }

        if (!serviceExceptionList.isEmpty()){
            throw new AdditionalServiceException("Additional service data format error", serviceExceptionList);
        }

        try {
            return additionalServiceDAO.changeService(name, cost, serviceId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean blockService(int serviceId) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        AdditionalServiceDAO additionalServiceDAO = daoProvider.getAdditionalServiceDAO();

        try {
            return additionalServiceDAO.blockService(serviceId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockService(int serviceId) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        AdditionalServiceDAO additionalServiceDAO = daoProvider.getAdditionalServiceDAO();

        try {
            additionalServiceDAO.unblockService(serviceId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateImageService(int serviceId, String imagePath) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        AdditionalServiceDAO additionalServiceDAO = daoProvider.getAdditionalServiceDAO();

        try {
            additionalServiceDAO.updateImageService(serviceId, imagePath);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
