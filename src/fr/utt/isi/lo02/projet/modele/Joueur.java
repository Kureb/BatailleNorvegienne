package fr.utt.isi.lo02.projet.modele;

import java.util.LinkedList;

/**
 * Représente le Joueur
 * @author daussy
 *
 */
public class Joueur {

	private String nom;
	private final LinkedList<Carte> faceUp;
	private final LinkedList<Carte> faceDown;
	private final LinkedList<Carte> main;
	private final StrategieJeu strategie;
	
	public Joueur(String nom){
		this.strategie = new StrategieJeu();//TODO : changer
		this.nom = nom;
		this.faceUp = new LinkedList<>();
		this.faceDown = new LinkedList<>();
		this.main = new LinkedList<>();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LinkedList<Carte> getFaceUp() {
		return faceUp;
	}

	public LinkedList<Carte> getFaceDown() {
		return faceDown;
	}

	public LinkedList<Carte> getMain() {
		return main;
	}

	
	
	
}
