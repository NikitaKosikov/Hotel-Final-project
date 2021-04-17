package by.epam_training.final_task.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceException extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    private List<Exception> errors= new ArrayList<>();

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }

    public ServiceException(String message, List<Exception> e) {
        super(message);
        errors = e;
    }

    public List<Exception> getErrors() {
        return errors;
    }
}
