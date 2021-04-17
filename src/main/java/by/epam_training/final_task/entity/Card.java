package by.epam_training.final_task.entity;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    private String typeOfCard;
    private String cardNumber;
    private String CVV;
    private String validityCard;
    private String ownerCard;

    public Card() {
    }

    public Card(String typeOfCard, String cardNumber, String CVV, String validityCard, String ownerCard) {
        this.typeOfCard = typeOfCard;
        this.cardNumber = cardNumber;
        this.CVV = CVV;
        this.validityCard = validityCard;
        this.ownerCard = ownerCard;
    }

    public String getTypeOfCard() {
        return typeOfCard;
    }

    public void setTypeOfCard(String typeOfCard) {
        this.typeOfCard = typeOfCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getValidityCard() {
        return validityCard;
    }

    public void setValidityCard(String validityCard) {
        this.validityCard = validityCard;
    }

    public String getOwnerCard() {
        return ownerCard;
    }

    public void setOwnerCard(String ownerCard) {
        this.ownerCard = ownerCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card cardInfo = (Card) o;
        return Objects.equals(typeOfCard, cardInfo.typeOfCard) &&
                Objects.equals(cardNumber, cardInfo.cardNumber) &&
                Objects.equals(CVV, cardInfo.CVV) &&
                Objects.equals(validityCard, cardInfo.validityCard) &&
                Objects.equals(ownerCard, cardInfo.ownerCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOfCard, cardNumber, CVV, validityCard, ownerCard);
    }
}
