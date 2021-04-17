package by.epam_training.final_task.service.validation;

import java.util.Calendar;
import java.util.Date;

public class OrderDateValidator {

    public static boolean isValidOrderDate(Date arrivalDate, Date departureDate){

        Calendar calendarForArrival = Calendar.getInstance();
        Calendar calendarForDeparture = Calendar.getInstance();

        calendarForArrival.setTime(arrivalDate);
        calendarForDeparture.setTime(departureDate);

        int arrivalYears = calendarForArrival.get(Calendar.YEAR);
        int departureYear = calendarForDeparture.get(Calendar.YEAR);

        if (arrivalYears > departureYear) {
            return false;
        }else if (arrivalYears == departureYear){
            int dayOfYearForArrival = calendarForArrival.get(Calendar.DAY_OF_YEAR);
            int dayOfYearForDeparture = calendarForDeparture.get(Calendar.DAY_OF_YEAR);
            return dayOfYearForArrival <= dayOfYearForDeparture;
        }
        return true;
    }
}
