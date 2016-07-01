package boutons;

import exceptions.VideoNonSupporte;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.xuggle.XuggleVideo;
import traitements.Traitement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChargerVideo implements ActionListener {

    public XuggleVideo source;
    public Traitement t;

    public void actionPerformed(ActionEvent e){
        JFileChooser choixFichier = new JFileChooser();
        choixFichier.showOpenDialog(null);
        try{
            source = chargerVideo(choixFichier);
        }catch (VideoNonSupporte ex){

        }
    }

    public XuggleVideo chargerVideo(JFileChooser fichier) throws VideoNonSupporte{
        XuggleVideo video = new XuggleVideo(fichier.getSelectedFile());
        t = new Traitement(video);
        return video;
    }

    public XuggleVideo getSource(){
        return source;
    }

    public Traitement getTraitement(){return t;}

    public void playSource(){
        VideoDisplay.createVideoDisplay(source);
    }


}


