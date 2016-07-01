package boutons;

import org.openimaj.video.VideoDisplay;
import org.openimaj.video.xuggle.XuggleVideo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 30/06/16.
 */
public class PlayButton implements ActionListener {

    XuggleVideo v = null;

    public PlayButton(XuggleVideo video){
        v = video;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        VideoDisplay.createVideoDisplay(v);

    }
}
