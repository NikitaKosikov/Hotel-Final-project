package by.epam_training.final_task.service.exception;

public class InvalidNumberOfBeds extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public InvalidNumberOfBeds() {
        super();
    }

    public InvalidNumberOfBeds(String message) {
        super(message);
    }

    public InvalidNumberOfBeds(Exception e) {
        super(e);
    }

    public InvalidNumberOfBeds(String message, Exception e) {
        super(message, e);
    }
}
