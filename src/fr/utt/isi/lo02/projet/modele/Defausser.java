package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;

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
			//Sinon il joue sa plus petite carte TODO bug
			carte = joueur.getPlusPetite();
		}
		cartes.add(carte);
		
		System.out.print(joueur.getNom() + " joue ");
		Iterator<Carte> it = cartes.iterator();
		while (it.hasNext()) {
			System.out.print(it.next());
		}System.out.println("");
		
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
	public Joueur choisirQuiRalentir() {
		// choisit le joueur 1 juste pour l'instant
		// TODO chznger
		return Bataille.getInstance().getJoueurs().get(0);
	}

}
