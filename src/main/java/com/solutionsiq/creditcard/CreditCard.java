package com.solutionsiq.creditcard;

public class CreditCard {
    private String cardNumber;
    private String cardIssuer;

    public CreditCard(String cardNumber) throws Exception {
        if (!cardNumber.matches("^[0-9]+$")) {
            throw new Exception("Invalid credit card digits");
        }
        Luhn luhn = new Luhn(cardNumber);
        if (!luhn.isValid()) {
            throw new Exception("Invalid Luhn validation");
        }
        this.cardNumber = cardNumber;
        cardIssuer = cardIssuer(cardNumber);
    }

    public String cardIssuer(String cardNumber) throws Exception {
        if (cardNumber.matches("^(34|37).*$") && (cardNumber.length() == 15)) {
            return "American Express";
        }
        if (cardNumber.matches("^(300|301|302|303|304|305).*$") && (cardNumber.length() == 14)) {
            return "Diners Club - Carte Blanche";
        }
        if (cardNumber.matches("^(36).*$") && (cardNumber.length() == 14)) {
            return "Diners Club - International";
        }
        if (cardNumber.matches("^(54).*$") && (cardNumber.length() == 16)) {
            return "Diners Club - North America";
        }
        if (cardNumber.matches("^(6011|6221|6222|6223|6224|6225|6226|6227|6228|6229|644|645|646|647|648|649|65).*$") && (cardNumber.length() == 16)) {
            return "Discover";
        }
        if (cardNumber.matches("^(637|638|639).*$") && (cardNumber.length() == 16)) {
            return "InstaPayment";
        }
        if (cardNumber.matches("^(352|352|354|355|356|357|358).*$") && (cardNumber.length() == 16)) {
            return "JCB";
        }
        if (cardNumber.matches("^(6304|6706|6771|6709).*") && (cardNumber.length() >= 16) && (cardNumber.length() <= 19)) {
            return "Laser";
        }
        if (cardNumber.matches("^(5018|5020|5038|5893|6304|6759|6761|6762|6763).*$") && (cardNumber.length() >= 16) && (cardNumber.length() <= 19)) {
            return "Maestro";
        }
        if (cardNumber.matches("^(51|52|53|54|55).*$") && (cardNumber.length() >= 16) && (cardNumber.length() <= 19)) {
            return "MasterCard";
        }
        if (cardNumber.matches("^(4026|417500|4508|4844|4913|4917).*$") && (cardNumber.length() == 16)) {
            return "Visa Electron";
        }
        if (cardNumber.matches("^(4).*$") && (cardNumber.length() >= 13) && (cardNumber.length() <= 16)) {
            return "Visa";
        }
        Exception e = new Exception("Credit card digits are not from a known bank");
        throw e;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public String getCardIssuer(){
        return cardIssuer;
    }

}
