package traitements;

import java.io.IOException;

public class ThreadAnaglyphe extends Thread {
    public void run(){
        try {
            Traitement.anaglyphe();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}