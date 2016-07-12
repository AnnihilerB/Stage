import boutons.ChargerVideo;
import boutons.Sauver;
import boutons.TraitementAnaglyphe;
import boutons.TraitementSideBySide;
import traitements.Traitement;
import utils.OptionsBarcode;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by ali on 02/07/16.
 */
public class Main {

    public static void main (String args[]) throws IOException {
        // Instance de traitement pour pouvoir l'avoir partout
        Traitement t;

        //Déclaration des principales composantes.
        JFrame fenetre;
        JPanel conteneur;
        JPanel panSource;
        JPanel panDest;
        JMenuBar menu;

        //Initialisation de la fenetre et du conteneur
        fenetre = new JFrame("Video 0.1");
        conteneur = new JPanel();
        panSource = new JPanel();
        panDest = new JPanel();

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
        fenetre.setPreferredSize(new Dimension(650,175));
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Paramètres du conteneur.
        conteneur.setPreferredSize(fenetre.getPreferredSize());
        conteneur.setLayout(new FlowLayout(FlowLayout.LEFT,15,15));

        //Paramètres des JPanel
        panSource.setLayout(new FlowLayout(FlowLayout.LEFT));
        panDest.setLayout(new FlowLayout(FlowLayout.LEFT));
        panSource.setPreferredSize(new Dimension(700,40));
        panDest.setPreferredSize(new Dimension(700,40));

        //Ajout des composants dans le conteneur.
        panSource.add(lSource);
        panSource.add(tSource);
        panSource.add(source);
        panDest.add(lDest);
        panDest.add(tDest);
        panDest.add(destination);

        conteneur.add(panSource);
        conteneur.add(panDest);

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
        sbs.addActionListener(new TraitementSideBySide());
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
