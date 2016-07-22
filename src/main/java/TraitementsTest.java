import org.junit.Test;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import traitements.Traitement;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ali on 18/07/2016.
 */

public class TraitementsTest {

    private static final int TAILLEBANDE = 5;

    private MBFImage creationImgBandeCentrale() throws IOException {
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

    @Test
    public void testBandeCentrale() throws IOException {
        MBFImage source = creationImgBandeCentrale();

        MBFImage bandeCentrale = Traitement.getBandeCentrale(source, TAILLEBANDE);
        ImageUtilities.write(bandeCentrale,new File("test2.png"));

        boolean pixelRouge = true;

        Float[] pixelsImage;
        Float[] pixelsAttendus = new Float[3];

        pixelsAttendus[0] = Float.valueOf(255);
        pixelsAttendus[1] = Float.valueOf(0);
        pixelsAttendus[2] = Float.valueOf(0);


        for (int y = 0; y < bandeCentrale.getHeight();++y){
            for (int x = 0; x < bandeCentrale.getWidth();++x) {

                pixelsImage = bandeCentrale.getPixel(x, y);

                if (pixelsAttendus[0].floatValue() != pixelsImage[0].floatValue() || pixelsAttendus[1].floatValue() != pixelsImage[1].floatValue() || pixelsAttendus[2].floatValue() != pixelsImage[2].floatValue())
                    pixelRouge = false;
            }
        }
        assertEquals(pixelRouge,true);
    }


}
