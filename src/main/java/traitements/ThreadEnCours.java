package traitements;

import javax.swing.*;

/**
 * Created by ali on 03/07/16.
 */
public class ThreadEnCours extends Thread {

    //Ce thread est lancé en parallèle aux autres traitements.

    JDialog dialog;
    JLabel label;
    JPanel conteneur;

    public ThreadEnCours(JPanel cont){
        dialog = new JDialog();
        label = new JLabel("Opération en cours...",JLabel.CENTER);
        conteneur = cont;
        creerFenetre();
    }
    public void run(){
        dialog.setVisible(true);
    }

    public void detruire(){
        dialog.dispose();
    }

    private void creerFenetre(){
        dialog = new JDialog();
        dialog.setSize(200,100);
        dialog.setTitle("Traitement");
        dialog.setLocationRelativeTo(conteneur);
        dialog.add(label);
    }
}
