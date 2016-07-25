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

    public int getTaillebande(){
        return TAILLEBANDE;
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


}
