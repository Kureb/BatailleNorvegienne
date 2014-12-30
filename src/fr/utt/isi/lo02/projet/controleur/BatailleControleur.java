package fr.utt.isi.lo02.projet.controleur;

import javax.swing.JOptionPane;

import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.vue.VueBataille;
import fr.utt.isi.lo02.projet.vue.DialogChangerCartes;

public class BatailleControleur {

	private static Bataille bataille;
	
	private static VueBataille vueBataille;
	
	public BatailleControleur(Bataille bataille,VueBataille vueBataille) {
		this.bataille = bataille;
		this.vueBataille = vueBataille;
	}
	
	public void changerTas(Carte carte) {
		vueBataille.majTas(carte);
	}
	
	
	public static void updateJTextArea(String message) {
		vueBataille.getLog().append(message + "\n");
		vueBataille.getLog().setCaretPosition(vueBataille.getLog().getDocument().getLength());
	}

	public static void fenetreChoixEchangeJoueurReel(Joueur joueur) {
		 DialogChangerCartes zd = new DialogChangerCartes(null, "Echange des cartes de " + joueur.getNom(), true, joueur);
		 zd.setVisible(true);
		 if (zd.getCarteMain() != null && zd.getCarteVisible() != null)
			 BatailleControleur.echange(zd.getJoueur(), zd.getCarteMain(), zd.getCarteVisible());
		 
		
	}

	public static void echange(Joueur joueur, Carte carteVisible, Carte carteMain) {
		joueur.echangerCarte(carteVisible, carteMain);
		
	}
	
	
	
	
	

}
