package by.epam_training.final_task.service.exception;

public class EmptyPasswordException extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public EmptyPasswordException() {
        super();
    }

    public EmptyPasswordException(String message) {
        super(message);
    }

    public EmptyPasswordException(Exception e) {
        super(e);
    }

    public EmptyPasswordException(String message, Exception e) {
        super(message, e);
    }
}
