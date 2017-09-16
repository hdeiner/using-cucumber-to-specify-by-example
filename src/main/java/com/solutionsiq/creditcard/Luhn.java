package com.solutionsiq.creditcard;

public class Luhn {

    private String digits;
    private int[] reversedCheckDigits;

    public Luhn(String digits){
        this.digits = digits;
        reversedCheckDigits = new int[digits.length()-1];
        for (int i=0; i<reversedCheckDigits.length; i++) {
            reversedCheckDigits[i] = Integer.parseInt(digits.substring(reversedCheckDigits.length-i-1,reversedCheckDigits.length-i));
        }
    }

    public int[] getReversedCheckDigits() {
        return reversedCheckDigits;
    }

    public boolean isValid() {
        int sum = 0;
        for (int i = 1; i <= reversedCheckDigits.length ; i++) {
            sum += processDigit(i);
        }
        int checkDigit = Integer.parseInt(digits.substring(digits.length()-1));
        return (((sum+checkDigit) % 10) == 0);
    }

    public int processDigit(int index) {
        int processedDigit = reversedCheckDigits[index-1];

        if ((index % 2) != 0) {
            processedDigit *= 2;
            while (processedDigit > 9) {
                processedDigit -= 9;
            }
        }

        return processedDigit;
    }
}
