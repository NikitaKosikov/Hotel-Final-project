package by.epam_training.final_task.service.validation;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceValidator {
    public static final ResourceBundle bundle = ResourceBundle.getBundle("regex");

    public static boolean isValidNameService(String name){

        Pattern namePattern = Pattern.compile(bundle.getString("valid.name.service"));
        Matcher nameMatcher = namePattern.matcher(name);
        int lengthName = 0;
        if (nameMatcher.find()){
            int start = nameMatcher.start();
            int end = nameMatcher.end();
            lengthName = end-start;
        }
        return lengthName == name.length();
    }
}
