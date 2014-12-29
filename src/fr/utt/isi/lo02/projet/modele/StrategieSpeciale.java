package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/* Joue en priorité les cartes spéciales **/
public class StrategieSpeciale extends StrategieJeu {

	public ArrayList<Carte> choisirCarteAJouer(Joueur joueur) {
		ArrayList<Carte> cartes = new ArrayList<>();
		Carte derniereCarteJouee = null, carte = null;
		
		// Si la table n'est pas vide, il va jouer en fonction de la dernière carte jouée
		if (!Bataille.getInstance().getTable().isEmpty()) {
			int indexDernierElement = Bataille.getInstance().getTable().size()-1;
			derniereCarteJouee = Bataille.getInstance().getTable().get(indexDernierElement);
			// S'il a une carte spéciale il la joue
			if (joueur.possedeCarteSpeciales())
				carte = joueur.getCarteSpecialeMain();
			//Sinon il joue la plus petite carte jouable
			else
				carte = joueur.getPlusPetite(derniereCarteJouee);
		} else {
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
		
		String message;
		message = joueur.getNom() + " joue ";
		//System.out.print(joueur.getNom() + " joue ");
		Iterator<Carte> itt = cartes.iterator();
		while (itt.hasNext()) {
			message += itt.next() + " ";
			//System.out.print(itt.next() + " ");
		}
		message += ".";
		System.out.println(message);
		
		setChanged();
		notifyObservers(message);
		
		
		return cartes;
	}

	@Override
	public void echangerCartes(Joueur joueur) {
		boolean spec = joueur.possedeCarteSpeciales();
		boolean mainNormal = joueur.possedeCarteNormale();
		String message = "";
		//int compteur = 0;
		// Logiquement ça ne passera pas plus de 3 fois
		if (spec && mainNormal) {
			while (spec && mainNormal) {
				Carte carteSpec = joueur.getCarteSpecialeFaceUp();
				Carte main = joueur.getCarteNormalMain();
				message = (joueur.getNom() + " a sacrifié " + main + " pour avoir " + carteSpec + "."); 
						
				System.out.println(message.replace("é", "e"));
				
				joueur.echangerCarte(main, carteSpec);
				//System.out.println(joueur);
				spec = joueur.possedeCarteSpeciales();
				mainNormal = joueur.possedeCarteNormale();
				//compteur++;
			}
			
		} else {
			message = joueur.getNom() + " n'a pas jugé utile d'échanger ses cartes.";
			System.out.println(message.replace("é", "e"));
		}
		
		setChanged();
		notifyObservers(message);
			
			
	}

	@Override
	public Joueur choisirQuiRalentir(Joueur joueurActuel) {
		// doit choisir le joueur avec le moins de cartes
		// sans se choisir soit-même forcément
		Iterator<Joueur> it = Bataille.getInstance().getJoueurs().iterator();
		int nbCarteMain = Bataille.getInstance().getJoueurs().size() * 9 + 7; //Au max un joueur a toutes les cartes..
		int pos=0; 
		while (it.hasNext()) {
			Joueur joueur = (Joueur) it.next();
			if (joueur != joueurActuel) {
				if (joueur.getMain().size() < nbCarteMain)
					nbCarteMain = joueur.getMain().size();
			}
			pos++;
		}
		
		return Bataille.getInstance().getJoueurs().get(pos-1);
	}

	@Override
	//Va choisir le 2 en priorité
	public Carte choisirCarteContre(Joueur joueur) {
		
		
		Iterator<Carte> it = joueur.getMain().iterator();
		while (it.hasNext()) {
			Carte carte = (Carte) it.next();
			if (carte.getValeur() == 0) return carte;
		}
		
		Iterator<Carte> itt = joueur.getMain().iterator();
		while (itt.hasNext()) {
			Carte carte = (Carte) itt.next();
			if (carte.getValeur() == 12) return carte;
		}
		
		return null;
		
		
	}

}
