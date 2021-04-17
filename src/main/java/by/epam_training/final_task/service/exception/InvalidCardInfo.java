package by.epam_training.final_task.service.exception;

public class InvalidCardInfo extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public InvalidCardInfo() {
        super();
    }

    public InvalidCardInfo(String message) {
        super(message);
    }

    public InvalidCardInfo(Exception e) {
        super(e);
    }

    public InvalidCardInfo(String message, Exception e) {
        super(message, e);
    }
}
