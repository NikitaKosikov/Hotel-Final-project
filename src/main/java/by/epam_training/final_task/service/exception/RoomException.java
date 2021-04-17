package by.epam_training.final_task.service.exception;

import by.epam_training.final_task.service.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class RoomException extends ServiceException {
    private static final long serialVersionUID = -2795804103099775537L;

    public RoomException() {
        super();
    }

    public RoomException(String message) {
        super(message);
    }

    public RoomException(Exception e) {
        super(e);
    }

    public RoomException(String message, Exception e) {
        super(message, e);
    }
    public RoomException(String message, List<Exception> e) {
        super(message, e);
    }

}
