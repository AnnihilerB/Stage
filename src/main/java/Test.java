import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.saliency.YehSaliency;
import org.openimaj.video.xuggle.XuggleVideo;

import java.io.File;
import java.io.IOException;

public class Test {

    public static void main(String args[]) throws IOException {

        YehSaliency carte = new YehSaliency();

        XuggleVideo video = new XuggleVideo("src/main/resources/video.mp4");

        int nbFrame = 0;
        while (video.hasNextFrame()){

            File dest = new File("saliency" + nbFrame +".png");
            nbFrame +=1;

            MBFImage frame = (MBFImage)video.getCurrentFrame();
            FImage sal;

            carte.analyseImage(frame);
            System.out.print("ANALYSE FAITE");
            sal = carte.getSaliencyMap();
            System.out.print("CARTE FAITE");

            ImageUtilities.write(sal,dest);

            frame = video.getNextFrame();
        }

    }
}
