package by.epam_training.final_task.controller.command;

import by.epam_training.final_task.service.exception.*;

import javax.naming.InvalidNameException;

/**
 * The class collects error messages for further transmission to the request parameter.
 */
public final class ErrorParameter {

    private static final String ERROR_NUMBER_OF_BEDS = "error_number_of_beds";
    private static final String ERROR_COST_ROOM = "error_cost_room";
    private static final String EMPTY_BEDS_ERROR = "empty_beds";
    private static final String EMPTY_COST_ERROR = "empty_cost";
    private static final String EQUALLY_BETWEEN_PARAMETER_AND_VALUE = "=";
    private static final String SEPARATE_PARAMETERS = "&";
    private static final String EMPTY_NAME_ERROR = "empty_name_error";
    private static final String COST_SERVICE_ERROR = "cost_service_error";
    private static final String NAME_SERVICE_ERROR = "invalid_name_service";
    private static final String USER_EMAIL_ERROR = "user_email_error";
    private static final String USER_PHONE_NUMBER_ERROR = "phone_number_error";
    private static final String EMPTY_SURNAME_ERROR = "empty_surname_error";
    private static final String ERROR_FORMAT_DATE = "error_format_date";
    private static final String EMPTY_REPEAT_PASSWORD_ERROR = "empty_repeat_password_error";
    private static final String EMPTY_PASSWORD_ERROR = "empty_password_error";
    private static final String PASSWORD_ERROR = "password_error";

    public static String buildExceptionMessages(UserException e){
        StringBuilder errorMessages = new StringBuilder();
        for (Exception exception : e.getErrors()) {
            if (exception.getClass() == InvalidUserEmail.class){
                errorMessages.append(USER_EMAIL_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == InvalidPhoneNumber.class){
                errorMessages.append(USER_PHONE_NUMBER_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == EmptyNameException.class){
                errorMessages.append(EMPTY_NAME_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == EmptySurnameException.class){
                errorMessages.append(EMPTY_SURNAME_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == InvalidPassword.class){
                errorMessages.append(PASSWORD_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == EmptyRepeatPasswordException.class){
                errorMessages.append(EMPTY_REPEAT_PASSWORD_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == EmptyPasswordException.class){
                errorMessages.append(EMPTY_PASSWORD_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }
        }
        errorMessages.setLength(errorMessages.length() - 1);
        return errorMessages.toString();
    }

    public static String buildExceptionMessages(RoomException e){
        StringBuilder errorMessages = new StringBuilder();
        for (Exception exception : e.getErrors()) {
            if (exception.getClass() == InvalidNumberOfBeds.class){
                errorMessages.append(ERROR_NUMBER_OF_BEDS).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == InvalidCost.class) {
                errorMessages.append(ERROR_COST_ROOM).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == EmptyNumberOfBedsException.class) {
                errorMessages.append(EMPTY_BEDS_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == EmptyCostException.class) {
                errorMessages.append(EMPTY_COST_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == InvalidFormatDate.class){
                errorMessages.append(ERROR_FORMAT_DATE).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }
        }
        errorMessages.setLength(errorMessages.length() - 1);
        return errorMessages.toString();
    }

    public static String buildExceptionMessages(AdditionalServiceException e){
        StringBuilder errorMessages = new StringBuilder();
        for (Exception exception : e.getErrors()) {
            if (exception.getClass() == EmptyNameException.class){
                errorMessages.append(EMPTY_NAME_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == EmptyCostException.class) {
                errorMessages.append(EMPTY_COST_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == InvalidCost.class) {
                errorMessages.append(COST_SERVICE_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }else if (exception.getClass() == InvalidNameException.class) {
                errorMessages.append(NAME_SERVICE_ERROR).append(EQUALLY_BETWEEN_PARAMETER_AND_VALUE).append(exception.getMessage()).append(SEPARATE_PARAMETERS);
            }
        }
        errorMessages.setLength(errorMessages.length() - 1);
        return errorMessages.toString();
    }
}
