package by.epam_training.final_task.service.validation;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomValidator {
    public static final ResourceBundle bundle = ResourceBundle.getBundle("regex");



    public static boolean isValidNumberOfBeds(String numberOfBeds) {
        Pattern emailPattern = Pattern.compile(bundle.getString("valid.number.seats"));
        Matcher emailMatcher = emailPattern.matcher(String.valueOf(numberOfBeds));
        int lengthNumberOfBeds = 0;
        if (emailMatcher.find()){
            int start = emailMatcher.start();
            int end = emailMatcher.end();
            lengthNumberOfBeds = end-start;
        }
        return lengthNumberOfBeds == numberOfBeds.length();
    }

    public static boolean isValidCost(String cost){
        Pattern costPattern = Pattern.compile(bundle.getString("valid.cost"));
        Matcher costMatcher = costPattern.matcher(String.valueOf(cost));
        int lengthCost = 0;
        if (costMatcher.find()){
            int start = costMatcher.start();
            int end = costMatcher.end();
            lengthCost = end-start;
        }
        return lengthCost == cost.length();
    }

}
