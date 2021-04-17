package by.epam_training.final_task.service.exception;

public class InvalidFormatDate extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public InvalidFormatDate() {
        super();
    }

    public InvalidFormatDate(String message) {
        super(message);
    }

    public InvalidFormatDate(Exception e) {
        super(e);
    }

    public InvalidFormatDate(String message, Exception e) {
        super(message, e);
    }
}
