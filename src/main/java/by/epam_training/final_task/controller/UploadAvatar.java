package by.epam_training.final_task.controller;

import by.epam_training.final_task.controller.command.impl.GoToActiveOrdersPage;
import by.epam_training.final_task.controller.exception.UploadImageException;
import by.epam_training.final_task.controller.user_session.UserSession;
import by.epam_training.final_task.entity.User;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
import by.epam_training.final_task.service.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class UploadAvatar extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UploadAvatar.class.getName());

    private static final String FILE_EXTENSION_REGEX = ".+\\.((png)|(jpg)|(jpeg))";
    private static final String UPLOAD_RESULT = "uploadResult";
    private static final String REDIRECT_TO_PROFILE_PAGE = "/Controller?command=gotoprofilepage";
    private static final String UPLOAD_DIR_FOR_USER_AVATAR = "D:\\EPAM\\FinalTask\\src\\main\\webapp\\images\\user_profile\\";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String FILE_PARAMETER = "file";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part part = request.getPart(FILE_PARAMETER);
        String path = part.getSubmittedFileName();
        if (path == null || path.trim().isEmpty()) {
            response.sendRedirect(REDIRECT_TO_PROFILE_PAGE + SEPARATE_PARAMETERS +
                    UPLOAD_RESULT + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + false);
        } else if (!path.matches(FILE_EXTENSION_REGEX)) {
            response.sendRedirect(REDIRECT_TO_PROFILE_PAGE + SEPARATE_PARAMETERS +
                    UPLOAD_RESULT + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + false);
        }

        String randFilename = UUID.randomUUID() + path.substring(path.lastIndexOf("."));
        try {
            initUserAvatar(request, part, randFilename);
            response.sendRedirect(REDIRECT_TO_PROFILE_PAGE + SEPARATE_PARAMETERS +
                        UPLOAD_RESULT + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + true);
        } catch (IOException | IllegalStateException | UploadImageException e) {
            logger.log(Level.ERROR, "Error occurred when upload image in directory or when updating user's avatar image");
            response.sendRedirect(REDIRECT_TO_PROFILE_PAGE + SEPARATE_PARAMETERS +
                    UPLOAD_RESULT + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + false);
        }

    }

    private void initUserAvatar(HttpServletRequest request, Part part, String randFilename) throws IOException, UploadImageException {

        File fileSaveDirForUserAvatar = new File(UPLOAD_DIR_FOR_USER_AVATAR);
        if (!fileSaveDirForUserAvatar.exists()) {
            fileSaveDirForUserAvatar.mkdirs();
        }

        part.write(UPLOAD_DIR_FOR_USER_AVATAR + randFilename);

        ServiceProvider provider = ServiceProvider.getInstance();
        UserService userService = provider.getUserService();

        try {
            User user = UserSession.getUserFromSession(request);
            userService.changeAvatar(user, randFilename);
        } catch (ServiceException e) {
            throw new UploadImageException("Failed upload photo");
        }
    }
}
