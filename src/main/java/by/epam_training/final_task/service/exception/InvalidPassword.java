package by.epam_training.final_task.service.exception;

public class InvalidPassword extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public InvalidPassword() {
        super();
    }

    public InvalidPassword(String message) {
        super(message);
    }

    public InvalidPassword(Exception e) {
        super(e);
    }

    public InvalidPassword(String message, Exception e) {
        super(message, e);
    }
}
