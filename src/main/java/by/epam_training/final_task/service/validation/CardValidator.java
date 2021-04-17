package by.epam_training.final_task.service.validation;

import by.epam_training.final_task.entity.Card;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidator {

    public static final ResourceBundle bundle = ResourceBundle.getBundle("regex");

    public static boolean isValidCard(Card cardInfo) {
        String cardNumber = cardInfo.getCardNumber();
        String CVV = cardInfo.getCVV();
        String validityCard = cardInfo.getValidityCard();
        String ownerCard = cardInfo.getOwnerCard();

        return isValidCardNumber(cardNumber) && isValidCVV(CVV)
                && isValidValidityCard(validityCard) && isValidOwnerCard(ownerCard);
    }

    public static boolean isValidCardNumber(String cardNumber){
        if ("".equals(cardNumber)){
            return false;
        }
        Pattern cardNumberPattern = Pattern.compile(bundle.getString("valid.card.number"));
        Matcher cardNumberMatcher = cardNumberPattern.matcher(cardNumber);

        int lengthCardNumberCode = 0;
        if (cardNumberMatcher.find()){
            int start = cardNumberMatcher.start();
            int end = cardNumberMatcher.end();
            lengthCardNumberCode = end-start;
        }
        return lengthCardNumberCode == cardNumber.length();
    }

    public static boolean isValidCVV(String CVV){
        if ("".equals(CVV)){
            return false;
        }
        Pattern verificationPattern = Pattern.compile(bundle.getString("valid.verification.card.number"));
        Matcher verificationMatcher = verificationPattern.matcher(CVV);

        int lengthCVVCode = 0;
        if (verificationMatcher.find()){
           int start = verificationMatcher.start();
           int end = verificationMatcher.end();
           lengthCVVCode = end-start;
        }
        return lengthCVVCode == CVV.length();
    }

    public static boolean isValidValidityCard(String validityCard){
        if ("".equals(validityCard)){
            return false;
        }

        Pattern validityPattern = Pattern.compile(bundle.getString("valid.validity.card"));
        Matcher validityMatcher = validityPattern.matcher(validityCard);

        int lengthValidityCardCode = 0;
        if (validityMatcher.find()){
            int start = validityMatcher.start();
            int end = validityMatcher.end();
            lengthValidityCardCode = end-start;
        }
        return lengthValidityCardCode == validityCard.length();
    }

    public static boolean isValidOwnerCard(String ownerCard){
        if ("".equals(ownerCard)){
            return false;
        }
        Pattern ownerCardPattern = Pattern.compile(bundle.getString("valid.owner.card"));
        Matcher ownerCardMatcher = ownerCardPattern.matcher(ownerCard);
        return ownerCardMatcher.find();
    }
}
