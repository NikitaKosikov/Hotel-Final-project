package by.epam_training.final_task.service.exception;

public class EmptyNumberOfBedsException extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public EmptyNumberOfBedsException() {
        super();
    }

    public EmptyNumberOfBedsException(String message) {
        super(message);
    }

    public EmptyNumberOfBedsException(Exception e) {
        super(e);
    }

    public EmptyNumberOfBedsException(String message, Exception e) {
        super(message, e);
    }
}
