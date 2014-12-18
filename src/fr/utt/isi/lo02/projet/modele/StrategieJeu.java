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
			
		
		
		if (estMainVide && estFaceUpVide && !estFaceDownVide) {
			joueur.remplirMainAvecFaceDown();
			System.out.println("La main de " + joueur.getNom() + " est vide, on prend les cartes cachees");

		}
			
		System.out.println(joueur);
		System.out.println("\n\n");
		return nb;
		
		
	}

	public abstract Carte choisirCarteContre(Joueur joueur);

	

	
	
}
