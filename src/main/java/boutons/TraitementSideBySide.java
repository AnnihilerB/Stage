package boutons;

import traitements.ThreadSideBySide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 12/07/16.
 */
public class TraitementSideBySide implements ActionListener {

    //Création du thread
    public void actionPerformed(ActionEvent actionEvent) {
        ThreadSideBySide thread = new ThreadSideBySide();
        thread.start();
    }
}
