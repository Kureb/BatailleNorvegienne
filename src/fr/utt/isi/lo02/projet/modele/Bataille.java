package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import fr.utt.isi.lo02.projet.controleur.BatailleControleur;



public class Bataille extends BatailleAbstraite{

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
	
	private final static String[] COMPUTERS = {"Steve", "Bill", "Linus", "Cendrillon", "Schwarzy", "Obama", "Eustache", "Jack", "Wozniak", "Vador", "Alice", "Timothée", "Ariel"};
	
	
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

	public ArrayList<Carte> getTable() {
		return table;
	}

	public void setTable(ArrayList<Carte> table) {
		this.table = table;
		//setChanged();
		//notifyObservers();
	}

	public ArrayList<Carte> getPioche() {
		return pioche;
	}

	public void setPioche(ArrayList<Carte> pioche) {
		this.pioche = pioche;
		//setChanged();
		//notifyObservers();
	}

	
	public void addTable(Carte carte) {
		this.table.add(carte);
		setChanged();
		notifyObservers(carte);
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
		//setChanged();
		//notifyObservers();

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
		//setChanged();
		//notifyObservers();
	}

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public JeuDeCartes construireJeuDeCartes() {
		JeuDeCartes jdc = new JeuDeCartes();
		this.jeuDeCartes = jdc;
		return jdc;
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
		/*	try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/
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
			//System.out.println("-- Tour de " + joueur.getNom());
			// on récupère le joueur devant jouer, il joue, on vérifie s'il a gagné
			nbCartes = joueur.jouer();
			fin = joueur.verifierGagner();
			this.attendre(); //TODO trouver pour mettre en pause et reprendre à l'appui du bouton
			if (fin) {
				message = joueur.getNom() + " a gagné, bravo ! ";
				//mesConsole = message.replace("à", "a");
				//mesConsole = message.replace("é", "e");
				//System.out.println(mesConsole);
			} else {
				suivant = (nbCartes == -1 ? this.getJoueurPrecedent(joueur) : this.getJoueurSuivant(joueur, nbCartes));
				fin = joueur.verifierGagner();
				if (!fin)
					joueur = suivant;
				else {//On peut très bien gagner suite à ce tour donc on refait le test
					message = joueur.getNom() + " a gagné, bravo ";
					//mesConsole = message.replace("à", "a");
					//mesConsole = message.replace("é", "e");
					//System.out.println(mesConsole);
					
				}
			}
			
			setChanged();
			notifyObservers(message);
			
			continuer = false;
		}
		
		
	}

	
	public void attendre() {
		while (!continuer) {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}
	
	public void continuer() {
		this.continuer = true;
	}

	private Joueur getJoueurPrecedent(Joueur joueur) {
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
		String message = "";
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
				message = "Le tas est retiré du jeu !";
				setChanged();
				notifyObservers(message);
				//System.out.println(message.replace("é", "e"));
				break;
			case 6: // 8
			//Le joueur suivant passe son tour(autant que de 8 posés)	
					suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
				for (int i = 0; i < nbCartesJouees; i++) {
					joueurActuel = suivant;
					message = joueurActuel.getNom() + " passe son tour";
					setChanged();
					notifyObservers(message);
					//System.out.println(message);
					suivant = JoueurSuivantCarteNormale(derniereCarteJouee, joueurActuel);
				}
				
				break;
			case 12: //As
				//On doit envoyer le tas à un joueur, qui peut contrer!
				//Normalement à cette étape l'As est sur le tas
				boolean suivantPeutContrer = false;
				Carte carteContre = null;
				Joueur joueurActuelVrai = joueurActuel;
				do {
					victime = joueurActuel.getStrategie().choisirQuiRalentir(joueurActuel);
					message = joueurActuel.getNom() + " victimise " + victime.getNom();
					//System.out.println(message);
					setChanged();
					notifyObservers(message);
					suivantPeutContrer = victime.peutContrerAs();
					if (suivantPeutContrer) {
						carteContre = victime.getStrategie().choisirCarteContre(victime);
						message = "Mais " + victime.getNom() + " peut contrer avec un " + carteContre;
						setChanged();
						notifyObservers(message);
						//System.out.println(message);
						victime.poserCarteUnique(carteContre);
						if (carteContre.getValeur() == 12) {
							joueurActuel = victime;
							victime = joueurActuel.getStrategie().choisirQuiRalentir(joueurActuel);
							message = joueurActuel.getNom() + " veut ralentir " + victime.getNom();
							//System.out.println(message);
							setChanged();
							notifyObservers(message);
							suivantPeutContrer = victime.peutContrerAs();
						} else { // si c'est un 2
							return JoueurSuivantCarteNormale(carteContre, joueurActuelVrai);
						}
					} else {
						//On envoie le tas seulement si le mec a contré avec un As
						message = victime.getNom() + " ne peut pas contrer";
						//System.out.println(message);
						setChanged();
						notifyObservers();
						//if (carteContre != null)
						//	if (carteContre.getValeur() == 12)
								joueurActuel.envoyerTas(victime);
								
					}
				} while (suivantPeutContrer);
				
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

	public void clearTable() {
		this.table.clear();
		setChanged();
		notifyObservers("Le tas est vide");
	}
	
	public void clearPioche() {
		this.pioche.clear();
		setChanged();
		notifyObservers("pioche");
	}

	public Joueur getJoueurs(String nom) {
		Iterator<Joueur> it = this.getJoueurs().iterator();
		while (it.hasNext()){
			Joueur j = it.next();
			if (j.getNom().equalsIgnoreCase(nom))
				return j;
		}
		
		return null;
		
	}
	
	
	//public String[] computersNames() {
	//	return this.COMPUTERS;
	//}

	
	
	public void init() {
		
		/*
		 * 
		 * if (joueurs == null) {
			Joueur Benjamin = new Joueur("Benjamin", new StrategieRapide());
			Joueur Diane = new Joueur("Diane", new StrategieSpeciale());
			
			// TODO : ajouter une exception si plus d'un joueur physique ? Ou si plusieurs Scrambler ?
			// ou si Pas de joueur physique
			
			Joueur Maxime = new Joueur("Maxime", new StrategieRapide());
			Scrambler Toi = new Scrambler("Toi");
			Joueur Robin = new Joueur("Robin", new StrategieRapide());
			
			this.addJoueur(Toi);
			this.addJoueur(Diane);
			this.addJoueur(Benjamin);
			this.addJoueur(Maxime);
			this.addJoueur(Robin);
		
			Toi.distribuerPaquet(this);
		 */
		
		BatailleControleur.initialisationPartie();
		
		
		
		
		
	}

	public static String[] getComputers() {
		return COMPUTERS;
	}

	



}
