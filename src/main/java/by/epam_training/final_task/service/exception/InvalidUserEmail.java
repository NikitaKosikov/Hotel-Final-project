package by.epam_training.final_task.service.exception;

public class InvalidUserEmail extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public InvalidUserEmail() {
        super();
    }

    public InvalidUserEmail(String message) {
        super(message);
    }

    public InvalidUserEmail(Exception e) {
        super(e);
    }

    public InvalidUserEmail(String message, Exception e) {
        super(message, e);
    }
}
