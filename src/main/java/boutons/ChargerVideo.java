package boutons;

import exceptions.VideoNonSupporte;
import org.openimaj.video.xuggle.XuggleVideo;
import traitements.Traitement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChargerVideo implements ActionListener {

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
        new Traitement(video);
    }
}


