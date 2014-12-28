package fr.utt.isi.lo02.projet.vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.utt.isi.lo02.projet.modele.Carte;

/**
 * Classe qui gère la vue d'une carte (modèle)
 * @see fr.utt.isi.lo02.projet.modele.Carte
 * @author daussy
 *
 */
public class VueCarte {

	/**
	 * Carte à représenter graphiquement
	 */
	private Carte carte;
	
	/**
	 * Image représentant la carte
	 */
	private JLabel image;
	
	
	public VueCarte(Carte carte) {
		this.carte = carte;
		
		String cheminImage = "img/" + carte.VALEURS[carte.getValeur()] + "_" + carte.COULEURS[carte.getCouleur()] + ".png";
		
		try {
			BufferedImage img = ImageIO.read(new File(cheminImage));
			image = new JLabel(new ImageIcon(cheminImage));
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}


	public Carte getCarte() {
		return carte;
	}


	public void setCarte(Carte carte) {
		this.carte = carte;
	}


	public JLabel getImage() {
		return image;
	}


	public void setImage(JLabel image) {
		this.image = image;
	}
	
	
	public static void main(String[] args) {
		Carte carte = new Carte(2, 2);
		Carte carte2 = new Carte(10, 0);
		VueCarte carteGraphique = new VueCarte(carte);
		VueCarte carteGraphique2 = new VueCarte(carte2);
		
		JFrame fenetre = new JFrame("Test Carte");
		fenetre.setLayout(new FlowLayout());
		Container reservoir = fenetre.getContentPane();

		reservoir.add(carteGraphique.getImage()); 
		reservoir.add(carteGraphique2.getImage()); 
		
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setVisible(true);
		
		
	}
	
	
}
