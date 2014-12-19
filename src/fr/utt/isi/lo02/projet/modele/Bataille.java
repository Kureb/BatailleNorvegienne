package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Bataille {

	/**
	 * Suit le modèle Singleton car ne pourra être instancié qu'une fois par
	 * partie
	 */
	private static Bataille singleton = null;
	/** Liste des joueurs à batailler */
	private ArrayList<Joueur> joueurs;
	/** jeu de cartes */
	private JeuDeCartes jeuDeCartes;
	/** la pioche du jeu */
	private ArrayList<Carte> pioche;
	/** tas où les joueurs posent leurs cartes */
	private ArrayList<Carte> table;

	/**
	 * Constructeur privé d'une bataille pour garantir
	 * l'unicité de cet objet
	 */
	private Bataille() {
		joueurs = new ArrayList<>();
		jeuDeCartes = new JeuDeCartes();
		pioche = new ArrayList<>();
		table = new ArrayList<>();
	}
	
	/**
	 * Permet d'assurer l'unicité de l'objet
	 * @return l'instance de Bataille, la seule, l'unique
	 */
	public static Bataille getInstance() {
		if (singleton == null)
			singleton = new Bataille();
		
		return singleton;
	}

	public ArrayList<Carte> getTable() {
		return table;
	}

	public void setTable(ArrayList<Carte> table) {
		this.table = table;
	}

	public ArrayList<Carte> getPioche() {
		return pioche;
	}

	public void setPioche(ArrayList<Carte> pioche) {
		this.pioche = pioche;
	}

	
	public void addTable(Carte carte) {
		this.table.add(carte);
	}
	
	/**
	 * Permet d'ajouter un joueur à la partie
	 * 
	 * @param j
	 *            le joueur à ajouter
	 * 
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
	 * @param j le joueur à supprimer
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
		return joueurs.toString() + "\n pioche : " + pioche.toString() + "\n table" + table.toString();
	}

	
	
	
	public void echangerCartes() {
		Iterator<Joueur> it = this.joueurs.iterator();
		Joueur joueur;
		while (it.hasNext()) {
			 joueur = it.next();
			 joueur.echangerCartes();;
		}
	}
	
	
	
	/**
	 * Lance la partie, fait jouer tous les joueurs jusqu'à
	 * ce que l'un d'eux gagne ! 
	 */
	public void lancerPartie() {
		Joueur joueur = null, suivant = null;
		boolean fin = false;
		// position du premier joueur
		int position = this.getPositionPremierJoueur();
		// tant que personne n'a gagn&
		joueur = this.getJoueurs().get(position);
		int nbCartes;
		while (!fin) {
			System.out.println("-- Tour de " + joueur.getNom());
			// on récupère le joueur devant jouer, il joue, on vérifie s'il a gagné
			nbCartes = joueur.jouer();
			fin = joueur.verifierGagner();
			suivant = (nbCartes == -1 ? this.getJoueurPrecedent(joueur) : this.getJoueurSuivant(joueur, nbCartes)); 
			joueur = suivant;
			
			
		}
		// Le gagnant est le dernier joueur si nous sommes ici
		System.out.println(joueur.getNom() + " a gagne, bravo ! ");
		
	}

	private Joueur getJoueurPrecedent(Joueur joueur) {
		Joueur precedent = null;
		int positionCourante = this.getPositionCourante(joueur);
		
		if (positionCourante == 0) return this.getJoueurs().get(this.getJoueurs().size()-1);
		else return this.getJoueurs().get(positionCourante-1);
				
		
		

	}

	private int getPositionCourante(Joueur joueur) {
		Iterator<Joueur> it = this.getJoueurs().iterator();
		int pos = 0;
		while (it.hasNext()) {
			if (it.next() == joueur)
				break;
			pos++;
		}
		
		return pos;
	}

	/**
	 * Permet de connaitre le joueur qui va commencer la partie. D'après les
	 * règles de la bataille Norvégienne le joueur à commencer Et le joueur à la
	 * gauche du donner C'est-à-dire qu'on tourne dans le sens horaires des
	 * aiguilles d'une montre C'est donc le joueur qui suit le Mélangeur qui va
	 * commencer la partie
	 * 
	 * @return Le joueur qui doit commencer la partie
	 */
	public Joueur getJoueurQuiJoueEnPremier() {
		
		// Si le Scrambler est le dernier joueur, on retourne alors le premier
		// de la liste
		// sinon on retourne simplement le joueur d'après
		return ((this.getPositionPremierJoueur() - 1 == joueurs.size() ? joueurs.get(0) : joueurs
				.get(this.getPositionPremierJoueur())));

	}
	 
	/** Retourne le joueur qui est censé jouer après 
	 * @param nbCartesJouees */
	public Joueur getJoueurSuivant(Joueur joueurActuel, int nbCartesJouees) {
		int indexDernierElement = Bataille.getInstance().getTable().isEmpty() ? -1 : Bataille.getInstance().getTable().size()-1;
		Carte derniereCarteJouee = (indexDernierElement == -1 ? null : Bataille.getInstance().getTable().get(indexDernierElement));
		Joueur joueurSuivant = null;
		//int nbCartesJouees = 1;
		if (derniereCarteJouee != null && derniereCarteJouee.estSpeciale())
			joueurSuivant = JoueurSuivantCarteSpeciale(derniereCarteJouee, joueurActuel, nbCartesJouees);
		else 
			joueurSuivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
		
		return joueurSuivant;

	}
	
	public Joueur JoueurSuivantCarteNormale(Joueur joueurActuel) {
		Iterator<Joueur> it = joueurs.iterator();
		int i = 0;
		while (it.hasNext())
			if (it.next() == joueurActuel)
				break;
			else
				i++;
				
		if (i + 1 < this.getJoueurs().size())
			i++;
		// Sinon c'est qu'on est à la fin de la liste, donc on retourne au début
		else
			i = 0;

		Joueur joueurSuivant = this.getJoueurs().get(i);
		
		return joueurSuivant;
	}
	
	public Joueur JoueurSuivantCarteNormale(Carte derniereCarteJouee, Joueur joueurActuel) {
		Iterator<Joueur> it = joueurs.iterator();
		int i = 0;
		while (it.hasNext())
			if (it.next() == joueurActuel)
				break;
			else
				i++;
				
		if (i + 1 < this.getJoueurs().size())
			i++;
		// Sinon c'est qu'on est à la fin de la liste, donc on retourne au début
		else
			i = 0;

		Joueur joueurSuivant = this.getJoueurs().get(i);
		
		return joueurSuivant;
	}

	public Joueur JoueurSuivantCarteSpeciale(Carte derniereCarteJouee, Joueur joueurActuel, int nbCartesJouees) {
		Joueur suivant = null, victime = null;
		
		if (nbCartesJouees == 0) return JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
		
		
		// C'est pas très propre, mais c'est juste pour montrer
		// que quand on a un 8 on passe le tour
		switch (derniereCarteJouee.getValeur()) {
			case 0:
			case 5:
			case 10:
				suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
				break;
			case 8:
				suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
				this.getTable().clear();
				System.out.println("Le tas est retire du jeu !");
				break;
			case 6: // 8
			//Le joueur suivant passe son tour(autant que de 8 posés)	
					suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
				for (int i = 0; i < nbCartesJouees; i++) {
					joueurActuel = suivant;
					System.out.println(joueurActuel.getNom() + " passe son tour");
					suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
				}
				
				break;
			case 12: //As
				//On doit envoyer le tas à un joueur, qui peut contrer!
				boolean suivantPeutContrer = false;
				Carte carteContre = null;
				Joueur joueurActuelVrai = joueurActuel;
				do {
					victime = joueurActuel.getStrategie().choisirQuiRalentir(joueurActuel);
					suivantPeutContrer = victime.peutContrerAs();
					if (suivantPeutContrer) {
						System.out.println("Mais " + victime.getNom() + " peut contrer");
						carteContre = victime.getStrategie().choisirCarteContre(victime);
						victime.poserCarteUnique(carteContre);
						if (carteContre.getValeur() == 12) {
							joueurActuel = victime;
							victime = joueurActuel.getStrategie().choisirQuiRalentir(joueurActuel);
							System.out.println(joueurActuel.getNom() + " veut ralentir " + victime.getNom());
							suivantPeutContrer = victime.peutContrerAs();
						} else { // si c'est un 2
							suivant = JoueurSuivantCarteNormale(carteContre, joueurActuelVrai);
						}
					} else {
						//On envoie le tas seulement si le mec a contré avec un As
						if (carteContre.getValeur() == 12)
							joueurActuel.envoyerTas(victime);
					}
				} while (suivantPeutContrer);
				
				
				if (carteContre != null)
					if (carteContre.getValeur() == 12) 
						joueurActuel.envoyerTas(victime);
				
				suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuelVrai);
				
				break;
			default:
				suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel); 
				break;

		}
		return suivant;
	}

	/**
	 * Permet de connaitre la position du joueur qui doit
	 * commencer la partie
	 * @return la position du joueur qui commence à jouer
	 */
	public int getPositionPremierJoueur() {
		Iterator<Joueur> it = joueurs.iterator();
		int i = 0;
		int positionScrambler = 0;
		while (it.hasNext()) {
			i++;
			if (it.next() instanceof Scrambler) {
				positionScrambler = i;
				break; 
			} 

		}
		return ++positionScrambler;
	}

	public void clearTable() {
		this.table.clear();
	}


}
