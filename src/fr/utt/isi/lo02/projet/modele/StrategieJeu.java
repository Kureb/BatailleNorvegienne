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
		boolean jouerEstPossible  = joueur.peutJouer();
		
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
			return cartes.size();
			
		} else {
			joueur.ramasserTas();
			return -1;
		}
		
		
		
		
	}

	public abstract Carte choisirCarteContre(Joueur joueur);

	

	
	
}
