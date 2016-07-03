package traitements;

import java.io.IOException;

/**
 * Created by ali on 03/07/16.
 */
public class ThreadBarcode extends Thread {
    public void run(){
        try {
            Traitement.barcode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
