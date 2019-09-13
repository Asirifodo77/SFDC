package utilities;

import org.testng.annotations.Test;

public class convertCardNumberToSFStyle {

    public String convertCardNoToSFstyle(String cardNumber) {
        String cardNumberNew="";
        if(cardNumber.toCharArray().length==12) {
            String set1 = cardNumber.substring(0,4).trim();
            String set2 = cardNumber.substring(4,8).trim();
            String set3 = cardNumber.substring(8,12).trim();
            cardNumberNew=set1+" "+set2+" "+set3;

        } else if (cardNumber.toCharArray().length==14) {
            cardNumberNew=cardNumber;
        }

        return cardNumberNew;
    }

    @Test
    public void test() {
        String id="a0l5D000000IbAQQA0";
        System.out.println(id.substring(0,id.toCharArray().length-3).trim());
    }
}
