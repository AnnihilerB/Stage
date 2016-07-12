package boutons;

import exceptions.VideoNonSupporte;
import org.openimaj.video.VideoPlayer;
import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.video.xuggle.XuggleVideo;
import traitements.Traitement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChargerVideo implements ActionListener {

    JTextArea text;
    JPanel conteneur;

    public ChargerVideo(JTextArea t, JPanel cont){
        text = t;
        conteneur = cont;
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
        //Création de deux vidéos à partir du même fichier pour supprimer le lien affichage traitement.
        XuggleVideo video = new XuggleVideo(fichier.getSelectedFile());
        if (video.countFrames() <=0 ){
            throw new VideoNonSupporte(conteneur);
        }
        XuggleAudio audio = new XuggleAudio(fichier.getSelectedFile());
        XuggleVideo videoTraiter = new XuggleVideo(fichier.getSelectedFile());
        text.setText(fichier.getSelectedFile().getAbsolutePath());
        VideoPlayer p = VideoPlayer.createVideoPlayer(video,audio);
        p.pause();
        p.showFrame();
        new Traitement(videoTraiter,conteneur);
    }

}


