package traitements;

import exceptions.DestinationManquante;

import java.io.IOException;

public class ThreadAnaglyphe extends Thread {

    // Méthode qui lance le thread.

    public void run(){
        try {
            Traitement.anaglyphe();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DestinationManquante destinationManquante) {
            destinationManquante.printStackTrace();
        }
    }

}