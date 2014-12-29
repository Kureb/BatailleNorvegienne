package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Interface Stratégie
 * 3 stratégies différentes
 * @author daussy
 *
 */
public abstract class StrategieJeu extends BatailleAbstraite{
	
	public abstract void echangerCartes(Joueur joueur);
	
	public abstract Joueur choisirQuiRalentir(Joueur joueur);
	
	public abstract ArrayList<Carte> choisirCarteAJouer(Joueur joueur);

	public int jouerCarte(Joueur joueur) {
		//System.out.println(joueur);
		System.out.println(joueur);
		boolean estMainVide = joueur.estMainVide();
		boolean jouerEstPossible = false;
		int nb = 0;
		if (!estMainVide) {
			jouerEstPossible = joueur.peutJouer();
			

			ArrayList<Carte> cartes = null;

			if (jouerEstPossible) {
				cartes = this.choisirCarteAJouer(joueur);
				int taille = cartes.size(); // nb de cartes à piocher
				Iterator<Carte> it = cartes.iterator();
				while (it.hasNext()) {
					joueur.poserCarteUnique(it.next());
				}
				boolean vide = Bataille.getInstance().getPioche().isEmpty();
				// Si la pioche n'est pas vide
				if (!vide) {
					// Pour chq carte posée
					for (int i = 0; i < taille; i++) {
						// On redonne au joueur une carte de la pioche
						if (!vide) {
							joueur.recevoirCarte(Bataille
									.getInstance()
									.getPioche()
									.get(Bataille.getInstance().getPioche()
											.size() - 1));
							Bataille.getInstance()
									.getPioche()
									.remove(Bataille.getInstance().getPioche()
											.size() - 1);
						}
						vide = Bataille.getInstance().getPioche().isEmpty();
						if (vide) Bataille.getInstance().clearPioche();
					}
				} 
				nb = cartes.size();

			} else {
				joueur.ramasserTas();
				nb = -1;
			}
		}
		
		//boolean estMainVide = joueur.estMainVide();
		boolean estFaceUpVide = joueur.estFaceUpVide();
		boolean estFaceDownVide = joueur.estFaceDownVide();
		
		
		if (estMainVide && !estFaceUpVide) {
			joueur.remplirMainAvecFaceUp();
			String message = "La main de " + joueur.getNom() + " est vide, il prend les cartes visibles.";
			System.out.println(message);
			
			setChanged();
			notifyObservers();
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
			//TODO si un As est joué dans la boucle, ça ne sera pas pris en compte
			//dire que c'est normal ? S'il peut jouer après pourquoi pas ..
			
			joueur.addMain(joueur.getFaceDown().getFirst());
			joueur.getFaceDown().remove(0);
			String message = "La main de " + joueur.getNom() + " est vide, il prend une carte cachée " + joueur.getMain().getFirst();
			System.out.println(message);
			
			setChanged();
			notifyObservers(message);
			
			
			jouerEstPossible = joueur.peutJouer();
			
			while (jouerEstPossible && !estFaceDownVide) {
				message = joueur.getNom() + " pose " + joueur.getMain().getFirst();
				
				setChanged();
				notifyObservers(message);
				
				System.out.println(message);
				joueur.poserCarteUnique(joueur.getMain().getFirst());
				
				
				
				estFaceDownVide = joueur.estFaceDownVide();
				if (estFaceDownVide) break;
				
				
				//Il a pu posr toutes les cartes à la suite
				joueur.addMain(joueur.getFaceDown().getFirst());
				joueur.getFaceDown().remove(0); 
				
				message = "La main de " + joueur.getNom() + " est vide, il prend une carte cachée " + joueur.getMain().getFirst();
				System.out.println(message);
				
				
				setChanged();
				notifyObservers(message);
				
				jouerEstPossible = joueur.peutJouer();
			}
			
			// Si jouer n'est plus possible
			// le joueur ramasse la pioche
			if (!jouerEstPossible) {
				message = joueur.getNom() + " ne peut pas jouer ! Il prend la pioche";
				System.out.println(message);
				
				setChanged();
				notifyObservers(message);
				
				if (!Bataille.getInstance().getTable().isEmpty())
					joueur.ramasserTas();
				nb = -1;
				
			}
		}
		
		
			
		System.out.println(joueur);
		System.out.println("\n\n");
		
		setChanged();
		notifyObservers(this);
		
		return nb;
		
		
	}

	public abstract Carte choisirCarteContre(Joueur joueur);

	

	
	
}
