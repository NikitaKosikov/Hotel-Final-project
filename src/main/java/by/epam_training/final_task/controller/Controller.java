package by.epam_training.final_task.controller;

import by.epam_training.final_task.controller.command.Command;
import by.epam_training.final_task.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Controller extends HttpServlet{
    private static final String COMMAND = "command";

    private final CommandProvider provider = new CommandProvider();

    public Controller() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    public void process(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter(COMMAND);
        Command command = provider.takeCommand(name);
        try {
            command.execute(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
