package traitements;


import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.video.xuggle.XuggleVideo;

import java.io.File;
import java.io.IOException;

public class Traitement {

    private static final int DECALAGE = 5;
    private static final int TAILLEBANDE = 5;

    //Correspond à l'espacement entre les deux yeux.
    private static final int ESPACEMENT = 246; //6.5cm -> pixel

    public static void anaglypheDubois(){

    }

    public static void anaglyphe() throws IOException {

        MBFImage source = ImageUtilities.readMBF(new File("src/main/resources/anaglyphe.jpg"));

        int hauteur = source.getHeight();
        int largeur = source.getWidth();

        MBFImage dest = new MBFImage(largeur,hauteur);

        for (int y = DECALAGE; y <hauteur - DECALAGE; ++y){
            for (int x = DECALAGE; x < largeur - DECALAGE; ++x){

                Float[] pixels;
                Float[] pixelsDest;

                //Recupere la couleur rouge
                pixelsDest = source.getPixel(x - DECALAGE,y);

                pixels = source.getPixel(x + DECALAGE,y);

                pixelsDest[1] = pixels[1];
                pixelsDest[2] = pixels[2];

                dest.setPixel(x,y,pixelsDest);

            }
        }

        DisplayUtilities.display(dest);

    }

    public static void barcode() throws IOException {
        XuggleVideo video = new XuggleVideo("src/main/resources/resume2.mp4");

        File sortie;
        int cptX = 0;
        int cpt = 0;

        MBFImage frame;
        MBFImage frameResume;
        MBFImage imgSortie = new MBFImage((int)(TAILLEBANDE * video.countFrames()) / 10,video.getCurrentFrame().getHeight() );
        System.out.println(video.getCurrentFrame().getHeight());

        System.out.println(video.countFrames());

        frame = video.getCurrentFrame();

        while (video.hasNextFrame()){

            if (cpt %13 == 0){

                frameResume = new MBFImage(TAILLEBANDE,frame.getHeight());

                frameResume.drawImage(frame, 0,0);


                imgSortie.drawImage(frameResume,cptX,0);
                cptX += TAILLEBANDE;
                System.out.println(cpt);

            }

            frame = video.getNextFrame();
            cpt++;

        }
        sortie = new File("frame.png");
        ImageUtilities.write(imgSortie,"png", sortie);
        video.close();
    }

    public static void sideBySide() throws IOException{


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

