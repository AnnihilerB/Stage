import boutons.*;
import utils.FenetreOptionsBarcode;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by ali on 02/07/16.
 */
public class Main {

    public static void main (String args[]) throws IOException {

        //Declaration des principales composantes.
        JFrame fenetre;
        JPanel conteneur;
        JPanel panelSource;
        JPanel panelDest;
        JMenuBar menu;

        //Initialisation de la fenetre et du conteneur
        fenetre = new JFrame("Video");
        conteneur = new JPanel();
        panelSource = new JPanel();
        panelDest = new JPanel();

        //Initialisation du menu et sous-menus.
        menu = new JMenuBar();

        JMenu traitements = new JMenu("Traitements...");
        JMenuItem barcode = new JMenuItem("Resume video");
        JMenuItem anaglyphe = new JMenuItem("Anaglyphe");
        JMenuItem anaglypheDubois = new JMenuItem("Anaglyphe Dubois");
        JMenuItem sbs = new JMenuItem("Side-by-side");

        //Labels et boutons de l'interface.
        JButton boutonSource = new JButton("Parcourir...");
        JButton boutonDestination = new JButton("Destination...");

        JLabel labelSource = new JLabel("Source : ");
        JLabel labelDest = new JLabel("Destination :");
        
        JTextArea textSource = new JTextArea(1,35);
        JTextArea textDest = new JTextArea(1,35);

        //Parametres de la fenetre
        fenetre.setPreferredSize(new Dimension(650,175));
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Parametres du conteneur.
        conteneur.setPreferredSize(fenetre.getPreferredSize());
        conteneur.setLayout(new FlowLayout(FlowLayout.LEFT,15,15));

        //Parametres des JPanel
        panelSource.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelDest.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelSource.setPreferredSize(new Dimension(700,40));
        panelDest.setPreferredSize(new Dimension(700,40));

        //Ajout des composants dans le conteneur.
        panelSource.add(labelSource);
        panelSource.add(textSource);
        panelSource.add(boutonSource);
        panelDest.add(labelDest);
        panelDest.add(textDest);
        panelDest.add(boutonDestination);

        conteneur.add(panelSource);
        conteneur.add(panelDest);

        //Parametres des TextBox
        textSource.setEditable(false);
        textDest.setEditable(false);

        //Ajout des elements de la barre de menu.
        menu.add(traitements);
        traitements.add(barcode);
        traitements.add(anaglyphe);
        traitements.add(anaglypheDubois);
        traitements.add(sbs);

        //Ajout des Listener sur les boutons.
        boutonSource.addActionListener(new ChargerVideo(textSource,conteneur));
        barcode.addActionListener(new FenetreOptionsBarcode());
        anaglyphe.addActionListener(new ListenerAnaglyphe());
        anaglypheDubois.addActionListener(new ListenerAnaglypheDubois());
        sbs.addActionListener(new ListenerSideBySide());
        boutonDestination.addActionListener(new Sauvegarder(textDest));

        //Construction de la fenetre.
        fenetre.setContentPane(conteneur);
        fenetre.setJMenuBar(menu);
        fenetre.setResizable(false);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
    }
}
