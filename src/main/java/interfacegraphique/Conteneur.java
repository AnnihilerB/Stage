package interfacegraphique;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ali on 26/06/16.
 */
public class Conteneur extends JPanel {

    JFrame fenetre;

    public Conteneur(JFrame f){
        fenetre = f;
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(f.getPreferredSize());
        this.setBackground(Color.blue);
    }
}
