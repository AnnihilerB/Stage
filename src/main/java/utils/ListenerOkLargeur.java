package utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 05/07/16.
 */
public class ListenerOkLargeur implements ActionListener {

    OptionsBarcode optionsBarcode;
    JTextArea textLargeur;

    public ListenerOkLargeur(OptionsBarcode op,JTextArea text){
        optionsBarcode = op;
        textLargeur = text;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        optionsBarcode.setLargeur(Integer.parseInt(textLargeur.getText()));
        System.out.println(optionsBarcode.getLargeur());
    }
}

