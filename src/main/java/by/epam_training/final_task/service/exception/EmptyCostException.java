package by.epam_training.final_task.service.exception;

public class EmptyCostException extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public EmptyCostException() {
        super();
    }

    public EmptyCostException(String message) {
        super(message);
    }

    public EmptyCostException(Exception e) {
        super(e);
    }

    public EmptyCostException(String message, Exception e) {
        super(message, e);
    }
}
