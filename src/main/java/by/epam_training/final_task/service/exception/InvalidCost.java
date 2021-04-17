package by.epam_training.final_task.service.exception;

public class InvalidCost extends Exception{
    private static final long serialVersionUID = -2795804103099775537L;

    public InvalidCost() {
        super();
    }

    public InvalidCost(String message) {
        super(message);
    }

    public InvalidCost(Exception e) {
        super(e);
    }

    public InvalidCost(String message, Exception e) {
        super(message, e);
    }
}
