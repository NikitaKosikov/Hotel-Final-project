package by.epam_training.final_task.service.validation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    public static final ResourceBundle bundle = ResourceBundle.getBundle("regex");

    public static boolean isValidEmail(String email){
        Pattern emailPattern = Pattern.compile(bundle.getString("valid.email"));
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.find();
    }

    public static boolean isValidPhone(String phoneNumber){
        Pattern phonePattern = Pattern.compile(bundle.getString("valid.phone.number"));
        Matcher phoneMatcher = phonePattern.matcher(phoneNumber);
        return phoneMatcher.find();
    }

    public static boolean isValidCurrentPasswordAndNewPassword(String userPassword, String currentPassword){
        return !userPassword.equals(currentPassword);
    }

    public static boolean isValidUserPasswordAndCurrentPassword(String userPassword, String currentPassword){
        return userPassword.equals(currentPassword);
    }

    public static boolean isValidPasswordAndRepeatPassword(String password, String repeatPassword){
            return password.equals(repeatPassword);
    }


}