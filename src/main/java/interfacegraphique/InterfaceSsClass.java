package interfacegraphique;

import boutons.ChargerVideo;
import boutons.TraitementBarcode;
import traitements.Traitement;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ali on 26/06/16.
 */
public class InterfaceSsClass {



    public static void main (String args[]){

        Traitement t;

        JFrame fenetre;
        JPanel conteneur;
        JPanel panFichiers;
        JPanel panTraitement;
        JPanel panOptions;
        JPanel panVideo;

        JRadioButton barcode = new JRadioButton("Resume Video",true);

        

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        BorderLayout bl = new BorderLayout();
       // BoxLayout boxl = new BoxLayout();
        CardLayout cl = new CardLayout();

        //Init des elements
        fenetre = new JFrame("Video 0.1");
        conteneur = new JPanel(gb);
        panFichiers = new JPanel(bl);
        panTraitement = new JPanel(cl);
        panOptions = new JPanel(bl);
        panVideo = new JPanel();

        //Init Fenetre
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setPreferredSize(new Dimension(800,600));

        //Init Conteneur
        conteneur.setPreferredSize(fenetre.getPreferredSize());
        conteneur.setBackground(Color.blue);

        //Init panFichiers
        panFichiers.setPreferredSize(new Dimension((int) (fenetre.getPreferredSize().getWidth()),
                (int)(fenetre.getPreferredSize().getHeight() / 2) + 100));

        //Init panTraitement
        panTraitement.setPreferredSize(new Dimension(534,200));
        panTraitement.setBackground(Color.orange);

        //Init panOptions
        panOptions.setPreferredSize(new Dimension(266,200));
        panOptions.setBackground(Color.pink);

        panVideo.setPreferredSize(new Dimension(800,200));

        //Ajout des éléments

        ChargerVideo loadvideo = new ChargerVideo(conteneur);
        
        JButton source = new JButton("Source");
        JButton traitement = new JButton("Traitement");
        
        panFichiers.add(source,BorderLayout.WEST);
        panFichiers.add(traitement,BorderLayout.EAST);


        source.addActionListener(loadvideo);
        traitement.addActionListener(new TraitementBarcode());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = gbc.weighty = 1;
        conteneur.add(panFichiers,gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.weightx = gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        conteneur.add(panOptions,gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        conteneur.add(panTraitement,gbc);

        //fenetre.setResizable(false);
        fenetre.setContentPane(conteneur);
        //Affichage
        fenetre.pack();
        fenetre.setVisible(true);









    }

}
