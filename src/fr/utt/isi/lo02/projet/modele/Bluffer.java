package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;

/** Chercher à se débarasser le plus vite possible de ses cartes */
public class Bluffer extends StrategieJeu {

	

	@Override
	/** Va chercher à créer des paires */
	public void echangerCartes(Joueur joueur) {
		boolean paire = joueur.peutFormerPaire();
		if (paire) {
			System.out.println(joueur.getNom() + " echange des cartes");
		} else {
			System.out.println(joueur.getNom() + " n'a pas juge necessaire d'echanger des cartes");
		}
		
	}

	@Override
	public Joueur choisirQuiRalentir() {
		// choisit le joueur 1 juste pour l'instant
		// TODO chznger
		
		return Bataille.getInstance().getJoueurs().get(0);
	}

	@Override
	public ArrayList<Carte> choisirCarteAJouer(Joueur joueur) {
		ArrayList<Carte> cartes = new ArrayList<>();
		Carte derniereCarteJouee = null, carte = null;
		
		// Si la table n'est pas vide, il va jouer en fonction de la dernière carte jouée
		if (!Bataille.getInstance().getTable().isEmpty()) {
			carte = joueur.getMain().getLast();
		} else {
			//Sinon il joue sa plus petite carte
			carte = joueur.getMain().getFirst();
		}
		cartes.add(carte);
		
		
		System.out.print(joueur.getNom() + " joue ");
		Iterator<Carte> it = cartes.iterator();
		while (it.hasNext()) {
			System.out.print(it.next());
		}
		System.out.println("");
		
		
		return cartes;
	}

	@Override
	public Carte choisirCarteContre(Joueur joueur) {
		//boolean deux = joueur.possede(0);
		//boolean as = joueur.possede(12);
		
		
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
