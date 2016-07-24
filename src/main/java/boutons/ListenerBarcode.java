package boutons;

import traitements.ThreadBarcode;
import utils.FenetreOptionsBarcode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 29/06/16.
 */
public class ListenerBarcode implements ActionListener {

    //Destruction de la fenetre des options et creation du thread

    public void actionPerformed(ActionEvent actionEvent) {
        FenetreOptionsBarcode.detruireOptions();
        ThreadBarcode thread = new ThreadBarcode();
        thread.start();
    }
}
