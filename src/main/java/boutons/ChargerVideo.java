package boutons;

import exceptions.VideoNonSupporte;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoPlayer;
import org.openimaj.video.xuggle.XuggleVideo;
import traitements.Traitement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChargerVideo implements ActionListener {

    JPanel videoPlayer;
    JPanel conteneur;

    public ChargerVideo(JPanel c){
        conteneur = c;
    }

    public void actionPerformed(ActionEvent e){
        JFileChooser choixFichier = new JFileChooser();
        choixFichier.showOpenDialog(null);
        try{
            chargerVideo(choixFichier);
        }catch (VideoNonSupporte ex){
        }
    }

    public void chargerVideo(JFileChooser fichier) throws VideoNonSupporte{
        XuggleVideo video = new XuggleVideo(fichier.getSelectedFile());
        VideoPlayer p = VideoPlayer.createVideoPlayer(video);
        p.pause();
        videoPlayer = p.getVideoPlayerPanel();
        afficherVideo();

        new Traitement(video);
    }

    public void afficherVideo(){
        videoPlayer.setPreferredSize(new Dimension(1024,768));
        conteneur.add(videoPlayer);


    }

}


