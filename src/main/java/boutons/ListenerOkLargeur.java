package boutons;

import utils.OptionsBarcode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 05/07/16.
 */
public class ListenerOkLargeur implements ActionListener {

    JTextArea textLargeur;

    public ListenerOkLargeur(JTextArea text){
        textLargeur = text;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        OptionsBarcode.setLargeur(Integer.parseInt(textLargeur.getText()));
    }
}

