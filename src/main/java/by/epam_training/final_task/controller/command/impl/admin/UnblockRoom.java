package by.epam_training.final_task.controller.command.impl.admin;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.User;
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

public class UnblockRoom implements Command {
    private static final Logger logger = Logger.getLogger(UnblockRoom.class.getName());

    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String REDIRECT_TO_BLOCKED_ROOMS_PAGE = "/Controller?command=gotoblockedroomspage";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String ERROR = "error";
    private static final String FORWARD_TO_ERROR_PAGE = "error.jsp";
    private static final String ROOM_ID_PARAMETER = "room_id";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserSession.getUserFromSession(request);
        if (user==null && user.getRoleId()!=2){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("User wants to unblock room");

        int roomId = Integer.parseInt(request.getParameter(ROOM_ID_PARAMETER));
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        RoomService roomService = serviceProvider.getRoomService();
        try {
            roomService.unblockRoom(roomId);
            logger.info("Room unblocked");
            response.sendRedirect(REDIRECT_TO_BLOCKED_ROOMS_PAGE);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while unblocking room", e);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_ERROR_PAGE +
                    QUESTION_START_PARAMETER + ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
            requestDispatcher.forward(request, response);
        }
    }
}
