package traitements;

import exceptions.DestinationManquante;

import java.io.IOException;

/**
 * Created by ali on 12/07/16.
 */
public class ThreadSideBySide extends Thread {
    public void run(){
        try {
            Traitement.sideBySide();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DestinationManquante destinationManquante) {
        }
    }
}
