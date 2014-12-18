package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Interface Stratégie
 * 3 stratégies différentes
 * @author daussy
 *
 */
public abstract class StrategieJeu {
	
	public abstract void echangerCartes(Joueur joueur);
	
	public abstract Joueur choisirQuiRalentir();
	
	public abstract ArrayList<Carte> choisirCarteAJouer(Joueur joueur);

	public int jouerCarte(Joueur joueur) {
		//System.out.println(joueur);
		System.out.println(joueur);
		
		boolean jouerEstPossible  = joueur.peutJouer();
		int nb = 0;
		
		ArrayList<Carte> cartes = null;
		
		if (jouerEstPossible) {
			cartes = this.choisirCarteAJouer(joueur);
			int taille = cartes.size(); //nb de cartes à piocher
			Iterator<Carte> it = cartes.iterator();
			while (it.hasNext()) {
				joueur.poserCarteUnique(it.next());
			}
			boolean vide = Bataille.getInstance().getPioche().isEmpty();
			//Si la pioche n'est pas vide
			if (!vide) {
				// Pour chq carte posée
				for (int i = 0; i < taille; i++) {
					// On redonne au joueur une carte de la pioche
					if (!vide) {
						joueur.recevoirCarte(Bataille
								.getInstance()
								.getPioche()
								.get(Bataille.getInstance().getPioche().size() - 1));
						Bataille.getInstance()
								.getPioche()
								.remove(Bataille.getInstance().getPioche()
										.size() - 1);
					}
				}
			}
			nb = cartes.size();
			
		} else {
			joueur.ramasserTas();
			nb = -1;
		}
		
		boolean estMainVide = joueur.estMainVide();
		boolean estFaceUpVide = joueur.estFaceUpVide();
		boolean estFaceDownVide = joueur.estFaceDownVide();
		
		if (estMainVide && !estFaceUpVide) {
			joueur.remplirMainAvecFaceUp();
			System.out.println("La main de " + joueur.getNom() + " est vide, on prend les cartes visibles");
		}
		
		
		
		/**
		 * Lorsqu'un joueur n'a de nouveau plus de cartes en main,
		 * il pioche au hasard une des trois cartes retournées disposées
		 * devant lui. Si celle-ci convient, il la pose et peut en 
		 * prendre une autre qu'il posera à son tour si elle convient.
		 * S'il ne peut jouer cette carte, le joueur ramasse le tas et doit 
		 * éliminer les cartes qu'il a en main avant de pouvoir à nouveau
		 * ramasser une des cartes devant lui.
		 */
		if (estMainVide && estFaceUpVide && !estFaceDownVide) {
			// Vu qu'on prend aléatoirement on qu'on ne connait
			// pas les cartes, autant prendre la première
			// à chaque fois !
			joueur.addMain(joueur.getFaceDown().getFirst());
			joueur.getFaceDown().remove(0);
			System.out.println("La main de " + joueur.getNom() + " est vide, on prend une carte cachee");
			
			jouerEstPossible = joueur.peutJouer();
			
			while (jouerEstPossible && !estFaceDownVide) {
				System.out.println("il peut poser " + joueur.getMain().getFirst());
				joueur.poserCarteUnique(joueur.getMain().getFirst());
				joueur.addMain(joueur.getFaceDown().getFirst());
				joueur.getFaceDown().remove(0);
				estFaceDownVide = joueur.estFaceDownVide();
			}
			
			// Si jouer n'est plus possible
			// le joueur ramasse la pioche
			if (!jouerEstPossible) {
				System.out.println("Il ne peut pas jouer ! Il prend la pioche");
				nb = -1;
				
			}
		}
		
		/*
		if (estMainVide && estFaceUpVide && !estFaceDownVide) {
			joueur.remplirMainAvecFaceDown();
			System.out.println("La main de " + joueur.getNom() + " est vide, on prend les cartes cachees");

		}
		*/
			
		System.out.println(joueur);
		System.out.println("\n\n");
		return nb;
		
		
	}

	public abstract Carte choisirCarteContre(Joueur joueur);

	

	
	
}
