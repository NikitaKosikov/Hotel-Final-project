package by.epam_training.final_task.service.impl;

import by.epam_training.final_task.dao.DAOException;
import by.epam_training.final_task.dao.DAOProvider;
import by.epam_training.final_task.dao.RoomDAO;
import by.epam_training.final_task.entity.Order;
import by.epam_training.final_task.entity.Room;
import by.epam_training.final_task.entity.criteria.Criteria;
import by.epam_training.final_task.entity.criteria.SearchCriteria;
import by.epam_training.final_task.service.RoomService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.exception.*;
import by.epam_training.final_task.service.validation.OrderDateValidator;
import by.epam_training.final_task.service.validation.RoomValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomServiceImpl implements RoomService {

    private static final String FORMAT_ARRIVAL_DATE = "yyyy-MM-dd";
    private static final String FORMAT_DEPARTURE_DATE = "yyyy-MM-dd";
    private static final String LOCALE_INVALID_FORMAT_DATE = "Locale.invalid.format.date";
    private static final String LOCALE_INVALID_NUMBER_OF_BEDS = "Locale.error.number.of.beds";
    private static final String LOCALE_INVALID_COST_ROOM = "Locale.error.cost.room";
    private static final String LOCALE_INVALID_ENTERED_DATE = "Locale.error.entered.date";
    private static final String LOCALE_EMPTY_NUMBER_OF_BEDS = "Locale.empty.number.of.beds";
    private static final String LOCALE_EMPTY_COST = "Locale.empty.cost";

    @Override
    public List<Room> findRoomsByStatus(String status) throws ServiceException{
        DAOProvider provider = DAOProvider.getInstance();
        RoomDAO roomDAO = provider.getRoomDAO();

        List<Room> rooms;
        try {
            rooms = roomDAO.findRoomsByStatus(status);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
        return rooms;
    }

    @Override
    public List<Room> findRoomsByCriteria(Criteria criteriaRoom) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        RoomDAO roomDAO = provider.getRoomDAO();

        String numberOfBeds = (String) criteriaRoom.getCriteria().get(SearchCriteria.Room.NUMBER_OF_BEDS.toString());
        String cost = (String) criteriaRoom.getCriteria().get(SearchCriteria.Room.COST.toString());
        String arrivalDateStr = (String) criteriaRoom.getCriteria().get(SearchCriteria.Room.ARRIVAL_DATE.toString());
        String departureDateStr = (String) criteriaRoom.getCriteria().get(SearchCriteria.Room.DEPARTURE_DATE.toString());

        List<Exception> roomExceptionList = new ArrayList<>();

        Date arrivalDate = null;
        Date departureDate = null;
        try {
            if (!"".equals(arrivalDateStr)){
                arrivalDate = new SimpleDateFormat(FORMAT_ARRIVAL_DATE).parse(arrivalDateStr);
            }
            if (!"".equals(departureDateStr)) {
                departureDate = new SimpleDateFormat(FORMAT_DEPARTURE_DATE).parse(departureDateStr);
            }
        } catch (ParseException e) {
            roomExceptionList.add(new InvalidFormatDate(LOCALE_INVALID_FORMAT_DATE));
        }

        criteriaRoom.add(SearchCriteria.Room.ARRIVAL_DATE.toString(), arrivalDate);
        criteriaRoom.add(SearchCriteria.Room.DEPARTURE_DATE.toString(), departureDate);



        if(!"".equals(numberOfBeds)){
            if (!RoomValidator.isValidNumberOfBeds(numberOfBeds)){
                roomExceptionList.add(new InvalidNumberOfBeds(LOCALE_INVALID_NUMBER_OF_BEDS));
            }else {
                criteriaRoom.add(SearchCriteria.Room.NUMBER_OF_BEDS.toString(), Integer.parseInt(numberOfBeds));
            }
        }

        if(!"".equals(cost)){
            if (!RoomValidator.isValidCost(cost)){
                roomExceptionList.add(new InvalidCost(LOCALE_INVALID_COST_ROOM));
            } else {
                criteriaRoom.add(SearchCriteria.Room.COST.toString(), Integer.parseInt(cost));
            }
        }

        if (arrivalDate!=null && departureDate!=null) {
            if (!OrderDateValidator.isValidOrderDate(arrivalDate, departureDate)) {
                roomExceptionList.add(new InvalidFormatDate(LOCALE_INVALID_ENTERED_DATE));
            }
        }

        if (!roomExceptionList.isEmpty()){
            throw new RoomException("Room data format error", roomExceptionList);
        }

        List<Room> rooms;
        try {
            rooms = roomDAO.findRoomsByCriteria(criteriaRoom);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
        return rooms;
    }

    @Override
    public List<Room> findRoomsByOrders(List<Order> orderRooms) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        RoomDAO roomDAO = provider.getRoomDAO();

        List<Room> rooms;
        try {
            rooms = roomDAO.findRoomsByOrders(orderRooms);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
        return rooms;
    }

    @Override
    public void addRoom(String numberOfBeds, String apartmentClass, String cost) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        RoomDAO roomDAO = provider.getRoomDAO();

        List<Exception> roomExceptionList = new ArrayList<>();

        if ("".equals(numberOfBeds)){
            roomExceptionList.add(new EmptyNumberOfBedsException(LOCALE_EMPTY_NUMBER_OF_BEDS));
        }
        if ("".equals(cost)){
            roomExceptionList.add(new EmptyCostException(LOCALE_EMPTY_COST));
        }
        if (!RoomValidator.isValidNumberOfBeds(numberOfBeds)){
            roomExceptionList.add(new InvalidNumberOfBeds(LOCALE_INVALID_NUMBER_OF_BEDS));
        }

        if (!RoomValidator.isValidCost(cost)){
            roomExceptionList.add(new InvalidCost(LOCALE_INVALID_COST_ROOM));
        }

        if (!roomExceptionList.isEmpty()){
            throw new RoomException("Room data format error", roomExceptionList);
        }

        try {
            roomDAO.addRoom(numberOfBeds, apartmentClass, cost);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeRoom(String numberOfBeds, String apartmentClass, String cost, int roomId) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        RoomDAO roomDAO = provider.getRoomDAO();

        List<Exception> roomExceptionList = new ArrayList<>();

        if ("".equals(numberOfBeds)){
            roomExceptionList.add(new EmptyNumberOfBedsException(LOCALE_EMPTY_NUMBER_OF_BEDS));
        }
        if ("".equals(cost)){
            roomExceptionList.add(new EmptyCostException(LOCALE_EMPTY_COST));
        }
        if (!RoomValidator.isValidNumberOfBeds(numberOfBeds)){
            roomExceptionList.add(new InvalidNumberOfBeds(LOCALE_INVALID_NUMBER_OF_BEDS));
        }

        if (!RoomValidator.isValidCost(cost)){
            roomExceptionList.add(new InvalidCost(LOCALE_INVALID_COST_ROOM));
        }

        if (!roomExceptionList.isEmpty()){
            throw new RoomException("Room data format error", roomExceptionList);
        }

        try {
            return roomDAO.changeRoom(numberOfBeds, apartmentClass, cost, roomId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean blockRoom(int roomId) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        RoomDAO roomDAO = provider.getRoomDAO();

        try {
            return roomDAO.blockRoom(roomId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockRoom(int roomId) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        RoomDAO roomDAO = provider.getRoomDAO();

        try {
            roomDAO.unblockRoom(roomId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateImageRoom(int roomId, String imagePath) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        RoomDAO roomDAO = provider.getRoomDAO();

        try {
            roomDAO.updateImageRoom(roomId, imagePath);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }
}
