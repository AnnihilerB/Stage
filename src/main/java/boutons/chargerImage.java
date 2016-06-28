package boutons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFileChooser;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

public class chargerImage implements ActionListener {

	public void actionPerformed(ActionEvent e) {
                MBFImage source = null;
		JFileChooser choixFichier = new JFileChooser();
                choixFichier.showOpenDialog(null);
            try {
                source = ImageUtilities.readMBF(choixFichier.getSelectedFile());
            } catch (IOException ex) {
                System.err.print("Le fichier choisi n'est pas une image.");
            }
            DisplayUtilities.display(source);
	}

}
