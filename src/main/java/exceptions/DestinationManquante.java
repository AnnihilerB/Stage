package exceptions;

import javax.swing.*;

/**
 * Created by ali on 12/07/16.
 */
public class DestinationManquante extends Exception {

    private JPanel conteneur;

    public DestinationManquante(JPanel cont){
        conteneur = cont;
        afficherMessage();
    }
    private void afficherMessage(){
        JOptionPane.showMessageDialog(conteneur, "Aucun fichier de sortie renseigné.", "Erreur", JOptionPane.WARNING_MESSAGE);
    }
}
