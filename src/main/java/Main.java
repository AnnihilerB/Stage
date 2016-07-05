import boutons.ChargerVideo;
import boutons.Sauver;
import boutons.TraitementAnaglyphe;
import traitements.Traitement;
import utils.OptionsBarcode;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ali on 02/07/16.
 */
public class Main {

    public static void main (String args[]){
        // Instance de traitement pour pouvoir l'avoir partout
        Traitement t;

        //Déclaration des principales composantes.
        JFrame fenetre;
        JPanel conteneur;
        JMenuBar menu;

        //Initialisation de la fenetre et du conteneur
        fenetre = new JFrame("Video 0.1");
        conteneur = new JPanel();

        //Initialisation du menu et sous-menus.
        menu = new JMenuBar();

        JMenu traitements = new JMenu("Traitements...");

        JMenuItem barcode = new JMenuItem("Résumé vidéo");
        JMenuItem anaglyphe = new JMenuItem("Anaglyphe...");
        JMenuItem sbs = new JMenuItem("Side-by-side");

        //Labels et boutons de l'interface.
        JButton source = new JButton("Parcourir...");
        JButton destination = new JButton("Destination...");

        JLabel lSource = new JLabel("Source : ");
        JLabel lDest = new JLabel("Destination :");
        
        JTextArea tSource = new JTextArea(1,35);
        JTextArea tDest = new JTextArea(1,35);

        //Paramètres de la fenètre
        fenetre.setPreferredSize(new Dimension(700,150));
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Paramètres du conteneur.
        conteneur.setPreferredSize(fenetre.getPreferredSize());
        conteneur.setLayout(new FlowLayout(FlowLayout.LEFT,15,25));

        //Ajout des composants dans le conteneur.
        conteneur.add(lSource);
        conteneur.add(tSource);
        conteneur.add(source);
        conteneur.add(lDest);
        conteneur.add(tDest);
        conteneur.add(destination);

        //Paramètres des TextBox
        tSource.setEditable(false);
        tDest.setEditable(false);

        //Ajout des éléments de la barre de menu.
        menu.add(traitements);
        traitements.add(barcode);
        traitements.add(anaglyphe);
        traitements.add(sbs);

        //Chargement des instances.
        ChargerVideo loadvideo = new ChargerVideo(tSource,conteneur);
        Sauver saveVideo = new Sauver(tDest);

        //Ajout des Listener sur les boutons.
        source.addActionListener(loadvideo);
        barcode.addActionListener(new OptionsBarcode());
        anaglyphe.addActionListener(new TraitementAnaglyphe());
        destination.addActionListener(saveVideo);

        //Construction de la fenetre.
        fenetre.setContentPane(conteneur);
        fenetre.setJMenuBar(menu);
        fenetre.setResizable(false);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);


    }




}
