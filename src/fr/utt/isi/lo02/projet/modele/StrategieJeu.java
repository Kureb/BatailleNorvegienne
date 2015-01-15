package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

/**
 * Interface Stratégie
 * 3 stratégies différentes
 * @author daussy
 *
 */
public abstract class StrategieJeu extends Observable{
	
	/**
	 * Donne la possibilité d'échanger des cartes
	 * @param joueur qui joue
	 */
	public abstract void echangerCartes(Joueur joueur);
	
	/**
	 * Permet de choisir le joueur à ralentir lors de la pause d'un As
	 * @param joueur qui joue
	 * @return
	 */
	public abstract Joueur choisirQuiRalentir(Joueur joueur);
	
	/**
	 * Permet de choisir la carte qu'il va utiliser pour contrer un As
	 * @param joueur qui joue
	 * @return Carte à jouer
	 */
	public abstract Carte choisirCarteContre(Joueur joueur);
	
	/**
	 * Permet de choisir une ou plusieurs cartes à jouer
	 * @param joueur qui joue
	 * @return liste des cartes à jouer
	 */
	public abstract ArrayList<Carte> choisirCarteAJouer(Joueur joueur);

	/**
	 * Permet au joueur de jouer une ou plusieurs cartes
	 * @param joueur qui joue
	 * @return le nombre de cartes posées
	 */
	public int jouerCarte(Joueur joueur) {
		boolean estFaceUpVide = joueur.estFaceUpVide();
		boolean estFaceDownVide = joueur.estFaceDownVide();
		boolean estMainVide = joueur.estMainVide();
		
		//Si la main est vide mais pas faceup, il récupère ses cartes
		if (estMainVide && !estFaceUpVide) {
			joueur.remplirMainAvecFaceUp();
			String message = "La main de " + joueur.getNom()
					+ " est vide, il prend les cartes visibles.";
			
			setChanged();
			notifyObservers(message);
			
			setChanged();
			notifyObservers(joueur);
		}

		estMainVide = joueur.estMainVide();
		boolean jouerEstPossible = false;
		int nb = 0;
		//si sa main n'est pas vide
		if (!estMainVide) {
			jouerEstPossible = joueur.peutJouer();
			

			ArrayList<Carte> cartes = null;
			//Si il a la possibilité de jouer
			if (jouerEstPossible) {
				cartes = this.choisirCarteAJouer(joueur); //Il choisit une ou plusieurs cartes
				int taille = cartes.size(); // nb de cartes à piocher
				Iterator<Carte> it = cartes.iterator();
				while (it.hasNext()) {
					joueur.poserCarteUnique(it.next());// Il les poses
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
				//S'il n'a pas pu jouer il ramasse le tas
				joueur.ramasserTas();
				nb = -1;
			}
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
			joueur.addMain(joueur.getFaceDown().getFirst());
			joueur.getFaceDown().remove(0);
			String message = "La main de " + joueur.getNom() + " est vide, il prend une carte cachée " + joueur.getMain().getFirst();
			
			setChanged();
			notifyObservers(message);
			
			
			jouerEstPossible = joueur.peutJouer();
			
			while (jouerEstPossible && !estFaceDownVide) {
				message = joueur.getNom() + " pose " + joueur.getMain().getFirst();
				
				setChanged();
				notifyObservers(message);
				
				joueur.poserCarteUnique(joueur.getMain().getFirst());
				
				
				
				estFaceDownVide = joueur.estFaceDownVide();
				if (estFaceDownVide) break;
				
				
				//Il a pu poesr toutes les cartes à la suite
				joueur.addMain(joueur.getFaceDown().getFirst());
				joueur.getFaceDown().remove(0); 
				
				message = "La main de " + joueur.getNom() + " est vide, il prend une carte cachée " + joueur.getMain().getFirst();
				
				
				setChanged();
				notifyObservers(message);
				
				jouerEstPossible = joueur.peutJouer();
			}
			
			// Si jouer n'est plus possible
			// le joueur ramasse la pioche
			if (!jouerEstPossible) {
				message = joueur.getNom() + " ne peut pas jouer ! Il prend la pioche";
				
				setChanged();
				notifyObservers(message);
				
				if (!Bataille.getInstance().getTable().isEmpty())
					joueur.ramasserTas();
				nb = -1;
				
			}
		}
		
		
		setChanged();
		notifyObservers(this);
		
		return nb;
		
		
	}

	

	

	
	
}
