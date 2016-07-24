package boutons;

import traitements.ThreadAnaglypheDubois;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 24/07/16.
 */
public class ListenerAnaglypheDubois implements ActionListener {

    public void actionPerformed(ActionEvent actionEvent) {
        ThreadAnaglypheDubois thread = new ThreadAnaglypheDubois();
        thread.start();

    }
}
