package interfacegraphique;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame{

    JPanel conteneur;

    public Fenetre(){
        this.setTitle("Video 0.1");
        this.setPreferredSize(new Dimension(800,600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.pack();
        this.setVisible(true);

        conteneur = null;
    }


    public void ajouterConteneur(JPanel conteneur){
        this.conteneur = conteneur;
        this.setContentPane(conteneur);
    }


}
