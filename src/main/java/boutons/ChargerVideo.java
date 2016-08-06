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

    //TextArea contenant le chemin de la source
    private JTextArea text;
    //Conteneur principal pour le placement
    private JPanel conteneur;

    public ChargerVideo(JTextArea t, JPanel cont){
        text = t;
        conteneur = cont;
    }

    public void actionPerformed(ActionEvent e){
        JFileChooser choixFichier = new JFileChooser();
        choixFichier.showOpenDialog(conteneur);
        try{
            chargerVideo(choixFichier);
        }catch (VideoNonSupporte ex){
            ex.printStackTrace();
        }
    }

    private void chargerVideo(JFileChooser fichier) throws VideoNonSupporte{
        //Creation de deux videos a partir du meme fichier pour supprimer le lien affichage/traitement.
        XuggleVideo videoAAfficher = new XuggleVideo(fichier.getSelectedFile());
        XuggleVideo videoATraiter = new XuggleVideo(fichier.getSelectedFile());

        if (videoAAfficher.countFrames() <=0 ){
            throw new VideoNonSupporte(conteneur);
        }
        XuggleAudio audio = new XuggleAudio(fichier.getSelectedFile());
        text.setText(fichier.getSelectedFile().getAbsolutePath());
        VideoPlayer player = VideoPlayer.createVideoPlayer(videoAAfficher,audio);
        player.pause();
        player.showFrame();
        //Creation du traitement avec la video a traiter et le conteneur.
        Traitement.setVideo(videoATraiter);
        Traitement.setConteneur(conteneur);
    }

}


