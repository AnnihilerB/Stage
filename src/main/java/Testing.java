import org.openimaj.video.VideoPlayer;
import org.openimaj.video.xuggle.XuggleVideo;

/**
 * Created by ali on 22/06/16.
 */
public class Testing {

    public static void main(String args[]) {

//      FenetrePrincipale f = new FenetrePrincipale();

        XuggleVideo video = new XuggleVideo("src/main/resources/resume2.mp4");
        VideoPlayer player = new VideoPlayer(video);
        player.play();
        System.out.print("FENETRE");
        player.showFrame();

    }
}
