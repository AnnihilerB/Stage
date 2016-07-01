package boutons;

import traitements.Traitement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by ali on 29/06/16.
 */
public class TraitementBarcode implements ActionListener {

    public void actionPerformed(ActionEvent actionEvent) {
        try {
            Traitement.barcode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
