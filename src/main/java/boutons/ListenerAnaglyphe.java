package boutons;

import traitements.ThreadAnaglyphe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ali on 04/07/16.
 */
public class ListenerAnaglyphe implements ActionListener{

    //Création d'un thread à l'appui d'un bouton pour permettre l'affichage d'une fenetre et effectuer le traitement.

    public void actionPerformed(ActionEvent actionEvent) {
        ThreadAnaglyphe thread = new ThreadAnaglyphe();
        thread.start();
    }
}
