package cn.medcn.transfer.utils;

/**
 * Created by lixuan on 2017/2/22.
 */
public class LetterUtils {

    public static String[] LETTER_ARRAY = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};

    public static String numberToLetter(int number){
        return LETTER_ARRAY[number-1];
    }


    public static String numberToLetter(String numberStr){
        return numberToLetter(Integer.parseInt(numberStr));
    }


    public static String numbersToLetters(String numberStr){
        if(numberStr == null){
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i< numberStr.length(); i++){
            builder.append(numberToLetter(String.valueOf(numberStr.charAt(i))));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String numberStr = "1356";
        System.out.println(numbersToLetters(numberStr));
        char a = 'a';
        System.out.println(String.valueOf(a));
    }

}
