package traitements;

import javax.swing.*;

/**
 * Created by ali on 03/07/16.
 */
public class ThreadEnCours extends Thread {

    JDialog dialog;
    JLabel label;
    JPanel conteneur;

    public ThreadEnCours(JPanel cont){
        dialog = new JDialog();
        label = new JLabel("Opération en cours...",JLabel.CENTER);
        conteneur = cont;
    }
    public void run(){
        dialog = new JDialog();
        dialog.setSize(200,100);
        dialog.setVisible(true);
        dialog.setTitle("Traitement");
        dialog.setLocationRelativeTo(conteneur);
        dialog.add(label);
    }

    public void detruire(){
        dialog.dispose();
    }
}
