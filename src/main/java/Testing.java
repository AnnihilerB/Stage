import interfacegraphique.Conteneur;
import interfacegraphique.Fenetre;

public class Testing {

    public static void main(String args[]) {

        Fenetre f = new Fenetre();
        Conteneur cont = new Conteneur(f);
        f.ajouterConteneur(cont);


    }
}
