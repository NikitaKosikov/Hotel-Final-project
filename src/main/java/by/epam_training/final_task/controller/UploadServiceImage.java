package by.epam_training.final_task.controller;

import by.epam_training.final_task.controller.exception.UploadImageException;
import by.epam_training.final_task.service.AdditionalServiceService;
import by.epam_training.final_task.service.ServiceException;
import by.epam_training.final_task.service.ServiceProvider;
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
public class UploadServiceImage extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UploadServiceImage.class.getName());

    private static final String FILE_EXTENSION_REGEX = ".+\\.((png)|(jpg)|(jpeg))";
    private static final String UPLOAD_RESULT = "uploadResult";
    private static final String REDIRECT_TO_CHANGE_SERVICES_PAGE = "/Controller?command=gotochangeservicepage";
    private static final String UPLOAD_DIR_FOR_USER_AVATAR = "D:\\EPAM\\FinalTask\\src\\main\\webapp\\images\\services\\";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String FILE_PARAMETER = "file";
    private static final String SERVICE_ID_PARAMETER = "service_id";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int serviceId = Integer.parseInt(request.getParameter(SERVICE_ID_PARAMETER));
        Part part = request.getPart(FILE_PARAMETER);
        String path = part.getSubmittedFileName();
        if (path == null || path.trim().isEmpty()) {
            response.sendRedirect(REDIRECT_TO_CHANGE_SERVICES_PAGE + SEPARATE_PARAMETERS +
                    UPLOAD_RESULT + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + false + SEPARATE_PARAMETERS
                    + SERVICE_ID_PARAMETER + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + serviceId);
            return;
        } else if (!path.matches(FILE_EXTENSION_REGEX)) {
            response.sendRedirect(REDIRECT_TO_CHANGE_SERVICES_PAGE + SEPARATE_PARAMETERS +
                    UPLOAD_RESULT + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + false + SEPARATE_PARAMETERS+ SERVICE_ID_PARAMETER +
                    EQUALLY_BETWEEN_PARAMETER_AND_VALUE + serviceId);
            return;
        }

        String randFilename = UUID.randomUUID() + path.substring(path.lastIndexOf("."));
        try {
            initServiceImage(serviceId, part, randFilename);
            response.sendRedirect(REDIRECT_TO_CHANGE_SERVICES_PAGE + SEPARATE_PARAMETERS +
                        UPLOAD_RESULT + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + true + SEPARATE_PARAMETERS
                    + SERVICE_ID_PARAMETER + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + serviceId);
        } catch (IOException | IllegalStateException | UploadImageException e) {
            logger.log(Level.ERROR, "Error occurred when upload image in directory or when updating image of the service");
            response.sendRedirect(REDIRECT_TO_CHANGE_SERVICES_PAGE + SEPARATE_PARAMETERS +
                    UPLOAD_RESULT + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + false + SEPARATE_PARAMETERS
                    + SERVICE_ID_PARAMETER + EQUALLY_BETWEEN_PARAMETER_AND_VALUE + serviceId);
        }

    }

    private void initServiceImage(int serviceId, Part part, String randFilename) throws IOException, UploadImageException {

        File fileSaveDirForUserAvatar = new File(UPLOAD_DIR_FOR_USER_AVATAR);
        if (!fileSaveDirForUserAvatar.exists()) {
            fileSaveDirForUserAvatar.mkdirs();
        }

        part.write(UPLOAD_DIR_FOR_USER_AVATAR + randFilename);

        ServiceProvider provider = ServiceProvider.getInstance();
        AdditionalServiceService additionalServiceService = provider.getAdditionalService();

        try {
            additionalServiceService.updateImageService(serviceId, randFilename);
        } catch (ServiceException e) {
            throw new UploadImageException("Failed upload photo");
        }
    }
}
