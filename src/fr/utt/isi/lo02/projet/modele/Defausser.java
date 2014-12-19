package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/* Joue en priorité les cartes spéciales **/
public class Defausser extends StrategieJeu {

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
		
		
		System.out.print(joueur.getNom() + " joue ");
		Iterator<Carte> itt = cartes.iterator();
		while (itt.hasNext()) {
			System.out.print(itt.next() + " ");
		}
		System.out.println("");
		
		
		return cartes;
	}

	@Override
	public void echangerCartes(Joueur joueur) {
		boolean spec = joueur.possedeCarteSpeciales();
		boolean mainNormal = joueur.possedeCarteNormale();
		//int compteur = 0;
		// Logiquement ça ne passera pas plus de 3 fois
		if (spec && mainNormal) {
			while (spec && mainNormal) {
				Carte carteSpec = joueur.getCarteSpecialeFaceUp();
				Carte main = joueur.getCarteNormalMain();
				System.out.println(joueur.getNom() + "(carte spec/defausser) echange des cartes\n" 
													+ "Maintenant elle a\n" 
													+ "\t en main : " + carteSpec
													+ "\n\t face visible : " + main);
				joueur.echangerCarte(main, carteSpec);
				//System.out.println(joueur);
				spec = joueur.possedeCarteSpeciales();
				mainNormal = joueur.possedeCarteNormale();
				//compteur++;
			}
			
		} else
			System.out.println(joueur.getNom() + " ne juge pas utile d'echanger des cartes");
			
	}

	@Override
	public Joueur choisirQuiRalentir(Joueur joueurActuel) {
		// doit choisir le joueur avec le moins de cartes
		// sans se choisir soit-même forcément
		Iterator<Joueur> it = Bataille.getInstance().getJoueurs().iterator();
		int nbCarteMain = 100000, pos=0; //TODO dégueu donc trouve autre chose
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
		//boolean deux = joueur.possede(0);
		//boolean as = joueur.possede(12);
		
		
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
