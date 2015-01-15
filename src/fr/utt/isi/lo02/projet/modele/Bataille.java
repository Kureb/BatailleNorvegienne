package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import fr.utt.isi.lo02.projet.controleur.BatailleControleur;


/**
 * Modèle de la Bataille,
 * essence du jeu
 * @author daussy
 *
 */
public class Bataille extends Observable{

	/**
	 * Suit le modèle Singleton car ne pourra être instancié qu'une fois par
	 * partie
	 */
	private static Bataille singleton = null;
	/** Liste des joueurs à batailler */
	private ArrayList<Joueur> joueurs;
	/** jeu de cartes */
	@SuppressWarnings("unused")
	private JeuDeCartes jeuDeCartes;
	/** la pioche du jeu */
	private ArrayList<Carte> pioche;
	/** tas où les joueurs posent leurs cartes */
	private ArrayList<Carte> table;
	/** tableau de noms pour les joueurs fictifs */
	private final static String[] COMPUTERS = {"Steve", "Bill", "Linus", "Cendrillon", "Schwarzy", "Obama", "Eustache", "Jack", "Wozniak", "Vador", "Alice", "Timothée", "Ariel"};
	/** boolean permettant de faire pause dans le jeu */
	private boolean continuer;

	/**
	 * Constructeur privé d'une bataille pour garantir
	 * l'unicité de cet objet
	 */
	private Bataille() {
		joueurs = new ArrayList<>();
		jeuDeCartes = new JeuDeCartes();
		pioche = new ArrayList<>();
		table = new ArrayList<>();
		continuer = false;
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
	
	/**
	 * Getter de table
	 * @return les cartes présentes sur la table
	 */
	public ArrayList<Carte> getTable() {
		return table;
	}

	/**
	 * Setter de table
	 * @param table liste de cartes à mettre sur la table
	 */
	public void setTable(ArrayList<Carte> table) {
		this.table = table;
	}

	/**
	 * Getter de la pioche
	 * @return les cartes présentes dans la pioche
	 */
	public ArrayList<Carte> getPioche() {
		return pioche;
	}

	/**
	 * Setter de la pioche
	 * @param pioche liste des cartes à mettre dans la pioche
	 */
	public void setPioche(ArrayList<Carte> pioche) {
		this.pioche = pioche;
	}

	/**
	 * Ajoute une carte sur la table
	 * @param carte Carte à ajouter sur la table
	 */
	public void addTable(Carte carte) {
		this.table.add(carte);
		//Notifie la vue
		setChanged();
		notifyObservers(carte);
	}
	
	/**
	 * Permet d'ajouter un joueur à la partie
	 * 
	 * @param j le joueur à ajouter
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

	/**
	 * Getter de joueurs
	 * @return la liste des joueurs de la partie
	 */
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	
	/**
	 * Setter de joueurs
	 * @param joueurs liste des joueurs à mettre dans la partie
	 */
	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	/**
	 * Construit le jeu de carte en fonction du
	 * nombre de joueurs
	 * @return un jeu de carte
	 */
	public JeuDeCartes construireJeuDeCartes() {
		JeuDeCartes jdc = new JeuDeCartes();
		this.jeuDeCartes = jdc;
		return jdc;
	}

	/**
	 * Setter de jeu de cartes
	 * @param jeuDeCartes Jeu à implémenter
	 */
	public void setJeuDeCartes(JeuDeCartes jeuDeCartes) {
		this.jeuDeCartes = jeuDeCartes;
	}

	/**
	 * toString de bataille
	 * @return l'état du joueur (main, face visibles, face cachées) et l'état de la pioche et de la table
	 */
	public String toString() {
		return joueurs.toString() + "\n pioche : " + pioche.toString() + "\n table" + table.toString();
	}

	
	
	/**
	 * Permet de proposer à tous les joueurs
	 * d'échanger leurs cartes
	 */
	public void echangerCartes() {
		Iterator<Joueur> it = this.joueurs.iterator();
		Joueur joueur;
		while (it.hasNext()) {
		joueur = it.next();
			joueur.echangerCartes();
		}
		
		
	}
	
	
	
	/**
	 * Lance la partie, fait jouer tous les joueurs jusqu'à
	 * ce que l'un d'eux gagne ! 
	 */
	public void lancerPartie() {
		Joueur joueur = null, suivant = null;
		boolean fin = false;
		String message = "";
		// position du premier joueur
		int position = this.getPositionPremierJoueur();
		// tant que personne n'a gagn&
		joueur = this.getJoueurs().get(position);
		int nbCartes;
		while (!fin) {
			// on récupère le joueur qui doit jouer, il joue, on vérifie s'il a gagné
			nbCartes = joueur.jouer();
			fin = joueur.verifierGagner();
			this.attendre(); //Attend l'appui sur le bouton dans la vue
			if (fin) {
				message = joueur.getNom() + " a gagné, bravo ! ";
			} else {
				suivant = (nbCartes == -1 ? this.getJoueurPrecedent(joueur) : this.getJoueurSuivant(joueur, nbCartes));
				fin = joueur.verifierGagner();
				if (!fin)
					joueur = suivant;
				else {//On peut très bien gagner suite à ce tour donc on refait le test
					message = joueur.getNom() + " a gagné, bravo ";
				}
			}
			// On notifie la vue
			setChanged();
			notifyObservers(message);
			
			continuer = false;
		}
		
		
	}

	/**
	 * Attend tant que continuer ne passe pas à vrai
	 */
	public void attendre() {
		while (!continuer) {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}
	
	/**
	 * Passe le boolean continuer à vrai
	 */
	public void continuer() {
		this.continuer = true;
	}

	/**
	 * Permet d'avoir le joueur précédent
	 * @param joueur Joueur suivant du précédent
	 * @return joueur précédent celui passé en paramètre
	 */
	private Joueur getJoueurPrecedent(Joueur joueur) {
		int positionCourante = this.getPositionCourante(joueur);
		
		if (positionCourante == 0) return this.getJoueurs().get(this.getJoueurs().size()-1);
		else return this.getJoueurs().get(positionCourante-1);
				
		
		

	}

	/**
	 * Permet d'avoir la position d'un joueur
	 * @param joueur dont on souhaite savoir la position
	 * @return position du joueur
	 */
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
	 * @param nbCartesJouees 
	 * */
	public Joueur getJoueurSuivant(Joueur joueurActuel, int nbCartesJouees) {
		int indexDernierElement = Bataille.getInstance().getTable().isEmpty() ? -1 : Bataille.getInstance().getTable().size()-1;
		Carte derniereCarteJouee = (indexDernierElement == -1 ? null : Bataille.getInstance().getTable().get(indexDernierElement));
		Joueur joueurSuivant = null;
		if (derniereCarteJouee != null && derniereCarteJouee.estSpeciale())
			joueurSuivant = JoueurSuivantCarteSpeciale(derniereCarteJouee, joueurActuel, nbCartesJouees);
		else 
			joueurSuivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
		
		return joueurSuivant;

	}
	
	/**
	 * Retourne le joueur qui doit jouer après
	 * dans le cas où ce n'est pas une carte spéciale
	 * @param joueurActuel joueur qui vient de terminer son tour
	 * @return
	 */
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
	
	/**
	 * Retourne le joueur qui doit jouer après
	 * dans le cas où ce n'est pas une carte spéciale
	 * @param derniereCarteJouee dernière carte sur la table
	 * @param joueurActuel joueur qui vient de terminer son tour
	 * @return
	 */
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

	/**
	 * Permet d'avoir le joueur suivant dans le cas où
	 * la dernière carte jouée est une carte spéciale !
	 * @param derniereCarteJouee dernière carete posée sur la table
	 * @param joueurActuel joueur finissant son tour
	 * @param nbCartesJouees nb de cartes jouées (surtout par rapport au 8)
	 * @return joueur qui va jouer ensuite
	 */
	public Joueur JoueurSuivantCarteSpeciale(Carte derniereCarteJouee, Joueur joueurActuel, int nbCartesJouees) {
		Joueur suivant = null, victime = null;
		String message = "";
		//Si le dernier joueur n'a posé aucune carte,on prend donc le suivant
		if (nbCartesJouees == 0) return JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
		
		
		// On regarde la valeur de la dernière carte jouée
		switch (derniereCarteJouee.getValeur()) {
			case 0:
			case 5:
			case 10:
				//Le joueur est le suivant, suivant la logique des tours
				suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
				break;
			case 8: //10
				//Le joueur est le suivant, suivant la logique des tours
				suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
				//Et on retire le tas
				this.getTable().clear();
				message = "Le tas est retiré du jeu !";
				//On notifie la vue 
				setChanged();
				notifyObservers(message);
				break;
			case 6: //8
				//Le joueur suivant passe son tour(autant que de 8 posés)	
				suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
				for (int i = 0; i < nbCartesJouees; i++) {
					joueurActuel = suivant;
					message = joueurActuel.getNom() + " passe son tour";
					//On notifie la vue pour mettre à jour les logs
					setChanged();
					notifyObservers(message);
					suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
				}
				
				break;
			case 12: //As
				//On doit envoyer le tas à un joueur, qui peut contrer!
				//Normalement à cette étape l'As est sur le tas
				boolean suivantPeutContrer = false;
				Carte carteContre = null;
				Joueur joueurActuelVrai = joueurActuel;
				do { //Tant que la victime peut contrer
					victime = joueurActuel.getStrategie().choisirQuiRalentir(joueurActuel); //choix d'une victime
					message = joueurActuel.getNom() + " victimise " + victime.getNom(); //notification de la vue
					setChanged();
					notifyObservers(message);
					suivantPeutContrer = victime.peutContrerAs(); //On vérifie qu'il peut contrer un As
					if (suivantPeutContrer) { //S'il peut on choisit sa carte de contre (As ou deux)
						carteContre = victime.getStrategie().choisirCarteContre(victime);
						message = "Mais " + victime.getNom() + " peut contrer avec un " + carteContre;
						setChanged(); //Et on notifie la vue
						notifyObservers(message);
						victime.poserCarteUnique(carteContre);
						if (carteContre.getValeur() == 12) { //Si cette carte est un As
							joueurActuel = victime; //La victime devient le joueur actuel
							//Qui choisit une nouvelle victime
							victime = joueurActuel.getStrategie().choisirQuiRalentir(joueurActuel);
							message = joueurActuel.getNom() + " veut ralentir " + victime.getNom();
							//On met à jour les logs
							setChanged();
							notifyObservers(message);
							suivantPeutContrer = victime.peutContrerAs(); // On vérifie que sa nouvelle victime peut contrer
						} else { // si c'est un 2
							return JoueurSuivantCarteNormale(carteContre, joueurActuelVrai);
						}
					} else {
						//Ici, il n'a donc pas pu contrer
						message = victime.getNom() + " ne peut pas contrer";
						setChanged();
						notifyObservers(message);
						
						//il se prend le tas
						joueurActuel.envoyerTas(victime);
								
					}
				} while (suivantPeutContrer);
				
				//Si le premier a contré, il se prend le tas
				if (carteContre != null)
					if (carteContre.getValeur() == 12)  {
						joueurActuel.envoyerTas(victime);
					}
						
				
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
		if (positionScrambler != 1)
			return ++positionScrambler;
		else
			return joueurs.size()-1;
	}

	/**
	 * Vide la tas et notifie la vue
	 */
	public void clearTable() {
		this.table.clear();
		setChanged();
		notifyObservers("Le tas est vide");
	}
	
	/**
	 * Vide la pioche et notifie la vue
	 */
	public void clearPioche() {
		this.pioche.clear();
		setChanged();
		notifyObservers("pioche");
	}

	/**
	 * Donne un joueur dont le nom a été passé en paramètre
	 * @param nom du joueur que l'on cherche
	 * @return le joueur avec le nom passé en paramètre
	 */
	public Joueur getJoueurs(String nom) {
		Iterator<Joueur> it = this.getJoueurs().iterator();
		while (it.hasNext()){
			Joueur j = it.next();
			if (j.getNom().equalsIgnoreCase(nom))
				return j;
		}
		
		return null;
		
	}
	
	

	
	/**
	 * Initialise la bataille
	 * en ouvrant la fenêtre JDialog faite pour
	 */
	public void init() {
		BatailleControleur.initialisationPartie();
		
		
		
		
		
	}

	/**
	 * Permet d'avoir le tableau des noms
	 * des joueurs ficitfs
	 * @return tableau de noms
	 */
	public static String[] getComputers() {
		return COMPUTERS;
	}

	



}
