package by.epam_training.final_task.controller.command.impl;

import by.epam_training.final_task.controller.command.Command;
import org.apache.log4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLocale implements Command {
    private static final Logger logger = Logger.getLogger(ChangeLocale.class.getName());

    private static final String LOCALE_PAGE_PARAMETER = "locale";
    private static final String URL = "url";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("User wants to change language");
        HttpSession session=request.getSession(true);
        String url= (String) session.getAttribute(URL);
        String locale= request.getParameter(LOCALE_PAGE_PARAMETER);
        session.setAttribute(LOCALE_PAGE_PARAMETER, locale);
        response.sendRedirect(url);
        logger.info("Language was changed");

    }
}
