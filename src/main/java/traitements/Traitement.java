package traitements;

import exceptions.DestinationManquante;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.video.xuggle.XuggleVideo;
import org.openimaj.video.xuggle.XuggleVideoWriter;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Traitement {

    /** Classe principale qui regroupe tous les traitements en static. La source et la destnation sont
     *  les mêmes pour tous les traitements d'où le choix du static.
     */

    private static final int DECALAGE = 5;
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

        MBFImage imgDroite = ImageUtilities.readMBF(new File("src/main/resources/right.jpg"));
        MBFImage imgGauche = ImageUtilities.readMBF(new File("src/main/resources/left.jpg"));

        int largeur = imgGauche.getWidth();
        int hauteur = imgGauche.getHeight();

        MBFImage imgSortie = new MBFImage(largeur,hauteur);

        for (int y = 0; y < hauteur; ++y){
            for (int x = 0; x < largeur; ++x){
                Float[] pixelsDroite;
                Float[] pixelsGauche;

                Float[] pixelsDest = new Float[3];

                pixelsGauche = imgGauche.getPixel(x,y);
                pixelsDroite = imgDroite.getPixel(x,y);

                pixelsDest[0] = doseRougeDubois(pixelsGauche[0],pixelsGauche[1],pixelsGauche[2],pixelsDroite[0],pixelsDroite[1],pixelsDroite[2]);
                pixelsDest[1] = doseVerteDubois(pixelsGauche[0],pixelsGauche[1],pixelsGauche[2],pixelsDroite[0],pixelsDroite[1],pixelsDroite[2]);
                pixelsDest[2] = doseBleueDubois(pixelsGauche[0],pixelsGauche[1],pixelsGauche[2],pixelsDroite[0],pixelsDroite[1],pixelsDroite[2]);

                imgSortie.setPixel(x,y,pixelsDest);

            }
        }
        DisplayUtilities.display(imgSortie);

    }

    /** Réalise l'effet anaglyphe sur une image passée en paramètre et la retourne
     *
     * @param source : image sur laquelle réalisée l'anaglyphe.
     * @param largeur : largeur de l'image
     * @param hauteur : hauteur de l'image
     * @return retourne l'image anaglyphée.
     * @throws IOException
     */
    public static MBFImage anaglypheImage(MBFImage source, int largeur, int hauteur) throws IOException {

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

    /**
     * Récupère la donnée membre vidéo et réalise une anaglyphe sur chaque frame et reconstruit une vidéo
     * anaglyphée.
     * @throws IOException
     * @throws DestinationManquante
     */

    public static void anaglyphe() throws IOException, DestinationManquante {

        verifSortie();

        ThreadEnCours thread = new ThreadEnCours(conteneur);
        thread.start();

        XuggleVideoWriter videoSortie = new XuggleVideoWriter(fichierSortie.getAbsolutePath(),video.getWidth(), video.getHeight(),video.getFPS());
        videoSortie.initialise();

        MBFImage frame;
        frame = video.getCurrentFrame();

        while(video.hasNextFrame()){

            videoSortie.addFrame(Traitement.anaglypheImage(frame,video.getWidth(),video.getHeight()));
            //System.out.println("Traitement");
            frame = video.getNextFrame();

        }
        videoSortie.processingComplete();
        thread.detruire();
        videoSortie.close();
        JOptionPane.showMessageDialog(conteneur, "Traitement terminé !", "Résumé vidéo", JOptionPane.PLAIN_MESSAGE);

    }

    /**
     * Réalise le moviebarcode sur la donnée membre vidéo.
     * @throws IOException
     * @throws DestinationManquante
     */

    public static void barcode() throws IOException, DestinationManquante {

        verifSortie();

        ThreadEnCours thread = new ThreadEnCours(conteneur);
        thread.start();

        //Placement horizontal dans image de sortie.
        int parcoursXImgSortie = 0;
        //Compteur de KeyFrame
        int nbKeyFrame = 0;

        MBFImage frame;
        MBFImage bandeCentralFrame;
        MBFImage imgSortieTmp = new MBFImage((int) (TAILLEBANDE * video.countFrames()) / 10, video.getCurrentFrame().getHeight());
        
        //Récupération de la première frame.
        frame = video.getCurrentFrame();

        while (video.hasNextFrame()) {
            if (video.nextFrameIsKeyFrame) {
                //r&cupération de la keframe
                frame = video.getNextFrame();
                nbKeyFrame ++;

                //Recuperation de la bande centrale.
                bandeCentralFrame = getBandeCentrale(frame);

                //Dessine dans l'image de sortie, la bande centrale récupérée.
                imgSortieTmp.drawImage(bandeCentralFrame, parcoursXImgSortie, 0);
                parcoursXImgSortie += TAILLEBANDE;

                //Passage a la frame suivante
                frame = video.getNextFrame();
            }
            //Frame n'est pas une keyframe, récupération de la suivante
            frame = video.getNextFrame();
        }
        MBFImage imageSortie = new MBFImage( (int)(TAILLEBANDE * nbKeyFrame), video.getCurrentFrame().getHeight());
        imageSortie.drawImage(imgSortieTmp, 0,0);
        ImageUtilities.write(imageSortie, "png", fichierSortie);
        video.close();
        thread.detruire();
        JOptionPane.showMessageDialog(conteneur, "Traitement terminé !", "Résumé vidéo", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Réalise le side by side sur une seule image. A partir d'une image, elle est dupliquée,
     * la première est coupée sur la droite, la seconde sur la gauche.
     * @param source : image dont on doit réaliser l'effet.
     * @return : Retourne une image qui fait deux fois la taille initiale moins le tronquage.
     * @throws IOException
     */

    public static MBFImage sideBySideImage(MBFImage source) throws IOException {

        //Récupération de l'image source et création de l'image de destination contenant les deux images gauche et droite.
        MBFImage dest = new MBFImage((source.getWidth() - ESPACEMENT) * 2, source.getHeight());

        //Création des deux images gauches et droite auquels on retire un bout corresspondant à  ce que voit l'oeil.
        MBFImage imgGauche = new MBFImage(source.getWidth() - ESPACEMENT, source.getHeight());
        MBFImage imgDroite = new MBFImage(source.getWidth() - ESPACEMENT, source.getHeight());

        //Correspond à l'endroit ou doit s'arreter le parcours de l'image source pour générer l'image gauche.
        int parcoursXSource = source.getWidth() - ESPACEMENT;

        //Indice de remplissage de l'image de l'oeil droit.
        int indexRemplissageXImageDroite = 0;

        //Parcours des y de l'image.
        for (int y = 0; y < source.getHeight(); ++y) {
            //Remplissage de l'image gauche.
            for (int x = 0; x < parcoursXSource; ++x) {
                Float[] pixels;
                pixels = source.getPixel(x, y);
                imgGauche.setPixel(x, y, pixels);
            }
            //Remplissage de l'aimge droite. On commence à partir de l'espcaement.
            for (int xD = ESPACEMENT; xD < source.getWidth(); ++xD) {
                Float[] pixels;
                pixels = source.getPixel(xD, y);
                //Besoin d'un index pour remplir l'imge de destination depuis 0 et non depuis l'espacement.
                //Ananlyse si on est en fin de parcours d'un ligne de pixel.
                if (indexRemplissageXImageDroite == imgDroite.getWidth()) {
                    imgDroite.setPixel(indexRemplissageXImageDroite, y, pixels);
                    indexRemplissageXImageDroite = 0;
                } //On est pas en fin de ligne donc remplissage.
                else {
                    imgDroite.setPixel(indexRemplissageXImageDroite, y, pixels);
                }
                indexRemplissageXImageDroite++;
            }
        }
        //Remplissage de l'image finale à l'aide des images gauche et droite.
        dest.drawImage(imgGauche, 0, 0);
        dest.drawImage(imgDroite, imgGauche.getWidth(), 0);
        return dest;
    }

    /**
     * Réalise le side by side sur une vidéo entière.
     * @throws IOException
     * @throws DestinationManquante
     */

    public static void sideBySide() throws IOException, DestinationManquante {

        verifSortie();

        ThreadEnCours thread = new ThreadEnCours(conteneur);
        thread.start();

        XuggleVideoWriter Videosortie = new XuggleVideoWriter(fichierSortie.getAbsolutePath(),(video.getWidth() - ESPACEMENT) * 2, video.getHeight(),video.getFPS());
        Videosortie.initialise();

        MBFImage frame;
        frame = video.getCurrentFrame();

        while(video.hasNextFrame()){

            Videosortie.addFrame(sideBySideImage(frame));
            //System.out.println("Traitement");
            frame = video.getNextFrame();

        }
        Videosortie.processingComplete();
        thread.detruire();
        Videosortie.close();
        JOptionPane.showMessageDialog(conteneur, "Traitement terminé !", "Résumé vidéo", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Méthode permettant de récupérer la bande centrale d'une image. Nécessaire pour le movie barcode.
     * @param source : image dont la bande centrale doit être récupérée.
     * @return : la bande centrale de l'image source.
     * @throws IOException
     */

    private static MBFImage getBandeCentrale(MBFImage source) throws IOException {

        int hauteur = source.getHeight();
        int milieu = source.getWidth() / 2;

        //Parcours des X
        int parcoursXImgDest = 0;

        MBFImage imgSortie = new MBFImage(TAILLEBANDE, hauteur);

        for (int y = 0; y < hauteur; ++y) {
            //Parcours de la source sur la taille de la bande précisée.
            for (int x = milieu - (TAILLEBANDE / 2); x < milieu + (TAILLEBANDE / 2); ++x) {
                //Ecriture de l'image de sortie.
                imgSortie.setPixel(parcoursXImgDest, y, source.getPixel(x, y));
                parcoursXImgDest++;
            }
            //Remise à zéro du parcours horizontal quand on change de Y.
            parcoursXImgDest = 0;
        }
        return imgSortie;
    }

    /**
     * Paramètre le fichier de sortie.
     * @param f : le fichier choisi lors del'appui sur le bouton.
     */
    public static void setFile(File f) {
        fichierSortie = f;
    }

    /**
     * Matrice de sélection des couleurs pour l'effet anaglyohe Dubois.
     * @param rouge : dose rouge image gauche.
     * @param vert : dose verte image gauche.
     * @param bleu : dose bleu image gauche
     * @param rougeD : dose rouge image droite.
     * @param vertD : dose verte image droite.
     * @param bleuD : dose bleu image droite.
     * @return : La couleur rouge de l'image anaglyphée.
     */

    private static float doseRougeDubois(float rouge, float vert, float bleu,float rougeD, float vertD, float bleuD) {
        return (float) ( (rouge * 0.4154) + (vert * 0.4710) + (bleu *0.1669) + (rougeD * -0.0109) + (vertD * -0.0364) + (bleuD * 0.0060) );

    }

    /**
     * Matrice de sélection des couleurs pour l'effet anaglyohe Dubois.
     * @param rouge : dose rouge image gauche.
     * @param vert : dose verte image gauche.
     * @param bleu : dose bleu image gauche
     * @param rougeD : dose rouge image droite.
     * @param vertD : dose verte image droite.
     * @param bleuD : dose bleu image droite.
     * @return : La couleur verte de l'image anaglyphée.
     */
    private static float doseVerteDubois(float rouge, float vert, float bleu,float rougeD, float vertD, float bleuD) {
        return (float) ( (rouge * -0.0458)+ (vert * -0.0484) + (bleu *-0.0257) + (rougeD * 0.3756) + (vertD * 0.7333) + (bleuD * 0.0111) );
    }

    /**
     * Matrice de sélection des couleurs pour l'effet anaglyohe Dubois.
     * @param rouge : dose rouge image gauche.
     * @param vert : dose verte image gauche.
     * @param bleu : dose bleu image gauche
     * @param rougeD : dose rouge image droite.
     * @param vertD : dose verte image droite.
     * @param bleuD : dose bleu image droite.
     * @return : La couleur bleue de l'image anaglyphée.
     */
    private static float doseBleueDubois(float rouge, float vert, float bleu,float rougeD, float vertD, float bleuD) {
        return (float) ( (rouge * -0.0547) + (vert * 04710) + (bleu *0.0128) + (rougeD * -0.0651) + (vertD * -0.1287) + (bleuD * 1.2971) );
    }

    /**
     * Vérifie si un fichier de sortie a été spécifié.
     * @throws DestinationManquante
     */
    private static void verifSortie() throws DestinationManquante {
        if (fichierSortie == null)
            throw new DestinationManquante(conteneur);
    }


}
