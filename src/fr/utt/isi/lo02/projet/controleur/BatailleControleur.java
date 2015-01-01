package fr.utt.isi.lo02.projet.controleur;

import java.util.ArrayList;

import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.Scrambler;
import fr.utt.isi.lo02.projet.modele.StrategieJeu;
import fr.utt.isi.lo02.projet.modele.StrategieRapide;
import fr.utt.isi.lo02.projet.vue.DialogChangerCartes;
import fr.utt.isi.lo02.projet.vue.DialogEnvoyerTasJoueur;
import fr.utt.isi.lo02.projet.vue.DialogJouerCarte;
import fr.utt.isi.lo02.projet.vue.FenetreInitialisation;
import fr.utt.isi.lo02.projet.vue.VueBataille;

/**
 * Controleur faisant le lien entre le Modèle et la Vue
 * @author daussy
 *
 */
public class BatailleControleur {

	/**
	 * Modèle
	 */
	private static Bataille bataille;
	
	/**
	 * Vue
	 */
	private static VueBataille vueBataille;
	
	/**
	 * Constructeur
	 * @param bataille modèle
	 * @param vueBataille vue
	 */
	public BatailleControleur(Bataille bataille,VueBataille vueBataille) {
		setBataille(bataille);
		setVueBataille(vueBataille);
	}
	
	/**
	 * Permet d'ajouter une carte au tas dans la vue
	 * @param carte Carte à ajouter sur le tas
	 */
	public void changerTas(Carte carte) {
		getVueBataille().majTas(carte);
	}
	
	/**
	 * Permet de mettre à jour la JTextArea
	 * servant à garder l'avancement du jeu
	 * @param message Log à ajouter
	 */
	public static void updateJTextArea(String message) {
		getVueBataille().getLog().append(message + "\n");
		//Pour placer le curseur à la fin, afin d'émuler un scroll automatique
		getVueBataille().getLog().setCaretPosition(getVueBataille().getLog().getDocument().getLength());
	}

	/**
	 * Permet d'afficher un JDialog permettant au joueur
	 * d'échanger ses cartes
	 * @param joueur Joueur qui aura la possibilité d'échanger ses cartes
	 */
	public static void fenetreChoixEchangeJoueurReel(Joueur joueur) {
		 DialogChangerCartes dialog = new DialogChangerCartes(null, "Echange des cartes de " + joueur.getNom(), true, joueur);
		 dialog.setVisible(true);
		 //Si les données retournées par la fenêtre sont bonnes, on appelle la méthode d'échange des cartes
		 if (dialog.getCartesMain().size() != 0 && dialog.getCartesVisibles().size() == dialog.getCartesMain().size()  ) {
			 BatailleControleur.echange(dialog.getJoueur(), dialog.getCartesMain(), dialog.getCartesVisibles());
		}
			 
	}
	
	/**
	 * Permet d'afficher un JDialog permettant au joueur
	 * de choisir un victime à qui envoyer le tas
	 * suite à la pose d'un As
	 * @param joueur Joueur qui doit choisir une victime
	 * @return joueur à victimiser
	 */
	public static Joueur fenetreChoixVictimeAs(Joueur joueur) {
		DialogEnvoyerTasJoueur dialog = new DialogEnvoyerTasJoueur(null, "Qui sera la vicime ?", true, joueur);
		dialog.setVisible(true);
		Joueur victime = dialog.getVictime();
		if (victime != null) 
			return victime;
		else return null;
	}
	
	/**
	 * Permet d'afficher un JPanel permettant au joueur
	 * de choisir la ou les cartes qu'il souhaite jouer
	 * @param joueur Joueur qui va jouer
	 * @return la liste des cartes qu'il va jouer
	 */
	public static ArrayList<Carte> fenetreChoixCarteJouer(Joueur joueur) {
		DialogJouerCarte dialog = new DialogJouerCarte(null, "Quelle carte jouer ?", true, joueur);
		dialog.setVisible(true);
		ArrayList<Carte> cartes = dialog.getCartes();
		// Si l'utilisaeur a bien selectionné des cartes dans la JDialog on les retournes
		if (cartes.size() != 0)
			return cartes;
		else return null;
	}


	/**
	 * Permet de procéder à l'échanger des cartes du joueur réel
	 * en début de  partie
	 * @param joueur Joueur réel procédant à l'échange
	 * @param carteVisible cartes qu'il possède devant lui qu'il souhaite échanger
	 * @param carteMain cartes qu'il a en main qu'il souhaite échanger
	 */
	public static void echange(Joueur joueur, ArrayList<Carte> carteVisible, ArrayList<Carte> carteMain) {
		Carte v, m;
		int size = carteVisible.size();
		for (int j = 0; j < size; j++) {
			v = carteVisible.get(j);
			m = carteMain.get(j);
			//Met à jour à la vue
			BatailleControleur.updateJTextArea(joueur.getNom() + " échange " +  m + " et " + v + ".");
			//Et le modèle
			joueur.echangerCarte(v, m);
		}
	}

	
	/**
	 * Permet d'afficher la fenêtre demandant à l'utilisateur le nom
	 * qu'il souhaite utiliser et le nombre d'adversaires qu'il souhaite affronter
	 */
	public static void initialisationPartie() {
		FenetreInitialisation dialog = new FenetreInitialisation(null, "Qui joue ?", true);
		dialog.setVisible(true);
		int nb = dialog.getNb();
		String name = dialog.getNom(), n = "";
		Scrambler j = new Scrambler(name);
		StrategieJeu strat;
		Bataille.getInstance().addJoueur(j);
		int taille = Bataille.getComputers().length;
		
		//Pour tous les joueurs virtuels
		//on va leur attribuer un nom et une stratégie
		//et les ajouter à la bataille
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
		//Le Scrambler mélanger et distribue les cartes
		j.distribuerPaquet();
		
		
		
		
	}

	/**
	 * Getter de bataille
	 * @return la bataille
	 */
	public static Bataille getBataille() {
		return bataille;
	}
	
	/**
	 * Setter de bataille
	 * @param bataille
	 */
	public static void setBataille(Bataille bataille) {
		BatailleControleur.bataille = bataille;
	}
	
	/**
	 * Getter de la vue Bataille
	 * @return la vueBataille
	 */
	public static VueBataille getVueBataille() {
		return vueBataille;
	}

	/**
	 * Setter de vue Bataille
	 * @param vueBataille
	 */
	public static void setVueBataille(VueBataille vueBataille) {
		BatailleControleur.vueBataille = vueBataille;
	}
	
	
	
	
	
	

}
