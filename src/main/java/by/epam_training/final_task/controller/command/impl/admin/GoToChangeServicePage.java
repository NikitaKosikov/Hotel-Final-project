package by.epam_training.final_task.controller.command.impl.admin;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToChangeServicePage implements Command {
    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String QUESTION_START_PARAMETER = "?";
    private static final String AUTHORIZATION_ERROR = "authorization_error";
    private static final String AUTHORIZATION_ERROR_MESSAGE = "Locale.error.user.not.registered";
    private static final String FORWARD_TO_CHANGE_SERVICE_PAGE = "/WEB-INF/jsp/change_service.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserSession.getUserFromSession(request);
        if (user == null && user.getRoleId()!=2) {
            response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE + QUESTION_START_PARAMETER + AUTHORIZATION_ERROR +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + AUTHORIZATION_ERROR_MESSAGE);
            return;
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_CHANGE_SERVICE_PAGE);
        requestDispatcher.forward(request, response);
    }
}
