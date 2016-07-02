package interfacegraphique;

import boutons.ChargerVideo;
import traitements.Traitement;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ali on 02/07/16.
 */
public class NewInterface {

    public static void main (String args[]){
        Traitement t;

        JFrame fenetre;
        JPanel conteneur;
        JPanel panVideo;
        JMenuBar menu;

        fenetre = new JFrame("Video 0.1");
        conteneur = new JPanel();
        panVideo = new JPanel();
        menu = new JMenuBar();

        ChargerVideo loadvideo = new ChargerVideo(conteneur);

        fenetre.setPreferredSize(new Dimension(1024,768));
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        conteneur.setPreferredSize(fenetre.getPreferredSize());
        conteneur.setBackground(Color.blue);


        fenetre.setContentPane(conteneur);
        fenetre.setJMenuBar(menu);

        JMenu file = new JMenu("File");
        menu.add(file);

        JMenuItem open = new JMenuItem("Ouvrir vidéo");
        file.add(open);

        open.addActionListener(loadvideo);

        fenetre.setResizable(false);
        fenetre.pack();
        fenetre.setVisible(true);


    }




}
