package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Cherche à se débarasser le plus vite possible de ses cartes 
 * @author daussy
 *
 */
public class StrategieRapide extends StrategieJeu {

	

	@Override
	/** Va chercher à créer des paires */
	public void echangerCartes(Joueur joueur) {
		boolean paire = joueur.peutFormerPaire();
		String message = "";
		if (paire) {
			Carte carteUp = joueur.getCarteUpPourFormerPaire();
			Carte carteMain = joueur.getCarteMainSacrificePourFormerPaire(carteUp);
			joueur.echangerCarte(carteMain, carteUp);
			message = joueur.getNom() + " a echangé " + carteMain + " VS " + carteUp + " afin d'avoir une paire.";
		} else {
			message = joueur.getNom() + " n'a pas jugé nécessaire d'échanger des cartes.";
		}
		
		setChanged();
		notifyObservers(message);
		
		
	}

	@Override
	public Joueur choisirQuiRalentir(Joueur joueurActuel) {
		// choisit le joueur suivant simplement..
		// bluffer n'est pas le plus intelligent
		return Bataille.getInstance().JoueurSuivantCarteNormale(joueurActuel);
	}

	@Override
	public ArrayList<Carte> choisirCarteAJouer(Joueur joueur) {
		ArrayList<Carte> cartes = new ArrayList<>();
		@SuppressWarnings("unused")
		Carte derniereCarteJouee = null, carte = null;
		// Si la table n'est pas vide, il va jouer en fonction de la dernière carte jouée
		if (!Bataille.getInstance().getTable().isEmpty()) {
			int index = Bataille.getInstance().getTable().size()-1;
			carte = joueur.getPlusPetite(Bataille.getInstance().getTable().get(index));
		} else {
			//Sinon il joue sa plus petite carte
			carte = joueur.getPlusPetite();
			
		}
		cartes.add(carte);
		
		
		LinkedList<Carte> copyOfMain = new LinkedList<>(joueur.getMain());
		copyOfMain.remove(cartes.get(0));
		
		Iterator<Carte> it = copyOfMain.iterator();
		while(it.hasNext()) {
			boolean autre = false;
			Carte carteCourante = it.next();
			autre = (cartes.get(0)).aMemeValeur(carteCourante);
			if (autre) {
				cartes.add(carteCourante);
				
				
			}
		}
		
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
	public Carte choisirCarteContre(Joueur joueur) {
		Iterator<Carte> it = joueur.getMain().iterator();
		while (it.hasNext()) {
			Carte carte = (Carte) it.next();
			if (carte.getValeur() == 12) return carte;
		}
		
		Iterator<Carte> itt = joueur.getMain().iterator();
		while (itt.hasNext()) {
			Carte carte = (Carte) itt.next();
			if (carte.getValeur() == 0) return carte;
		}
		
		return null;
		
		
	}

	

}
