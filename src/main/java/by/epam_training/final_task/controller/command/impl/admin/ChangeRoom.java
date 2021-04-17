package by.epam_training.final_task.controller.command.impl.admin;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.command.ErrorParameter;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.RoomService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import by.epam_training.final_task.service.exception.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeRoom implements Command {
    private static final Logger logger = Logger.getLogger(ChangeRoom.class.getName());

    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String NUMBER_OF_BEDS_PARAMETER = "number_of_beds";
    private static final String APARTMENT_CLASS_PARAMETER = "apartment_class";
    private static final String COST_PARAMETER = "cost";
    private static final String ROOM_ID_PARAMETER = "room_id";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String REDIRECT_TO_CHANGE_ROOM_PAGE = "/Controller?command=gotochangeroompage";
    private static final String REDIRECT_TO_INDEX_PAGE = "/Controller?command=gotoindexpage";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String ERROR_NUMBER_OF_BEDS = "error_number_of_beds";
    private static final String ERROR_COST_ROOM = "error_cost_room";
    private static final String SUCCESSFULLY_CHANGED_ROOM = "changed_room";
    private static final String SUCCESSFULLY_CHANGED_ROOM_MESSAGE = "Locale.successfully.changed.room";
    private static final String EMPTY_BEDS_ERROR = "empty_beds";
    private static final String EMPTY_COST_ERROR = "empty_cost";
    private static final String ERROR_CHANGE_ROOM = "change_room_error";
    private static final String ERROR_CHANGE_ROOM_MESSAGE = "Locale.error.changed.room";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserSession.getUserFromSession(request);
        if (user==null && user.getRoleId()!=2){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("Admin wants to change room");

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        RoomService roomService = serviceProvider.getRoomService();

        String numberOfBeds = request.getParameter(NUMBER_OF_BEDS_PARAMETER).trim();
        String apartmentClass = request.getParameter(APARTMENT_CLASS_PARAMETER).trim();
        String cost = request.getParameter(COST_PARAMETER).trim();
        int roomId = Integer.parseInt(request.getParameter(ROOM_ID_PARAMETER).trim());

        try {
            if (roomService.changeRoom(numberOfBeds, apartmentClass, cost,roomId)){
                logger.info("Room successfully changed");
                response.sendRedirect(REDIRECT_TO_INDEX_PAGE + SEPARATE_PARAMETERS + SUCCESSFULLY_CHANGED_ROOM +
                        EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SUCCESSFULLY_CHANGED_ROOM_MESSAGE);
                return;
            }
            logger.info("Room not changed");
            response.sendRedirect(REDIRECT_TO_INDEX_PAGE + SEPARATE_PARAMETERS + ERROR_CHANGE_ROOM +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + ERROR_CHANGE_ROOM_MESSAGE);
        }catch (RoomException e) {

            logger.log(Level.ERROR, "Entered data for changing room is invalid", e);
            String errorMessages = ErrorParameter.buildExceptionMessages(e);
            response.sendRedirect(REDIRECT_TO_CHANGE_ROOM_PAGE + SEPARATE_PARAMETERS + ROOM_ID_PARAMETER +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + roomId + SEPARATE_PARAMETERS + errorMessages);
        } catch (ServiceException e) {

            logger.log(Level.FATAL, "A server error occurred while changing the room", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER +
                    ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        }
    }
}
