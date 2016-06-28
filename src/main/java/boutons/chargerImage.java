package boutons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

public class chargerImage implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		JFileChooser choixFichier = new JFileChooser();
                choixFichier.showOpenDialog(null);
	}

}
