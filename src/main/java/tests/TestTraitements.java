package tests;

import org.junit.Test;
import org.openimaj.image.MBFImage;
import traitements.Traitement;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ali on 18/07/2016.
 */

public class TestTraitements {

    ImagesTests imagesTests = new ImagesTests();



    @Test
    public void testBandeCentrale() throws IOException {
        MBFImage source = imagesTests.creationImgBandeCentrale();

        MBFImage bandeCentrale = Traitement.getBandeCentrale(source, imagesTests.getTaillebande());

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
