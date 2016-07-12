package exceptions;

import javax.swing.*;

public class VideoNonSupporte extends Exception {
    JPanel conteneur;

    public VideoNonSupporte(JPanel cont){
        conteneur = cont;
        afficherMessage();
    }

    private void afficherMessage(){
        JOptionPane.showMessageDialog(conteneur, "Traitement terminé !", "Résumé vidéo", JOptionPane.WARNING_MESSAGE);
    }
}
