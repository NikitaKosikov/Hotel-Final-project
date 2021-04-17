package by.epam_training.final_task.service.exception;

import by.epam_training.final_task.service.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class UserException extends ServiceException {
    private static final long serialVersionUID = -2795804103099775537L;

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Exception e) {
        super(e);
    }

    public UserException(String message, Exception e) {
        super(message, e);
    }

    public UserException(String message, List<Exception> e) {
        super(message, e);
    }

}
