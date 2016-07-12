package boutons;

import traitements.ThreadSideBySide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 12/07/16.
 */
public class TraitementSideBySide implements ActionListener {
    public void actionPerformed(ActionEvent actionEvent) {
        ThreadSideBySide t = new ThreadSideBySide();
        t.start();
    }
}
