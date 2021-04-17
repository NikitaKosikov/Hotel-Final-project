package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToRegistrationPage implements Command {

    private static final String FORWARD_TO_REGISTER_PAGE = "/Registration";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_TO_REGISTER_PAGE);
        requestDispatcher.forward(request, response);
    }
}
