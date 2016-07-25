package boutons;

import utils.OptionsAnaglyphe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ali on 25/07/2016.
 */
public class ListenerOkDecalage implements ActionListener {

    JTextArea textLargeur;

    public ListenerOkDecalage(JTextArea text){
        textLargeur = text;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        OptionsAnaglyphe.setDecalage(Integer.parseInt(textLargeur.getText()));
    }
}
