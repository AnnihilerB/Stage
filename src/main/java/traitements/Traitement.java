package traitements;

import exceptions.DestinationManquante;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.video.xuggle.XuggleVideo;
import org.openimaj.video.xuggle.XuggleVideoWriter;
import utils.OptionsBarcode;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Traitement {

    /** Classe principale qui regroupe tous les traitements en static. La source et la destnation sont
     *  les mêmes pour tous les traitements d'où le choix du static.
     */

    private static final int DECALAGE = 5;
    //Correspond à l'espacement entre les deux yeux.
    private static final int ESPACEMENT =15;

    //Donnée membre en static pour qu'elle soit la même pour toutes les méthodes. (Non spécifique à une instance).
    protected static XuggleVideo video;
    protected static File fichierSortie;
    protected static JPanel conteneur;

    public Traitement(XuggleVideo v, JPanel cont) {
        video = v;
        conteneur = cont;
    }
    public static MBFImage  anaglypheDuboisImage(MBFImage source) throws IOException {

        MBFImage imgGauche = decouperImageGauche(source, ESPACEMENT);
        MBFImage imgDroite = decouperImageDroite(source, ESPACEMENT);

        int largeur = imgGauche.getWidth();
        int hauteur = imgGauche.getHeight();

        MBFImage imgSortie = new MBFImage(largeur,hauteur);

        Float[] pixelsDroite;
        Float[] pixelsGauche;

        Float[] pixelsDest = new Float[3];

        for (int y = 0; y < hauteur; ++y){
            for (int x = 0; x < largeur; ++x){


                pixelsGauche = imgGauche.getPixel(x,y);
                pixelsDroite = imgDroite.getPixel(x,y);

                pixelsDest[0] = doseRougeDubois(pixelsGauche[0],pixelsGauche[1],pixelsGauche[2],pixelsDroite[0],pixelsDroite[1],pixelsDroite[2]);
                pixelsDest[1] = doseVerteDubois(pixelsGauche[0],pixelsGauche[1],pixelsGauche[2],pixelsDroite[0],pixelsDroite[1],pixelsDroite[2]);
                pixelsDest[2] = doseBleueDubois(pixelsGauche[0],pixelsGauche[1],pixelsGauche[2],pixelsDroite[0],pixelsDroite[1],pixelsDroite[2]);

                imgSortie.setPixel(x,y,pixelsDest);

            }
        }
        return imgSortie;
    }

    public static void anaglypheDubois() throws IOException, DestinationManquante {

        verifSortie();

        //Création du thread inforamtnt sur le déroulement du traitement.
        ThreadEnCours thread = new ThreadEnCours(conteneur);
        thread.start();

        XuggleVideoWriter videoSortie = new XuggleVideoWriter(fichierSortie.getAbsolutePath(),video.getWidth(), video.getHeight(),video.getFPS());
        videoSortie.initialise();

        MBFImage frame;
        frame = video.getCurrentFrame();

        while(video.hasNextFrame()){

            videoSortie.addFrame( Traitement.anaglypheDuboisImage(frame) );
            frame = video.getNextFrame();

        }
        videoSortie.processingComplete();
        thread.detruire();
        videoSortie.close();
        JOptionPane.showMessageDialog(conteneur, "Traitement termine !", "Anaglyphe Dubois", JOptionPane.PLAIN_MESSAGE);
    }

    /** Realise l'effet anaglyphe sur une image passee en parametre et la retourne
     *
     * @param source : image sur laquelle realiser l'anaglyphe.
     * @param largeur : largeur de l'image
     * @param hauteur : hauteur de l'image
     * @return retourne l'image anaglyphee.
     * @throws IOException
     */
    public static MBFImage anaglypheImage(MBFImage source, int largeur, int hauteur) throws IOException {

        MBFImage dest = new MBFImage(largeur, hauteur);

        for (int y = DECALAGE; y < hauteur - DECALAGE; ++y) {
            for (int x = DECALAGE; x < largeur - DECALAGE; ++x) {

                //Tableaux de pixel de l'image source et de l'image anaglyphee
                Float[] pixels;
                Float[] pixelsDest;

                //Recupere la couleur rouge directerment depuis la source.
                pixelsDest = source.getPixel(x - DECALAGE, y);

                // Recuperation des couleurs du pixel de l'image source.
                pixels = source.getPixel(x + DECALAGE, y);

                //Recuperation des canaux rouge/vert pour faire le cyan.
                pixelsDest[1] = pixels[1];
                pixelsDest[2] = pixels[2];

                //Coloration de l'image de destination.
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

        //Vérification de la spécification de la sortie.
        verifSortie();

        //Création du thread inforamtnt sur le déroulement du traitement.
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
        JOptionPane.showMessageDialog(conteneur, "Traitement termine !", "Anaglyphe", JOptionPane.PLAIN_MESSAGE);

    }

    /**
     * Réalise le moviebarcode sur la donnée membre vidéo.
     * @throws IOException
     * @throws DestinationManquante
     */

    public static void barcode() throws IOException, DestinationManquante {

        int tailleBande = OptionsBarcode.getTailleBande();

        verifSortie();

        ThreadEnCours thread = new ThreadEnCours(conteneur);
        thread.start();

        //Placement horizontal dans image de sortie.
        int parcoursXImgSortie = 0;

        MBFImage frame;
        MBFImage bandeCentralFrame;
        MBFImage imgSortie = new MBFImage(OptionsBarcode.getLargeur(), video.getCurrentFrame().getHeight());
        
        //Recuperation de la premiere frame.
        frame = video.getCurrentFrame();

        while (video.hasNextFrame() && (parcoursXImgSortie < OptionsBarcode.getLargeur()) ) {
            if (video.nextFrameIsKeyFrame) {
                //recuperation de la keframe
                frame = video.getNextFrame();

                //Recuperation de la bande centrale.
                bandeCentralFrame = getBandeCentrale(frame,tailleBande);

                //Dessine dans l'image de sortie, la bande centrale recuperee.
                imgSortie.drawImage(bandeCentralFrame, parcoursXImgSortie, 0);
                parcoursXImgSortie += tailleBande;

                //Passage a la frame suivante
                frame = video.getNextFrame();
            }
            //Frame n'est pas une keyframe, recuperation de la suivante
            frame = video.getNextFrame();
        }
        ImageUtilities.write(imgSortie, "png", fichierSortie);
        video.close();
        thread.detruire();
        thread.interrupt();
        JOptionPane.showMessageDialog(conteneur, "Traitement termine !", "Resume video", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Realise le side by side sur une seule image. A partir d'une image, elle est dupliquee,
     * la premiere est coupee sur la droite, la seconde sur la gauche.
     * @param source : image dont on doit realiser l'effet.
     * @return : Retourne une image qui fait deux fois la taille initiale moins le tronquage.
     * @throws IOException
     */

    public static MBFImage sideBySideImage(MBFImage source) throws IOException {

        //Recuperation de l'image source et creation de l'image de destination contenant les deux images gauche et droite.
        MBFImage dest = new MBFImage((source.getWidth() - ESPACEMENT) * 2, source.getHeight());

        //Creation des deux images gauches et droite auquels on retire un bout corresspondant a ce que voit l'oeil.
        MBFImage imgGauche = decouperImageGauche(source, ESPACEMENT);
        MBFImage imgDroite = decouperImageDroite(source, ESPACEMENT);

        //Remplissage de l'image finale a l'aide des images gauche et droite.
        dest.drawImage(imgGauche, 0, 0);
        dest.drawImage(imgDroite, imgGauche.getWidth(), 0);
        return dest;
    }

    /**
     * Realise le side by side sur une video entiere.
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
        JOptionPane.showMessageDialog(conteneur, "Traitement termine !", "Side-by-side", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Methode permettant de recuperer la bande centrale d'une image. Necessaire pour le movie barcode.
     * @param source : image dont la bande centrale doit etre recuperee.
     * @return : la bande centrale de l'image source.
     * @throws IOException
     */

    public static MBFImage getBandeCentrale(MBFImage source, int tailleBande) throws IOException {

        int hauteur = source.getHeight();
        int milieu = source.getWidth() / 2;

        //Parcours des X
        int parcoursXImgDest = 0;

        MBFImage imgSortie = new MBFImage(tailleBande, hauteur);

        for (int y = 0; y < hauteur; ++y) {
            //Parcours de la source sur la taille de la bande precisee.
            for (int x = milieu - (tailleBande / 2); x <= milieu + (tailleBande / 2); ++x) {
                //Ecriture de l'image de sortie.
                imgSortie.setPixel(parcoursXImgDest, y, source.getPixel(x, y));
                parcoursXImgDest++;
            }
            //Remise a zero du parcours horizontal quand on change de Y.
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
     * Matrice de selection des couleurs pour l'effet anaglyohe Dubois.
     * @param rouge : dose rouge image gauche.
     * @param vert : dose verte image gauche.
     * @param bleu : dose bleu image gauche
     * @param rougeD : dose rouge image droite.
     * @param vertD : dose verte image droite.
     * @param bleuD : dose bleu image droite.
     * @return : La couleur rouge de l'image anaglyphee.
     */

    private static float doseRougeDubois(float rouge, float vert, float bleu,float rougeD, float vertD, float bleuD) {
        return (float) ( (rouge * 0.4154) + (vert * 0.4710) + (bleu *0.1669) + (rougeD * (-0.0109) ) + (vertD * (-0.0364) ) + (bleuD * 0.0060) );

    }

    /**
     * Matrice de selection des couleurs pour l'effet anaglyohe Dubois.
     * @param rouge : dose rouge image gauche.
     * @param vert : dose verte image gauche.
     * @param bleu : dose bleu image gauche
     * @param rougeD : dose rouge image droite.
     * @param vertD : dose verte image droite.
     * @param bleuD : dose bleu image droite.
     * @return : La couleur verte de l'image anaglyphee.
     */
    private static float doseVerteDubois(float rouge, float vert, float bleu,float rougeD, float vertD, float bleuD) {
        return (float) ( (rouge * (-0.0458) )+ (vert * (-0.0484) ) + (bleu * (-0.0257) ) + (rougeD * 0.3756) + (vertD * 0.7333) + (bleuD * 0.0111) );
    }

    /**
     * Matrice de selection des couleurs pour l'effet anaglyohe Dubois.
     * @param rouge : dose rouge image gauche.
     * @param vert : dose verte image gauche.
     * @param bleu : dose bleu image gauche
     * @param rougeD : dose rouge image droite.
     * @param vertD : dose verte image droite.
     * @param bleuD : dose bleu image droite.
     * @return : La couleur bleue de l'image anaglyphee.
     */
    private static float doseBleueDubois(float rouge, float vert, float bleu,float rougeD, float vertD, float bleuD) {
        return (float) ( (rouge * (-0.0547) ) + (vert * 0.4710) + (bleu *0.0128) + (rougeD * (-0.0651) ) + (vertD * (-0.1287) ) + (bleuD * 1.2971) );
    }

    /**
     * Verifie si un fichier de sortie a ete specifie.
     * @throws DestinationManquante
     */
    private static void verifSortie() throws DestinationManquante {
        if (fichierSortie == null)
            throw new DestinationManquante(conteneur);
    }

    public static MBFImage decouperImageGauche(MBFImage source, int espacement) throws IOException {

        //Creation des deux images gauches et droite auquels on retire un bout correspondant a ce que voit l'oeil.
        MBFImage imgGauche = new MBFImage(source.getWidth() - espacement, source.getHeight());

        //Correspond a l'endroit ou doit s'arreter le parcours de l'image source pour generer l'image gauche.
        int parcoursXSource = source.getWidth() - espacement;


        //Parcours des y de l'image.
        for (int y = 0; y < source.getHeight(); ++y) {
            //Remplissage de l'image gauche.
            for (int x = 0; x < parcoursXSource; ++x) {
                Float[] pixels;
                pixels = source.getPixel(x, y);
                imgGauche.setPixel(x, y, pixels);
            }
        }
        ImageUtilities.write(imgGauche, new File("imggauchedecouperdansmethode.png"));
        return imgGauche;
    }

    public static MBFImage decouperImageDroite(MBFImage source, int espacement) throws IOException {

        //Creation des deux images gauches et droite auquels on retire un bout corresspondant a  ce que voit l'oeil.
        MBFImage imgDroite = new MBFImage(source.getWidth() - espacement, source.getHeight());

        //Indice de remplissage de l'image de l'oeil droit.
        int indexRemplissageXImageDroite = 0;

        //Parcours des y de l'image.
        for (int y = 0; y < source.getHeight(); ++y) {
            //Remplissage de l'aimge droite. On commence a partir de l'espcaement.
            for (int xD = espacement; xD < source.getWidth(); ++xD) {
                Float[] pixels;
                pixels = source.getPixel(xD, y);
                //Besoin d'un index pour remplir l'imge de destination depuis 0 et non depuis l'espacement.
                //Analyse si on est en fin de parcours d'un ligne de pixel.
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
        return imgDroite;
    }

    public static void setVideo(XuggleVideo video) {
        Traitement.video = video;
    }

    public static void setConteneur(JPanel conteneur) {
        Traitement.conteneur = conteneur;
    }
}
