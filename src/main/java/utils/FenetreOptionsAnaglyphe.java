package utils;

import boutons.ListenerBarcode;
import boutons.ListenerOkDecalage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ali on 25/07/2016.
 */
public class FenetreOptionsAnaglyphe implements ActionListener {

    static JDialog options;

    public void actionPerformed(ActionEvent actionEvent) {
        this.ouvrirFenetreOptions();
    }

    /**
     * Méthode affichant une fenête des options permettant de configurer le barcode
     */

    private void ouvrirFenetreOptions(){
        // Choix de la textLargeur de l'image, taille de la bande, calcul de la taille de l'image.
        options = new JDialog();

        //Paramètrage des textes.
        JLabel labDecalage = new JLabel("Decalage (pixels) : ");

        //Zones de texte où rentrer les valeurs.
        JTextArea textDecalage = new JTextArea(1,4);

        textDecalage.setAutoscrolls(false);

        //Bouton pour valider les valeurs entrées.
        JButton lancerTraitement = new JButton("Valider");
        JButton validerDecalage = new JButton("Ok");


        //Paramètrage de la fenetre.
        options.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        options.setSize(300,200);
        options.setLayout(new FlowLayout(FlowLayout.LEFT,15,25));
        options.setTitle("Paramètres de sortie");

        //Ajout des éléments dans la fenetre.
        options.add(labDecalage);
        options.add(textDecalage);
        options.add(validerDecalage);
        options.add(lancerTraitement);

        //Ajout des Listener sur les boutons.
        lancerTraitement.addActionListener(new ListenerBarcode());
        validerDecalage.addActionListener(new ListenerOkDecalage(textDecalage));
        //Affichage de la fenetre.
        options.setVisible(true);
    }

    /**
     * Destruction de la fenetre des options.
     */
    public static void detruireOptions(){
        options.dispose();
    }
}

