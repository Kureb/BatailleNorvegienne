package fr.utt.isi.lo02.projet.modele;

import java.util.Collections;
import java.util.LinkedList;

public class JeuDeCartes {

	private LinkedList<Carte> tas;
	
	/**
	 * Construction d'un jeu de carte
	 */
	public JeuDeCartes() {
		tas = new LinkedList<>();
		int nbCartesNecessaires = (Joueur.NB_JOUEURS * 9) + 7;//tjrs avoir 7 dans la pioche
		double nbPaquetsNecessaires = nbCartesNecessaires / 52.0; //récupérer entier supérieur
		int nbPaquet =  (int) Math.ceil(nbPaquetsNecessaires);
		System.out.println("nb paquets : " + nbPaquet);
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


	public int size() {
		return tas.size();
	}


	public LinkedList<Carte> getTas() {
		return tas;
	}


	public void setTas(LinkedList<Carte> tas) {
		this.tas = tas;
	}
	
	
	
	
}
