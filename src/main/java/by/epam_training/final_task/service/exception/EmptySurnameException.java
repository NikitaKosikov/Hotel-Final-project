package by.epam_training.final_task.service.exception;

public class EmptySurnameException extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public EmptySurnameException() {
        super();
    }

    public EmptySurnameException(String message) {
        super(message);
    }

    public EmptySurnameException(Exception e) {
        super(e);
    }

    public EmptySurnameException(String message, Exception e) {
        super(message, e);
    }
}

