package boutons;

import traitements.Traitement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 02/07/16.
 */
public class Sauver implements ActionListener {

    JTextArea text;

    public Sauver(JTextArea t){
        text = t;

    }

    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser sauver = new JFileChooser();
        sauver.setDialogTitle("Enregistrer sous...");
        sauver.showSaveDialog(null);
        text.setText(sauver.getSelectedFile().getAbsolutePath());
        Traitement.setFile(sauver.getSelectedFile());


    }
}
