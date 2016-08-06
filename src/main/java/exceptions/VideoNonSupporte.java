package exceptions;

import javax.swing.*;

public class VideoNonSupporte extends Exception {
    private JPanel conteneur;

    public VideoNonSupporte(JPanel cont){
        conteneur = cont;
        afficherMessage();
    }

    private void afficherMessage(){
        JOptionPane.showMessageDialog(conteneur, "Le fichier n'est pas supporté où n'est pas une vidéo.", "Erreur", JOptionPane.WARNING_MESSAGE);
    }
}
