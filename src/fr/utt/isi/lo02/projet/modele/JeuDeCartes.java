package fr.utt.isi.lo02.projet.modele;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Représente le jeu de carte (qui contient au minimum 52 cartes)
 * @author daussy
 *
 */
public class JeuDeCartes {

	/**
	 * Cartes constituant le jeu de cartes
	 */
	private LinkedList<Carte> tas;
	
	/**
	 * Construction d'un jeu de carte
	 */
	public JeuDeCartes() {
		tas = new LinkedList<>();
		int nbCartesNecessaires = (Joueur.NB_JOUEURS * 9) + 7;//tjrs avoir 7 dans la pioche
		double nbPaquetsNecessaires = nbCartesNecessaires / 52.0; 
		int nbPaquet =  (int) Math.ceil(nbPaquetsNecessaires);//arrondi à l'entier supérieur
		
		for (int i = 0; i < nbPaquet; i++) {
			//On parcourt toutes les couleurs possibles, puis toutes les valeurs possibles
			//C'est-à-dire toutes les cartes possibles, on les instancie et on
			//les ajoute au tas de cartes.
			for (int couleur = Carte.PIC; couleur <= Carte.TREFLE; couleur++){
				for (int valeur = Carte.DEUX; valeur <= Carte.AS; valeur++){
					tas.add(new Carte(valeur, couleur));
				}
			}

		}
	}
	
	
	/**
	 * Permet de piocher la carte du dessus
	 * @return carte du dessus du paquet
	 */
	public Carte tirerCarteDessus() {
		return tas.isEmpty() ? null : tas.pop();
	}
	
	
	/**
	 * Mélange le paquet de cartes
	 */
	public void melanger() {
		Collections.shuffle(tas);
	}
	
	
	/**
	 * Permet de savoir si le paquet est vide ou non 
	 * (utile lors de la distribution)
	 * @return true si paquet vide; false sinon
	 */
	public boolean estVide() {
		return tas.isEmpty();
	}

	/**
	 * Donne la taille du paquet
	 * @return taille du jeu de cartes
	 */
	public int size() {
		return tas.size();
	}

	/**
	 * Permet d'avoir le jeu de carte
	 * @return le jeu de carte
	 */
	public LinkedList<Carte> getTas() {
		return tas;
	}

	/**
	 * Setter du jeu de carte
	 * @param tas liste de cartes à implémenter
	 */
	public void setTas(LinkedList<Carte> tas) {
		this.tas = tas;
	}
	
	
	
	
}
