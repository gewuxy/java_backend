package cn.medcn.common.utils;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * Created by lixuan on 2017/2/22.
 */
public class LetterUtils {

    public static String[] LETTER_ARRAY = {
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T", "U", "V", "W", "X", "Y", "Z"};

    public static String numberToLetter(int number){
        Assert.isTrue(number>0 && number<= 26);
        return LETTER_ARRAY[number-1];
    }


    public static String sortLetters(String letters){
        if(!StringUtils.isEmpty(letters)){
            char[] array = new char[letters.length()];
            for(int i = 0 ; i< letters.length(); i++){
                array[i] = letters.charAt(i);
            }
            Arrays.sort(array);
            StringBuilder builder = new StringBuilder();
            for(char c:array){
                builder.append(c);
            }
            return builder.toString();
        }
        return "";
    }


    public static void main(String[] args) {
        String a = "ACBDE";
        System.out.println(sortLetters(a));

        String scorePolicy = "5x10+4x8";
        System.out.println(scorePolicy.contains("+"));
        String[] arr = scorePolicy.split("\\+");
        System.out.println(arr[0]+" - "+arr[1]);
    }
}
