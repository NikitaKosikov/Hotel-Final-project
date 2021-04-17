package by.epam_training.final_task.service.exception;

public class EmptyRepeatPasswordException extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public EmptyRepeatPasswordException() {
        super();
    }

    public EmptyRepeatPasswordException(String message) {
        super(message);
    }

    public EmptyRepeatPasswordException(Exception e) {
        super(e);
    }

    public EmptyRepeatPasswordException(String message, Exception e) {
        super(message, e);
    }
}
