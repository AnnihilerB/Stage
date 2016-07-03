package traitements;

import org.openimaj.video.xuggle.XuggleVideo;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by ali on 03/07/16.
 */
public class ThreadProgressBar extends Thread implements ChangeListener{

    XuggleVideo video;
    JPanel conteneur;
    JProgressBar barre;



    public  ThreadProgressBar(XuggleVideo v, JPanel cont){
        video = v;
        conteneur = cont;

    }

    public void run(){
        int pourcent = 0;
        long nbTour = video.countFrames() /10;
        Component bar = conteneur.getComponent(6);
        barre = (JProgressBar)bar;
        barre.addChangeListener(this);
        for (int i = 0;i < nbTour;++i){
            if (i %10 == 0){
                barre.setValue(i);


            }
        }
    }

    public void stateChanged(ChangeEvent changeEvent) {
        barre.setString ("Progession: " + (int)(barre.getPercentComplete()*100) + "%");

    }
}
