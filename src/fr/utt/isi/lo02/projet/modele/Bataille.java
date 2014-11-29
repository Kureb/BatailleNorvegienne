package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Bataille {

	/** Liste des joueurs à batailler */
	private ArrayList<Joueur> joueurs;
	/** jeu de cartes */
	private JeuDeCartes jeuDeCartes;
	/** la pioche du jeu */
	private ArrayList<Carte> pioche;

	/**
	 * Constructeur d'une bataille
	 */
	public Bataille() {
		joueurs = new ArrayList<>();
		jeuDeCartes = new JeuDeCartes();
		pioche = new ArrayList<>();
	}

	public ArrayList<Carte> getPioche() {
		return pioche;
	}

	public void setPioche(ArrayList<Carte> pioche) {
		this.pioche = pioche;
	}

	/**
	 * Permet d'ajouter un joueur à la partie
	 * 
	 * @param j
	 *            le joueur à ajouter
	 */
	public void addJoueur(Joueur j) {
		// On l'ajoute seulement s'il n'y est pas déjà
		if (!this.joueurs.contains(j)) {
			this.joueurs.add(j);
		}

	}

	/**
	 * Permet de supprimer un joueur de la partie
	 * 
	 * @param j
	 *            le joueur à spupprimer
	 */
	public void removeJoueur(Joueur j) {
		// On le supprime seulement s'il existe
		if (this.joueurs.contains(j))
			this.joueurs.remove(j);
	}

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public JeuDeCartes getJeuDeCartes() {
		return jeuDeCartes;
	}

	public void setJeuDeCartes(JeuDeCartes jeuDeCartes) {
		this.jeuDeCartes = jeuDeCartes;
	}

	public String toString() {
		return joueurs.toString();
	}

	/**
	 * Demande à tous les joueurs s'ils veulent échanger leurs cartes L'échange
	 * est possible seulement entre les cartes que le joueur possède en main et
	 * les cartes visibles Si le joueur répond ne pas vouloir faire d'échange on
	 * passe au joueur suivant Si le joueur répond vouloir faire un échange, on
	 * lui propose l'échange et on lui redemande s'il souhaite faire un échange
	 * jusqu'à ce qu'il dise non (considéré comme définitif)
	 */
	public void echangerCartes() {
		Iterator<Joueur> it = this.joueurs.iterator();
		int rep;
		Scanner sc = new Scanner(System.in);

		// Pour tous les joueurs
		while (it.hasNext()) {
			Joueur joueur = it.next();
			rep = -1; // pour repasser dans la boucle

			// Tant qu'ils n'ont pas répondu 'oui' ou 'non'
			do {
				// Tant qu'ils n'ont pas dit 'non'
				do {
					System.out.println("Veux-tu echanger des cartes "
							+ joueur.getNom() + " ?\t(1 (oui)/ 2 (non))");
					rep = sc.nextInt();
					if (rep == 1)
						joueur.proposerChangerCartes(); // Echange de cartes si
														// réponse positive
				} while (rep != 2);

			} while (rep != 1 && rep != 2);
		}
	}
	
	
	
	/**
	 * Permet de connaitre le joueur qui va commencer la partie D'après les
	 * règles de la bataille Norvégienne le joueur à commencer Et le joueur à la
	 * gauche du donner C'est-à-dire qu'on tourne dans le sens horaires des
	 * aiguilles d'une montre C'est donc le joueur qui suit le Mélangeur qui va
	 * commencer la partie
	 * 
	 * @return
	 */
	public Joueur getJoueurQuiJoueEnPremier() {
		Iterator<Joueur> it = joueurs.iterator();
		int i = 0;
		int positionScrambler = 0;
		while (it.hasNext()) {
			i++;
			if (it.next() instanceof Scrambler) {
				positionScrambler = i;
				break; // pas beau, TODO : trouver qqch d'autre..
			} else {
				// Exception genre y'a pas de Scrambler ?
				// TODO : compléter
			}

		}
		// Si le Scrambler est le dernier joueur, on retourne alors le premier
		// de la liste
		// sinon on retourne simplement le joueur d'après
		return ((positionScrambler == joueurs.size() ? joueurs.get(0) : joueurs
				.get(positionScrambler++)));

	}
	
	
	

}
