package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import fr.utt.isi.lo02.projet.controleur.BatailleControleur;


/**
 * Permet de définir comment va jouer un joueur réel
 * @author daussy
 *
 */
public class StrategieRelle extends StrategieJeu {

		
	

	/**
	 * Permet au joueur réel de choisir la carte qu'il veut jouer
	 * @param joueur Joueur courant (réel)
	 * @return Carte qu'il souhaite jouer
	 */
	public ArrayList<Carte> choisirCarteAJouer(Joueur joueur) {
		ArrayList<Carte> cartes = new ArrayList<>();
		cartes = BatailleControleur.fenetreChoixCarteJouer(joueur);
		String message = joueur.getNom() + " joue ";
		Iterator<Carte> itt = cartes.iterator();
		while (itt.hasNext()) {
			message += itt.next() + " ";
		}
		
		setChanged();
		notifyObservers(message);
		
		
		return cartes;
		
		
	}

	
	
	@Override
	public void echangerCartes(Joueur joueur) {
		joueur.proposerChangerCartes();
	}

	@Override
	public Joueur choisirQuiRalentir(Joueur joueurActuel) {
		Joueur joueur = BatailleControleur.fenetreChoixVictimeAs(joueurActuel);
		return joueur;
		
	}



	@Override
	// Donc il a un As ou un 2 ou les 2
	//Va choisir l'As en priorité et l'envoyer
	//au joueur possédant le moins de cartes en main
	public Carte choisirCarteContre(Joueur joueur) {
		//On appelle la fenetre de choix de carte
		//Car de toute façon une vérif est faite sur la faisabilité du mouvement
		ArrayList<Carte> cartes = BatailleControleur.fenetreChoixCarteJouer(joueur);
		return cartes.get(0);
	}

}
