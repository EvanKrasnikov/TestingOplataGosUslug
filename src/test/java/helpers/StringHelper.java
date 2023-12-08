package helpers;

public class StringHelper {

    public static String split(String str, String regex, int index) {
        String[] splitted = str.split(regex);
        return splitted[index];
    }

}
