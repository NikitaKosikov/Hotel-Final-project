package by.epam_training.final_task.dao.impl.exception;

public class EmailOfUserAlreadyExistException extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public EmailOfUserAlreadyExistException() {
        super();
    }

    public EmailOfUserAlreadyExistException(String message) {
        super(message);
    }

    public EmailOfUserAlreadyExistException(Exception e) {
        super(e);
    }

    public EmailOfUserAlreadyExistException(String message, Exception e) {
        super(message, e);
    }
}
