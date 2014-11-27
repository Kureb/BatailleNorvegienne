package fr.utt.isi.lo02.projet.modele;
import java.util.ArrayList;


public class Bataille {

	/** Compte le nombre de joueurs ajoutés à la partie */
	public static int NB_JOUEURS = 0;
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
	 * @param j le joueur à ajouter
	 */
	public void addJoueur(Joueur j) {
		//On l'ajoute seulement s'il n'y est pas déjà
		if (!this.joueurs.contains(j)) {
			this.joueurs.add(j);
			this.NB_JOUEURS++;
		}
			
	}
	
	/**
	 * Permet de supprimer un joueur de la partie
	 * @param j le joueur à spupprimer
	 */
	public void removeJoueur(Joueur j) {
		//On le supprime seulement s'il existe
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
	
	
	
	
}
