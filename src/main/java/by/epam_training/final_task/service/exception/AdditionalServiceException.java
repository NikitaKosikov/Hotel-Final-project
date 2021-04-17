package by.epam_training.final_task.service.exception;

import by.epam_training.final_task.service.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class AdditionalServiceException extends ServiceException {
    private static final long serialVersionUID = -2795804103099775537L;

    public AdditionalServiceException() {
        super();
    }

    public AdditionalServiceException(String message) {
        super(message);
    }

    public AdditionalServiceException(Exception e) {
        super(e);
    }

    public AdditionalServiceException(String message, Exception e) {
        super(message, e);
    }

    public AdditionalServiceException(String message, List<Exception> e) {
        super(message, e);
    }

}
