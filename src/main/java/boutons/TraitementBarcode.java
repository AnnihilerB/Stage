package boutons;

import traitements.ThreadBarcode;
import utils.OptionsBarcode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 29/06/16.
 */
public class TraitementBarcode implements ActionListener {

    public void actionPerformed(ActionEvent actionEvent) {
        OptionsBarcode.detruireOptions();
        ThreadBarcode t = new ThreadBarcode();
        t.start();
    }
}
