package fr.utt.isi.lo02.projet.vue;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
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
	
	
	/**
	 * Construit la vue d'une carte en fonction de la carte
	 * @param carte Carte à représenter de façon graphique
	 */
	public VueCarte(Carte carte) {
		this.carte = carte;
		String cheminImage = "img/" + carte.getValeurs()[carte.getValeur()] + "_" + carte.getCouleurs()[carte.getCouleur()] + ".png";
		this.image = new JLabel(new ImageIcon(cheminImage));
	}


	/**
	 * Getter de carte
	 * @return la carte à représenter
	 */
	public Carte getCarte() {
		return carte;
	}

	/**
	 * Getter de l'image
	 * @return l'image (Jlabel) associée à la carte
	 */
	public JLabel getImage() {
		return image;
	}

}
