package tests;

import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.File;
import java.io.IOException;

/**
 * Created by ali on 25/07/16.
 */
public class ImagesTests {

    private static final int TAILLEBANDE = 5;
    private static final int ESPACEMENT = 15;
    private static final int largeur = 500;
    private static final int hauteur = 500;

    public int getTaillebande(){
        return TAILLEBANDE;
    }

    public int getEspacement(){
        return ESPACEMENT;
    }

    public MBFImage creationImgBandeCentrale() throws IOException {
        MBFImage source = new MBFImage(500,500);

        int hauteur = source.getHeight();
        int milieu = source.getWidth() / 2;

        Float[] rouge = new Float[3];
        rouge[0] = Float.valueOf(255);
        rouge[1] = Float.valueOf(0);
        rouge[2] = Float.valueOf(0);

        for (int y = 0; y < hauteur; ++y) {

            for (int x = milieu - (TAILLEBANDE / 2); x <= milieu + (TAILLEBANDE / 2); ++x) {

                source.setPixel(x, y, rouge);
            }
        }
        ImageUtilities.write(source,new File("test.png"));
        return source;
    }

    public  MBFImage creationImgDroite() throws IOException {
        MBFImage imgDroite = new MBFImage(largeur,hauteur);

        Float[] rouge = new Float[3];
        rouge[0] = Float.valueOf(255);
        rouge[1] = Float.valueOf(0);
        rouge[2] = Float.valueOf(0);

        for (int y = 0; y < hauteur; y++ ){
            for (int x = 0; x < ESPACEMENT; ++x){
                imgDroite.setPixel(x,y,rouge);

            }
        }
        return imgDroite;
    }

    public  MBFImage creationImgGauche() throws IOException {
        MBFImage imgGauche = new MBFImage(largeur,hauteur);

        Float[] rouge = new Float[3];
        rouge[0] = Float.valueOf(255);
        rouge[1] = Float.valueOf(0);
        rouge[2] = Float.valueOf(0);

        for (int y = 0; y < hauteur; y++ ){
            for (int x = largeur - ESPACEMENT; x < largeur; ++x){
                imgGauche.setPixel(x,y,rouge);

            }
        }
        ImageUtilities.write(imgGauche, new File ("imggauche.png"));
        return imgGauche;
    }


}
