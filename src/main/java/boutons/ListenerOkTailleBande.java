package boutons;

import utils.OptionsBarcode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 23/07/16.
 */
public class ListenerOkTailleBande implements ActionListener {

    JTextArea textTailleBande;

    public ListenerOkTailleBande(JTextArea text){
        textTailleBande = text;

    }

    public void actionPerformed(ActionEvent actionEvent) {
        OptionsBarcode.setTailleBande(Integer.parseInt(textTailleBande.getText()));
        System.out.println(OptionsBarcode.getTailleBande());

    }
}
