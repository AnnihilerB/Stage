import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.File;
import java.io.IOException;


public class Sbs {

    //Correspond à l'espacement entre les deux yeux.
    private static final int ESPACEMENT = 246; //6.5cm -> pixel

    public static void main(String args[]) throws IOException {

        //Récupération de l'image source et création de l'image de destination contenant les deux images gauche et droite.
        MBFImage source = ImageUtilities.readMBF(new File("src/main/resources/sbs.jpg"));
        MBFImage dest = new MBFImage((source.getWidth() - ESPACEMENT) *2,source.getHeight());

        //Création des deux images gauches et droite auquels on retire un bout corresspondant à  ce que voit l'oeil.
        MBFImage gauche = new MBFImage(source.getWidth() - ESPACEMENT,source.getHeight());
        MBFImage droite = new MBFImage(source.getWidth() - ESPACEMENT,source.getHeight());

        //Fenetre d'affichage des images.
        DisplayUtilities.createNamedWindow("droite");
        DisplayUtilities.createNamedWindow("gauche");

        //Correspond à l'endroit ou doit s'arreter le parcours de l'image source pour générer l'image gauche.
        int parcoursX = source.getWidth() - ESPACEMENT;

        //Indice de remplissage de l'image de l'oeil droit.
        int indexdroite = 0;

        //Parcours des y de l'image.
        for (int y = 0; y< source.getHeight(); ++y){
            //Remplissage de l'image gauche.
            for (int x = 0; x < parcoursX; ++x){
                Float[] pixels;
                pixels = source.getPixel(x,y);
                gauche.setPixel(x,y,pixels);
            }
            //Remplissage de l'aimge droite. On commence à partir de l'espcaement.
            for (int xD = ESPACEMENT; xD < source.getWidth(); ++xD){
                Float[] pixels;
                pixels = source.getPixel(xD,y);
                //Besoin d'un index pour remplir l'imge de destination depuis 0 et non depuis l'espacement.
                //Ananlyse si on est en fin de parcours d'un ligne de pixel.
                if (indexdroite == droite.getWidth()){
                    droite.setPixel(indexdroite,y,pixels);
                    indexdroite = 0;
                }
                //On est pas en fin de ligne donc remplissage.
                else{
                    droite.setPixel(indexdroite,y,pixels);
                }
                indexdroite++;

            }

        }
        //Remplissage de l'image finale à l'aide des images gauche et droite.
        dest.drawImage(gauche,0,0);
        dest.drawImage(droite,gauche.getWidth(),0);

        //Affichage.
        //DisplayUtilities.displayName(gauche,"gauche");
        //DisplayUtilities.displayName(droite,"droite");
        DisplayUtilities.display(dest);

    }
}
