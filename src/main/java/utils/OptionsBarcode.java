package utils;

/**
 * Created by Ali on 18/07/2016.
 */
public class OptionsBarcode {

    static private int largeur;
    static private int tailleBande;

    public OptionsBarcode(){
        largeur = 1080;
        tailleBande = 5;
    }

    public static int getLargeur() {
        return largeur;
    }

    public  static void setLargeur(int l) {
        largeur = l;
    }

    public static int getTailleBande() {
        return tailleBande;
    }

    public static void setTailleBande(int tBande) {
        tailleBande = tBande;
    }
}
