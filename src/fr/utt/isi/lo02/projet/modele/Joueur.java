package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Représente le Joueur
 * 
 * @author daussy - obeidat
 * 
 */
public class Joueur {

	/** Le nom du joueur */
	private String nom;
	/** Les cartes faces visibles du joueur */
	private LinkedList<Carte> faceUp;
	/** Les cartes faces cachées du joueur */
	private LinkedList<Carte> faceDown;
	/** Les cartes en main du joueur */
	private LinkedList<Carte> main;
	/** La stratégie adoptée par le joueur */
	private StrategieJeu strategie;
	/** Compte le nombre de joueurs ajoutés à la partie */
	public static int NB_JOUEURS = 0;

	
	/**
	 * Constructeur du joueur réel
	 * 
	 * @param nom
	 *            Nom du joueur
	 */
	public Joueur(String nom) {
		this.strategie = new StrategieRelle();
		this.nom = nom;
		this.faceUp = new LinkedList<>();
		this.faceDown = new LinkedList<>();
		this.main = new LinkedList<>();
		Joueur.NB_JOUEURS++;
	}

	
	
	/**
	 * Constructeur du joueur virtuel
	 * 
	 * @param nom
	 *            Nom du joueur
	 * @param strategie
	 *            Stratégie à utiliser
	 */
	public Joueur(String nom, StrategieJeu strategie) {
		this.strategie = strategie;
		this.nom = nom;
		this.faceUp = new LinkedList<>();
		this.faceDown = new LinkedList<>();
		this.main = new LinkedList<>();
		Joueur.NB_JOUEURS++;
	}

	
	// ----------------- FIN ATTRIBUTS/CONSTRUCTEURS -----------------------
	
	
	
	
	
	
	/**
	 * Permet au joueur de jouer (appelle la méthode propre à sa stratégie)
	 */
	public void jouer() {
		strategie.jouerCarte(this);
	}

	
	
	/**
	 * Permet de recevoir une carte lors de la distribution
	 * 
	 * @param carte
	 *            Carte à recevoir
	 */
	public void recevoirCarte(Carte carte) {
		// On vérifie dans quelle liste la mettre
		if (faceDown.size() < 3)
			faceDown.add(carte);

		else if (faceUp.size() < 3)
			faceUp.add(carte);

		else if (main.size() < 3)
			main.add(carte);
	}

	/**
	 * Permet au joueur d'échanger une carte de sa main avec une carte visible
	 * devant lui
	 * 
	 * @param carteMain
	 *            carte qu'il possède dans sa main
	 * @param carteVisible
	 *            carte visible devant lui Remarque : les cartes ne seront pas
	 *            forcément rangées dans le même ordre
	 */
	public void echangerCarte(Carte carteMain, Carte carteVisible) {
		// On vérifie que les cartes sont bien à l'endroit indiqué
		if (this.faceUp.contains(carteVisible) && this.main.contains(carteMain)) {
			this.faceUp.add(carteMain);
			this.main.remove(carteMain);
			this.main.add(carteVisible);
			this.faceUp.remove(carteVisible);
		}
	}

	/**
	 * Permet de proposer l'échange de cartes à un joueur
	 */
	public void proposerChangerCartes() {
		//System.out.println(this.toString());
		Scanner sc = new Scanner(System.in);
		System.out.print(this.getNom()
				+ ", quelle carte dans la main (1, 2 ou 3) : ");
		int carteM = sc.nextInt();
		System.out.print(this.getNom() + ", quelle carte visible ? (1, 2 ou 3) : ");
		int carteV = sc.nextInt();
		Carte carteMain = this.main.get(--carteM);
		Carte carteVisible = this.faceUp.get(--carteV);
		this.echangerCarte(carteMain, carteVisible);
	}
	
	/**
	 * Permet de poser une carte sur la table
	 * @param carte Carte à poser sur la table
	 * On ajoute la carte à la table
	 * et on la supprime de la main du joueur
	 */
	public void poserCarteUnique(Carte carte) {
		Bataille.getInstance().addTable(carte);
		this.main.remove(carte);
	}

	
	/**
	 * Personnalise l'affichage d'un joueur
	 * Ecrit son nom, puis le contenu de ses 3 tas 
	 * (main, cartes visibles et cartes cachées)
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append(nom);
		sb.append("\n\t Main -> ");
		sb.append(main);
		sb.append("\n\t FaceUp -> ");
		sb.append(faceUp);
		sb.append("\n\t FaceDown -> ");
		sb.append(faceDown);
		sb.append("\n");
		return sb.toString();
	}

	//TODO  && this.faceDown.isEmpty() && this.faceUp.isEmpty()
	public boolean verifierGagner() {
		return (this.main.isEmpty());
	}
	
	/**
	 * Utilisation de Delegation Pattern
	 * TODO : expliquer
	 */
	public void echangerCartes() {
		this.getStrategie().echangerCartes(this);
	}
	
	
	
	
	// ---------------------- GETTER ET SETTER --------------------

	/**
	 * Getter de nom
	 * 
	 * @return nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter de nom
	 * 
	 * @param nom
	 *            nom du joueur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter de FaceUp
	 * 
	 * @return liste des cartes face visible
	 */
	public LinkedList<Carte> getFaceUp() {
		return faceUp;
	}

	/**
	 * Getter de FaceDown
	 * 
	 * @return liste des cartes face cachée
	 */
	public LinkedList<Carte> getFaceDown() {
		return faceDown;
	}

	/**
	 * Getter de la main
	 * 
	 * @return les cartes dans la main du joueur
	 */
	public LinkedList<Carte> getMain() {
		return main;
	}

	/**
	 * Getter de la stratégie
	 * 
	 * @return la strategie adoptée
	 */
	public StrategieJeu getStrategie() {
		return strategie;
	}

	public void setStrategieJeu(StrategieJeu strategie) {
		if (this.strategie != null)
			this.strategie = strategie;
		// TODO : else en faire une exception ? Un joueur réel ne peut devenir
		// un joueur virtuel
		// et vérifier aussi le != null InstanceOf ?
	}


}
