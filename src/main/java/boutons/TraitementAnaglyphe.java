package boutons;

import traitements.ThreadAnaglyphe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 04/07/16.
 */
public class TraitementAnaglyphe implements ActionListener{

    public void actionPerformed(ActionEvent actionEvent) {
        ThreadAnaglyphe t = new ThreadAnaglyphe();
        t.start();
    }
}
