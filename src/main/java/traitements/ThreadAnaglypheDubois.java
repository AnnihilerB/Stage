package traitements;

import exceptions.DestinationManquante;

import java.io.IOException;

/**
 * Created by ali on 24/07/16.
 */
public class ThreadAnaglypheDubois extends  Thread {

    public void run(){
        try {
            Traitement.anaglypheDubois();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DestinationManquante destinationManquante) {
            destinationManquante.printStackTrace();
        }
    }
}
