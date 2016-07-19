package utils;

/**
 * Created by Ali on 18/07/2016.
 */
public class OptionsBarcode {

    int largeur;
    int tailleBande;

    public OptionsBarcode(){
        largeur = 1080;
        tailleBande = 5;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int l) {
        largeur = l;
    }

    public int getTailleBande() {
        return tailleBande;
    }

    public void setTailleBande(int tBande) {
        tailleBande = tBande;
    }
}
