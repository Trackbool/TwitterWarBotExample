package utils;

import java.util.Random;

public class Utils {
    public static int randomNumber(int min, int max){
        return new Random().nextInt(max-min) + min;
    }

    public static boolean isValidInteger(String digits){
        boolean valid = false;
        try{
            Integer.parseInt(digits);
            valid = true;
        }catch (NumberFormatException ignored){}

        return valid;
    }
}
