import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.File;
import java.io.IOException;

public class Anaglyphe {

    public static final int DECALAGE = 5;

    public static void main(String args[]) throws IOException {

        MBFImage source = ImageUtilities.readMBF(new File("src/main/resources/anaglyphe.jpg"));

        int hauteur = source.getHeight();
        int largeur = source.getWidth();

        MBFImage dest = new MBFImage(largeur,hauteur);

        for (int y = DECALAGE; y <hauteur - DECALAGE; ++y){
            for (int x = DECALAGE; x < largeur - DECALAGE; ++x){

                Float[] pixels;
                Float[] pixelsDest;

                //Recupere la couleur rouge
                pixelsDest = source.getPixel(x - DECALAGE,y);

                pixels = source.getPixel(x + DECALAGE,y);

                pixelsDest[1] = pixels[1];
                pixelsDest[2] = pixels[2];

                dest.setPixel(x,y,pixelsDest);

            }
        }

        DisplayUtilities.display(dest);




    }
}
