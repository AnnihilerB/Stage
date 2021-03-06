package utils;

import boutons.ListenerOkLargeur;
import boutons.ListenerOkTailleBande;
import boutons.ListenerBarcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 04/07/16.
 */
public class FenetreOptionsBarcode implements ActionListener {

    private static JDialog options;

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
        JLabel labLargeur = new JLabel("Largeur de l'image : ");
        JLabel labTailleBande = new JLabel("Taille de la bande : ");

        //Zones de texte où rentrer les valeurs.
        JTextArea textLargeur = new JTextArea(1,4);
        JTextArea textTailleBande = new JTextArea(1,4);

        textLargeur.setAutoscrolls(false);

        //Bouton pour valider les valeurs entrées.
        JButton lancerTraitement = new JButton("Valider");
        JButton validerLargeur = new JButton("Ok");
        JButton validerTaille = new JButton("Ok");

        //Paramètrage de la fenetre.
        options.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        options.setSize(300,200);
        options.setLayout(new FlowLayout(FlowLayout.LEFT,15,25));
        options.setTitle("Paramètres de sortie");

        //Ajout des éléments dans la fenetre.
        options.add(labLargeur);
        options.add(textLargeur);
        options.add(validerLargeur);
        options.add(labTailleBande);
        options.add(textTailleBande);
        options.add(validerTaille);
        options.add(lancerTraitement);

        //Ajout des Listener sur les boutons.
        lancerTraitement.addActionListener(new ListenerBarcode());
        validerLargeur.addActionListener(new ListenerOkLargeur(textLargeur));
        validerTaille.addActionListener(new ListenerOkTailleBande(textTailleBande));
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
