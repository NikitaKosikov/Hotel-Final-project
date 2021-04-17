package by.epam_training.final_task.service.exception;

public class InvalidPhoneNumber extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public InvalidPhoneNumber() {
        super();
    }

    public InvalidPhoneNumber(String message) {
        super(message);
    }

    public InvalidPhoneNumber(Exception e) {
        super(e);
    }

    public InvalidPhoneNumber(String message, Exception e) {
        super(message, e);
    }
}
