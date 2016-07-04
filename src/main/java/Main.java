import boutons.ChargerVideo;
import boutons.Sauver;
import boutons.TraitementBarcode;
import traitements.Traitement;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ali on 02/07/16.
 */
public class Main {

    public static void main (String args[]){
        Traitement t;

        JFrame fenetre;
        JPanel conteneur;
        JMenuBar menu;

        fenetre = new JFrame("Video 0.1");
        conteneur = new JPanel();
        menu = new JMenuBar();

        JButton source = new JButton("Parcourir...");
        JButton destination = new JButton("Destination...");

        JLabel lSource = new JLabel("Source : ");
        JLabel lDest = new JLabel("Destination :");
        
        JTextArea tSource = new JTextArea(1,35);
        JTextArea tDest = new JTextArea(1,35);

        ChargerVideo loadvideo = new ChargerVideo(tSource,conteneur);
        Sauver saveVideo = new Sauver(tDest);

        tSource.setEditable(false);
        tDest.setEditable(false);

        conteneur.setLayout(new FlowLayout(FlowLayout.LEFT,15,25));
        fenetre.setPreferredSize(new Dimension(700,150));
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        conteneur.setPreferredSize(fenetre.getPreferredSize());

        conteneur.add(lSource);
        conteneur.add(tSource);
        conteneur.add(source);

        conteneur.add(lDest);
        conteneur.add(tDest);
        conteneur.add(destination);


        fenetre.setContentPane(conteneur);
        fenetre.setJMenuBar(menu);

        JMenu traitements = new JMenu("Traitements...");
        menu.add(traitements);

        JMenuItem open = new JMenuItem("Ouvrir vidéo");

        JMenuItem barcode = new JMenuItem("Résumé vidéo");
        JMenuItem anaglyphe = new JMenuItem("Anaglyphe...");
        JMenuItem sbs = new JMenuItem("Side-by-side");

        traitements.add(barcode);
        traitements.add(anaglyphe);
        traitements.add(sbs);

        source.addActionListener(loadvideo);
        barcode.addActionListener(new TraitementBarcode());
        destination.addActionListener(saveVideo);


        fenetre.setResizable(false);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);


    }




}
