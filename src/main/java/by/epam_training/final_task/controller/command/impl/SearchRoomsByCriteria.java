package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.command.ErrorParameter;
import by.epam_training.final_task.entity.Room;
import by.epam_training.final_task.entity.criteria.Criteria;
import by.epam_training.final_task.entity.criteria.SearchCriteria;
import by.epam_training.final_task.service.RoomService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import by.epam_training.final_task.service.exception.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SearchRoomsByCriteria implements Command{
    private static final Logger logger = Logger.getLogger(SearchRoomsByCriteria.class.getName());

    private static final String ROOMS = "rooms";
    private static final String NUMBER_OF_BEDS_PARAMETER = "number_of_beds";
    private static final String APARTMENT_CLASS_PARAMETER = "apartment_class";
    private static final String ARRIVAL_DATE_PARAMETER = "arrival_date";
    private static final String DEPARTURE_DATE_PARAMETER = "departure_date";
    private static final String COST_ROOM_PARAMETER = "cost_room";
    private static final String REDIRECT_TO_INDEX_PAGE = "/Controller?command=gotoindexpage";
    private static final String FORWARD_TO_INDEX_PAGE = "index.jsp";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String ERROR_FORMAT_DATE = "error_format_date";
    private static final String ERROR_NUMBER_OF_BEDS = "error_number_of_beds";
    private static final String ERROR_COST_ROOM = "error_cost_room";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String EMPTY_LIST_OF_ROOMS = "Locale.empty.list.of.room";
    private static final String EMPTY_LIST_ERROR = "empty_list_of_room";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("User wants to search rooms by criteria");
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        RoomService roomService = serviceProvider.getRoomService();

        String numberOfBeds = request.getParameter(NUMBER_OF_BEDS_PARAMETER).trim();
        String apartmentClass = request.getParameter(APARTMENT_CLASS_PARAMETER).trim();
        String arrivalDateString = request.getParameter(ARRIVAL_DATE_PARAMETER).trim();
        String departureDateString = request.getParameter(DEPARTURE_DATE_PARAMETER).trim();
        String costRoom = request.getParameter(COST_ROOM_PARAMETER).trim();

        Criteria criteriaRoom = new Criteria(Room.class.getSimpleName());
        criteriaRoom.add(SearchCriteria.Room.NUMBER_OF_BEDS.toString(), numberOfBeds);
        criteriaRoom.add(SearchCriteria.Room.APARTMENT_CLASS.toString(), apartmentClass);
        criteriaRoom.add(SearchCriteria.Room.COST.toString(), costRoom);
        criteriaRoom.add(SearchCriteria.Room.ARRIVAL_DATE.toString(), arrivalDateString);
        criteriaRoom.add(SearchCriteria.Room.DEPARTURE_DATE.toString(), departureDateString);

        try {
            List<Room> rooms = roomService.findRoomsByCriteria(criteriaRoom);
            if (rooms.isEmpty()){
                request.setAttribute(EMPTY_LIST_ERROR, EMPTY_LIST_OF_ROOMS);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_INDEX_PAGE);
                requestDispatcher.forward(request, response);
                return;
            }
            request.setAttribute(ROOMS, rooms);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_INDEX_PAGE);
            requestDispatcher.forward(request, response);
        }catch (RoomException e) {
            logger.log(Level.ERROR, "Entered room components is invalid", e);
            String errorMessages = ErrorParameter.buildExceptionMessages(e);
            response.sendRedirect(REDIRECT_TO_INDEX_PAGE + SEPARATE_PARAMETERS + errorMessages);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while using the search system", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER + ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);

        }
    }
}
