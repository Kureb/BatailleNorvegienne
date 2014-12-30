package fr.utt.isi.lo02.projet.controleur;

import javax.swing.JOptionPane;

import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.vue.DialogEnvoyerTasJoueur;
import fr.utt.isi.lo02.projet.vue.VueBataille;
import fr.utt.isi.lo02.projet.vue.DialogChangerCartes;
import fr.utt.isi.lo02.projet.vue.DialogJouerCarte;

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
		 DialogChangerCartes dialog = new DialogChangerCartes(null, "Echange des cartes de " + joueur.getNom(), true, joueur);
		 dialog.setVisible(true);
		 //TODO faire en sorte de lui demander plusieurs fois !
		 if (dialog.getCarteMain() != null && dialog.getCarteVisible() != null) {
			 BatailleControleur.echange(dialog.getJoueur(), dialog.getCarteMain(), dialog.getCarteVisible());
			 BatailleControleur.updateJTextArea(dialog.getJoueur().getNom() + " échange " +  dialog.getCarteMain() + " et " + dialog.getCarteVisible() + ".");
		}
			 
	}
	
	
	public static Joueur fenetreChoixVictimeAs(Joueur joueur) {
		DialogEnvoyerTasJoueur dialog = new DialogEnvoyerTasJoueur(null, "Qui sera la vicime ?", true, joueur);
		dialog.setVisible(true);
		Joueur victime = dialog.getVictime();
		if (victime != null) 
			return victime;
		else return null;
	}
	
	
	public static Carte fenetreChoixCarteJouer(Joueur joueur) {
		
		DialogJouerCarte dialog = new DialogJouerCarte(null, "Quelle carte jouer ?", true, joueur);
		//Carte carte = DialogJouerCarte.openForm(null, joueur);
		dialog.setVisible(true);
		Carte carte = dialog.getCarteMain();
		if (dialog.getCarteMain() != null)
			return carte;
		else return null;
		//dialog.setVisible(true);
		//if (dialog.getCarteMain() != null){
		//	BatailleControleur.jouer(joueur, dialog.getCarteMain());
		//	BatailleControleur.updateJTextArea(joueur.getNom() + " joue " + dialog.getCarteMain());
		//}
	}

	private static void jouer(Joueur joueur, Carte carteMain) {
		joueur.poserCarteUnique(carteMain);
		
	}

	public static void echange(Joueur joueur, Carte carteVisible, Carte carteMain) {
		joueur.echangerCarte(carteVisible, carteMain);
		
	}
	
	
	
	
	

}
