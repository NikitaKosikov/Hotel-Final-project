package by.epam_training.final_task.service.exception;

public class EmptyNameException extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public EmptyNameException() {
        super();
    }

    public EmptyNameException(String message) {
        super(message);
    }

    public EmptyNameException(Exception e) {
        super(e);
    }

    public EmptyNameException(String message, Exception e) {
        super(message, e);
    }
}
