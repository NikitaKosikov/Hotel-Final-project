package by.epam_training.final_task.controller.exception;

public class UploadImageException extends Exception{
    public UploadImageException() {
    }

    public UploadImageException(String message) {
        super(message);
    }

    public UploadImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadImageException(Throwable cause) {
        super(cause);
    }
}
