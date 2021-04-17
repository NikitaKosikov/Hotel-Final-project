package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Logout implements Command {
    private static final Logger logger = Logger.getLogger(Logout.class.getName());

    private static final String REDIRECT_TO_AUTHORIZATION_PAGE = "/SignIn";
    private static final String USER_ATTRIBUTE = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("User wants to logout");

        HttpSession session = request.getSession();
        session.removeAttribute(USER_ATTRIBUTE);
        logger.info("User session closed");

        response.sendRedirect(REDIRECT_TO_AUTHORIZATION_PAGE);
        logger.info("User logged out");
    }
}
