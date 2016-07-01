package boutons;

import traitements.Traitement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by ali on 29/06/16.
 */
public class TraitementBarcode implements ActionListener {

    Traitement t;

    public TraitementBarcode(Traitement tra){
        t = tra;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        try {
            t.barcode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
