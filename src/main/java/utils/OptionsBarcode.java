package utils;

import boutons.TraitementBarcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 04/07/16.
 */
public class OptionsBarcode implements ActionListener {

    static JDialog options;
    int hauteur = 1280;
    int tailleBande = 5;

    public void actionPerformed(ActionEvent actionEvent) {
        this.ouvrirFenetreOptions();
    }

    private void ouvrirFenetreOptions(){
        // Choix de la largeur de l'image, taille de la bande, calcul de la taille de l'image.
        options = new JDialog();

        JLabel labLargeur = new JLabel("Largeur de l'image : ");
        JLabel labTailleBande = new JLabel("Taille de la bande : ");

        JTextArea textLargeur = new JTextArea(1,4);
        JTextArea textTailleBande = new JTextArea(1,4);

        textLargeur.setAutoscrolls(false);

        JButton ok = new JButton("Valider");
        JButton validerHauteur = new JButton("Ok");
        JButton validerTaille = new JButton("Ok");

        options.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        options.setSize(300,200);
        options.add(labLargeur);
        options.add(textLargeur);
        options.add(validerHauteur);
        options.add(labTailleBande);
        options.add(textTailleBande);
        options.add(validerTaille);
        options.add(ok);
        ok.addActionListener(new TraitementBarcode());

        options.setLayout(new FlowLayout(FlowLayout.LEFT,15,25));
        options.setTitle("Paramètres de sortie");
        options.setVisible(true);

        System.out.print(textLargeur.getText());

    }

    public static void detruireOptions(){
        options.dispose();
    }
}
