package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.entity.Room;
import by.epam_training.final_task.service.RoomService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToIndexPage implements Command {
    private static final Logger logger = Logger.getLogger(GoToIndexPage.class.getName());

    private static final String ROOMS = "rooms";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String ERROR = "error";
    private static final String FORWARD_TO_ERROR_PAGE = "error.jsp";
    private static final String FORWARD_TO_INDEX_PAGE = "index.jsp";
    private static final String ROOM_STATUS_IS_OPEN = "открыт";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        RoomService roomService = serviceProvider.getRoomService();

        try {
            List<Room> rooms = roomService.findRoomsByStatus(ROOM_STATUS_IS_OPEN);
            request.setAttribute(ROOMS, rooms);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_INDEX_PAGE);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while searching the rooms", e);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_ERROR_PAGE +
                    QUESTION_START_PARAMETER + ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
            requestDispatcher.forward(request, response);
        }
        
    }
}
