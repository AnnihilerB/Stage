package utils;

/**
 * Created by Ali on 25/07/2016.
 */
public class OptionsAnaglyphe {

    private static int decalage;

    public OptionsAnaglyphe(){
        decalage = 5;
    }

    public static int getDecalage() {
        return decalage;
    }

    public static void setDecalage(int decalage) {
        OptionsAnaglyphe.decalage = decalage;
    }
}
