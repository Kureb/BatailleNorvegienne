package fr.utt.isi.lo02.projet.controleur;

import java.util.ArrayList;
import java.util.Iterator;

import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.Scrambler;
import fr.utt.isi.lo02.projet.modele.StrategieJeu;
import fr.utt.isi.lo02.projet.modele.StrategieRapide;
import fr.utt.isi.lo02.projet.modele.StrategieRelle;
import fr.utt.isi.lo02.projet.vue.DialogChangerCartes;
import fr.utt.isi.lo02.projet.vue.DialogEnvoyerTasJoueur;
import fr.utt.isi.lo02.projet.vue.DialogJouerCarte;
import fr.utt.isi.lo02.projet.vue.FenetreInitialisation;
import fr.utt.isi.lo02.projet.vue.VueBataille;

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
		 if (dialog.getCartesMain().size() != 0 && dialog.getCartesVisibles().size() == dialog.getCartesMain().size()  ) {
			 BatailleControleur.echange(dialog.getJoueur(), dialog.getCartesMain(), dialog.getCartesVisibles());
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
	
	
	public static ArrayList<Carte> fenetreChoixCarteJouer(Joueur joueur) {
		
		DialogJouerCarte dialog = new DialogJouerCarte(null, "Quelle carte jouer ?", true, joueur);
		//Carte carte = DialogJouerCarte.openForm(null, joueur);
		dialog.setVisible(true);
		ArrayList<Carte> cartes = dialog.getCartes();
		if (cartes.size() != 0)
			return cartes;
		else return null;
		//dialog.setVisible(true);
		//if (dialog.getCarteMain() != null){
		//	BatailleControleur.jouer(joueur, dialog.getCarteMain());
		//	BatailleControleur.updateJTextArea(joueur.getNom() + " joue " + dialog.getCarteMain());
		//}
	}



	public static void echange(Joueur joueur, ArrayList<Carte> carteVisible, ArrayList<Carte> carteMain) {
		Carte v, m;
		int size = carteVisible.size();
		for (int j = 0; j < size; j++) {
			v = carteVisible.get(j);
			m = carteMain.get(j);
			BatailleControleur.updateJTextArea(joueur.getNom() + " échange " +  m + " et " + v + ".");
			joueur.echangerCarte(v, m);

		}
		
		
	}

	public static void initialisationPartie() {
		FenetreInitialisation dialog = new FenetreInitialisation(null, "Qui joue ?", true);
		dialog.setVisible(true);
		int nb = dialog.getNb();
		String name = dialog.getNom(), n = "";
		Scrambler j = new Scrambler(name);
		StrategieJeu strat;
		Bataille.getInstance().addJoueur(j);
		int taille = Bataille.getComputers().length;
		for (int i = 0; i < nb; i++) {
			if (i % 2 == 0) strat = new StrategieRapide();
			else strat = new StrategieRapide();
			
			if (i >= taille-1)
				n = Bataille.getComputers()[i%taille] + "_" + i;
			else
				n = Bataille.getComputers()[i];
			
			
			Joueur joueur = new Joueur(n, strat);
			Bataille.getInstance().addJoueur(joueur);
		}
		
		j.distribuerPaquet(Bataille.getInstance());
		
		
		
		
	}
	
	
	
	
	
	

}
