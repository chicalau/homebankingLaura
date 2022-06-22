package com.mindhub.homebanking.Utils;

public final class CardUtils {


    private CardUtils(){};

    public static int getRandomNumber(int min,int max){
        return (int) ((Math.random() * (max - min)) + min);
    }


    public static String getCardNumber(){
        String cardNumber = (int) ((Math.random() * (9999 - 1000)) + 1000)
                        + "-" + (int) ((Math.random() * (9999 - 1000)) + 1000)
                        + "-" + (int) ((Math.random() * (9999 - 1000)) + 1000)
                        + "-" + (int) ((Math.random() * (9999 - 1000)) + 1000);
                            return cardNumber;
    }

    public static int getCvv(){
        int cvv = (int) ((Math.random() * (999 - 1)) + 1);
        return cvv;
    }




}
