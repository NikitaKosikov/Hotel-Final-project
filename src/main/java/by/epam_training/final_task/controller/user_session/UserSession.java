package by.epam_training.final_task.controller.user_session;

import by.epam_training.final_task.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class UserSession {
    private static final String USER = "user";

    public static void putUserInSession(HttpServletRequest request, User user){
        HttpSession session = request.getSession(true);
        session.setAttribute(USER, user);
    }

    public static User getUserFromSession(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        return (User) session.getAttribute(USER);
    }
}
