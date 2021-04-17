package by.epam_training.final_task.controller.command.impl.admin;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.RoomService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockRoom implements Command {
    private static final Logger logger = Logger.getLogger(ChangeRoom.class.getName());

    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String REDIRECT_TO_INDEX_PAGE = "/Controller?command=gotoindexpage";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String ROOM_ID_PARAMETER = "room_id";
    private static final String ERROR = "error";
    private static final String SHOW_ERROR_MESSAGE = "Locale.global.error";
    private static final String REDIRECT_TO_ERROR_PAGE = "error.jsp";
    private static final String SUCCESSFULLY_DELETED_ROOM = "deleted_room";
    private static final String SUCCESSFULLY_DELETED_ROOM_MESSAGE = "Locale.successfully.block.room";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String ERROR_DELETED_ROOM = "deleted_room_error";
    private static final String ERROR_DELETED_ROOM_MESSAGE = "Locale.error.delete.room";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserSession.getUserFromSession(request);
        if (user==null && user.getRoleId()!=2){
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        logger.info("Admin wants to block room");

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        RoomService roomService = serviceProvider.getRoomService();

        int roomId = Integer.parseInt(request.getParameter(ROOM_ID_PARAMETER));

        try {
            if (roomService.blockRoom(roomId)){
                logger.info("Room successfully blocked");
                response.sendRedirect(REDIRECT_TO_INDEX_PAGE + SEPARATE_PARAMETERS + SUCCESSFULLY_DELETED_ROOM +
                        EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SUCCESSFULLY_DELETED_ROOM_MESSAGE);
                return;
            }
            logger.info("Room not blocked");
            response.sendRedirect(REDIRECT_TO_INDEX_PAGE + SEPARATE_PARAMETERS + ERROR_DELETED_ROOM +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + ERROR_DELETED_ROOM_MESSAGE);
        } catch (ServiceException e) {
            logger.log(Level.FATAL, "A server error occurred while blocking a room", e);
            response.sendRedirect(REDIRECT_TO_ERROR_PAGE + QUESTION_START_PARAMETER +
                    ERROR + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + SHOW_ERROR_MESSAGE);
        }

    }
}
