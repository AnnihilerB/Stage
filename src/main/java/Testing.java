import org.openimaj.image.MBFImage;
import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.video.xuggle.XuggleVideo;
import org.openimaj.video.xuggle.XuggleVideoWriter;
import traitements.Traitement;

import java.io.File;
import java.io.IOException;

public class Testing {

    public static void main(String args[]) throws IOException {

        XuggleVideo sourceV = new XuggleVideo( new File("src/main/resources/resume2.mp4"));
        XuggleAudio sourceA = new XuggleAudio( new File("src/main/resources/resume2.mp4"));

        XuggleVideoWriter sortie = new XuggleVideoWriter("sortie.mp4",sourceV.getWidth(),
               sourceV.getHeight(),sourceV.getFPS());
        sortie.initialise();

        MBFImage frame;
        frame = sourceV.getCurrentFrame();

        while(sourceV.hasNextFrame()){

            sortie.addFrame(Traitement.anaglyphe(frame,sourceV.getWidth(),sourceV.getHeight()));
            System.out.println("Traitement");
            frame = sourceV.getNextFrame();

        }
        sortie.processingComplete();
        sortie.close();




    }
}
