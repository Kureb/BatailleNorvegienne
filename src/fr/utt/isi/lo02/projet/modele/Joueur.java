package fr.utt.isi.lo02.projet.modele;

import java.util.LinkedList;

/**
 * Représente le Joueur
 * @author daussy - obeidat
 *
 */
public class Joueur {
	
	/** Le nom du joueur */
	private String nom;
	/** Les cartes faces visibles du joueur */
	private final LinkedList<Carte> faceUp;
	/** Les cartes faces cachées du joueur */
	private final LinkedList<Carte> faceDown;
	/** Les cartes en main du joueur */
	private final LinkedList<Carte> main;
	/** La stratégie adoptée par le joueur */
	private final StrategieJeu strategie;
	
	
	/**
	 * Constructeur du joueur
	 * @param nom Nom du joueur
	 */
	public Joueur(String nom){
		this.strategie = null;//TODO : changer
		this.nom = nom;
		this.faceUp = new LinkedList<>();
		this.faceDown = new LinkedList<>();
		this.main = new LinkedList<>();
	}

	
	/**
	 * Getter de nom
	 * @return nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter de nom
	 * @param nom nom du joueur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter de FaceUp
	 * @return liste des cartes face visible
	 */
	public LinkedList<Carte> getFaceUp() {
		return faceUp;
	}
	
	/**
	 * Getter de FaceDown
	 * @return liste des cartes face cachée
	 */
	public LinkedList<Carte> getFaceDown() {
		return faceDown;
	}

	/**
	 * Getter de la main
	 * @return les cartes dans la main du joueur
	 */
	public LinkedList<Carte> getMain() {
		return main;
	}

	/**
	 * Getter de la stratégie
	 * @return la strategie adoptée
	 */
	public StrategieJeu getStrategie() {
		return strategie;
	}

	
	
	
}
