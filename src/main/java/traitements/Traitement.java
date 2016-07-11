package traitements;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.video.xuggle.XuggleVideo;
import org.openimaj.video.xuggle.XuggleVideoWriter;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Traitement {

    private static final int DECALAGE = 1;
    private static final int TAILLEBANDE = 5;
    //Correspond à l'espacement entre les deux yeux.
    private static final int ESPACEMENT = 246; //6.5cm -> pixel

    //Donnée membre en static pour qu'elle soit la même pour toutes les méthodes. (Non spécifique à une instance).
    protected static XuggleVideo video;
    protected static File fichierSortie;
    protected static JPanel conteneur;

    public Traitement(XuggleVideo v, JPanel cont) {
        video = v;
        conteneur = cont;
    }

    public static void anaglypheDubois() throws IOException {

        MBFImage droite = ImageUtilities.readMBF(new File("src/main/resources/right.jpg"));
        MBFImage gauche = ImageUtilities.readMBF(new File("src/main/resources/left.jpg"));

        int largeur = gauche.getWidth();
        int hauteur = gauche.getHeight();

        MBFImage inter = new MBFImage(largeur,hauteur);

        for (int y = 0; y < hauteur; ++y){
            for (int x = 0; x < largeur; ++x){
                Float[] pixelsDroite;
                Float[] pixelsGauche;

                Float[] pixelsDest = new Float[3];

                pixelsGauche = gauche.getPixel(x,y);
                pixelsDroite = droite.getPixel(x,y);

                pixelsDest[0] = doseRougeDubois(pixelsGauche[0],pixelsGauche[1],pixelsGauche[2],pixelsDroite[0],pixelsDroite[1],pixelsDroite[2]);
                pixelsDest[1] = doseVerteDubois(pixelsGauche[0],pixelsGauche[1],pixelsGauche[2],pixelsDroite[0],pixelsDroite[1],pixelsDroite[2]);
                pixelsDest[2] = doseBleueDubois(pixelsGauche[0],pixelsGauche[1],pixelsGauche[2],pixelsDroite[0],pixelsDroite[1],pixelsDroite[2]);

                inter.setPixel(x,y,pixelsDest);

            }
        }
        DisplayUtilities.display(inter);

    }

    public static MBFImage anaglypheImage(MBFImage source, int largeur, int hauteur) throws IOException {

       // ThreadEnCours t = new ThreadEnCours(conteneur);
       // t.start();

        MBFImage dest = new MBFImage(largeur, hauteur);

        for (int y = DECALAGE; y < hauteur - DECALAGE; ++y) {
            for (int x = DECALAGE; x < largeur - DECALAGE; ++x) {

                Float[] pixels;
                Float[] pixelsDest;

                //Recupere la couleur rouge
                pixelsDest = source.getPixel(x - DECALAGE, y);

                pixels = source.getPixel(x + DECALAGE, y);

                pixelsDest[1] = pixels[1];
                pixelsDest[2] = pixels[2];

                dest.setPixel(x, y, pixelsDest);

            }
        }
        return dest;
    }

    public static void anaglyphe() throws IOException {

        XuggleVideoWriter sortie = new XuggleVideoWriter(fichierSortie.getName(),video.getWidth(), video.getHeight(),video.getFPS());
        sortie.initialise();

        MBFImage frame;
        frame = video.getCurrentFrame();

        while(video.hasNextFrame()){

            sortie.addFrame(Traitement.anaglypheImage(frame,video.getWidth(),video.getHeight()));
            System.out.println("Traitement");
            frame = video.getNextFrame();

        }
        sortie.processingComplete();
        sortie.close();

    }

    public static void barcode() throws IOException {

        ThreadEnCours t = new ThreadEnCours(conteneur);
        t.start();

        //Placement horizontal dans image de sortie.
        int cptX = 0;
        //Compteur de KeyFrame
        int kFrameCpt = 0;

        MBFImage frame;
        MBFImage frameResume;
        MBFImage imgSortieTmp = new MBFImage((int) (TAILLEBANDE * video.countFrames()) / 10, video.getCurrentFrame().getHeight());
        
        //Récupération de la première frame.
        frame = video.getCurrentFrame();

        while (video.hasNextFrame()) {
            if (video.nextFrameIsKeyFrame) {
                kFrameCpt ++;

                //Recuperation de la bande centrale.
                frameResume = getBandeCentrale(frame);

                //Dessine dans l'image de sortie, la bande centrale récupérée.
                imgSortieTmp.drawImage(frameResume, cptX, 0);
                cptX += TAILLEBANDE;

                frame = video.getNextFrame();
            }
            frame = video.getNextFrame();
        }
        MBFImage ImageSortie = new MBFImage( (int)(TAILLEBANDE * kFrameCpt), video.getCurrentFrame().getHeight());
        ImageSortie.drawImage(imgSortieTmp, 0,0);
        ImageUtilities.write(ImageSortie, "png", fichierSortie);
        video.close();
        t.detruire();
        JOptionPane.showMessageDialog(conteneur, "Traitement terminé !", "Résumé vidéo", JOptionPane.PLAIN_MESSAGE);
    }

    public static void sideBySide() throws IOException {

        //Récupération de l'image source et création de l'image de destination contenant les deux images gauche et droite.
        MBFImage source = ImageUtilities.readMBF(new File("src/main/resources/sbs.jpg"));
        MBFImage dest = new MBFImage((source.getWidth() - ESPACEMENT) * 2, source.getHeight());

        //Création des deux images gauches et droite auquels on retire un bout corresspondant à  ce que voit l'oeil.
        MBFImage gauche = new MBFImage(source.getWidth() - ESPACEMENT, source.getHeight());
        MBFImage droite = new MBFImage(source.getWidth() - ESPACEMENT, source.getHeight());

        //Fenetre d'affichage des images.
        DisplayUtilities.createNamedWindow("droite");
        DisplayUtilities.createNamedWindow("gauche");

        //Correspond à l'endroit ou doit s'arreter le parcours de l'image source pour générer l'image gauche.
        int parcoursX = source.getWidth() - ESPACEMENT;

        //Indice de remplissage de l'image de l'oeil droit.
        int indexdroite = 0;

        //Parcours des y de l'image.
        for (int y = 0; y < source.getHeight(); ++y) {
            //Remplissage de l'image gauche.
            for (int x = 0; x < parcoursX; ++x) {
                Float[] pixels;
                pixels = source.getPixel(x, y);
                gauche.setPixel(x, y, pixels);
            }
            //Remplissage de l'aimge droite. On commence à partir de l'espcaement.
            for (int xD = ESPACEMENT; xD < source.getWidth(); ++xD) {
                Float[] pixels;
                pixels = source.getPixel(xD, y);
                //Besoin d'un index pour remplir l'imge de destination depuis 0 et non depuis l'espacement.
                //Ananlyse si on est en fin de parcours d'un ligne de pixel.
                if (indexdroite == droite.getWidth()) {
                    droite.setPixel(indexdroite, y, pixels);
                    indexdroite = 0;
                } //On est pas en fin de ligne donc remplissage.
                else {
                    droite.setPixel(indexdroite, y, pixels);
                }
                indexdroite++;

            }

        }
        //Remplissage de l'image finale à l'aide des images gauche et droite.
        dest.drawImage(gauche, 0, 0);
        dest.drawImage(droite, gauche.getWidth(), 0);

        //Affichage.
        //DisplayUtilities.displayName(gauche,"gauche");
        //DisplayUtilities.displayName(droite,"droite");
        DisplayUtilities.display(dest);
    }

    private static MBFImage getBandeCentrale(MBFImage source) throws IOException {

        int hauteur = source.getHeight();
        int milieu = source.getWidth() / 2;

        //Parcours des X
        int cptXDest = 0;

        MBFImage sortie = new MBFImage(TAILLEBANDE, hauteur);

        for (int y = 0; y < hauteur; ++y) {
            for (int x = milieu - (TAILLEBANDE / 2); x < milieu + (TAILLEBANDE / 2); ++x) {
                sortie.setPixel(cptXDest, y, source.getPixel(x, y));
                cptXDest++;
            }
            cptXDest = 0;
        }
        return sortie;
    }

    public static void setFile(File f) {
        fichierSortie = f;
    }

    private static void setOptions(){
    }


    private static float doseRougeDubois(float rouge, float vert, float bleu,float rougeD, float vertD, float bleuD) {
        return (float) ( (rouge * 0.4154) + (vert * 0.4710) + (bleu *0.1669) + (rougeD * -0.0109) + (vertD * -0.0364) + (bleuD * 0.0060) );

    }
    private static float doseVerteDubois(float rouge, float vert, float bleu,float rougeD, float vertD, float bleuD) {
        return (float) ( (rouge * -0.0458)+ (vert * -0.0484) + (bleu *-0.0257) + (rougeD * 0.3756) + (vertD * 0.7333) + (bleuD * 0.0111) );
    }

    private static float doseBleueDubois(float rouge, float vert, float bleu,float rougeD, float vertD, float bleuD) {
        return (float) ( (rouge * -0.0547) + (vert * 04710) + (bleu *0.0128) + (rougeD * -0.0651) + (vertD * -0.1287) + (bleuD * 1.2971) );
    }


}
