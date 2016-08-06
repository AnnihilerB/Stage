package boutons;

import traitements.Traitement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 02/07/16.
 */
public class Sauvegarder implements ActionListener {

    //TextArea pour afficher la destination
    private JTextArea text;

    public Sauvegarder(JTextArea t){
        text = t;

    }

    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser fichierSortie = new JFileChooser();
        fichierSortie.setDialogTitle("Enregistrer sous...");
        fichierSortie.showSaveDialog(null);
        //Verification que le chois n'a pas ete annule
        if (fichierSortie.getSelectedFile().getAbsolutePath() != null) {
            text.setText(fichierSortie.getSelectedFile().getAbsolutePath());
            //Changement dans le traitement du fichier de sortie.
            Traitement.setFile(fichierSortie.getSelectedFile());
        }
    }
}
