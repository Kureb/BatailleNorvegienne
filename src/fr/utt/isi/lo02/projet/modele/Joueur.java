package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;


/**
 * Représente le Joueur
 * 
 * @author daussy
 * 
 */
public class Joueur extends Observable{

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
	 * @param nom Nom du joueur
	 * 
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

	
	
	
	/**
	 * Permet au joueur de jouer (appelle la méthode propre à sa stratégie)
	 * @return 
	 */
	public int jouer() {
		return strategie.jouerCarte(this);
	}
	
	/**
	 * Permet de savoir si un joueur peut jouer
	 * @param derniereCarte dernière carte jouée, donc celle sur le paquet
	 * @return vrai ou faux (peut jouer ou non)
	 */
	public boolean peutJouer(Carte derniereCarte) {
		Carte carte = null;
		if (derniereCarte == null) return true;
		boolean rep = false;
		Iterator<Carte> it = this.getMain().iterator();
		while (it.hasNext()){
			carte = it.next();
			if (derniereCarte.estRecouvrablePar(carte)){
				rep = true;
				return rep;
			}
		}
		return rep;
	}
	
	
	/**
	 * Permet de savoir si le joueur peut jouer
	 * @return vrai ou faux
	 */
	public boolean peutJouer() {
		//Si la table est vide retourne vrai
		if (Bataille.getInstance().getTable().isEmpty()) 
			return true;
		else {
			//Sinon on appelle la méthode qui prend en paramètre la dernière
			//carte posée
			int index = Bataille.getInstance().getTable().size()-1;
			Carte derniere = Bataille.getInstance().getTable().get(index);
			return peutJouer(derniere);
		}
		
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
		if (this.possedeFaceUp(carteVisible) && this.possedeMain(carteMain)) { 
			this.faceUp.add(carteMain);
			this.main.remove(carteMain);
			this.main.add(carteVisible);
			this.faceUp.remove(carteVisible);
			//On notifie la vue
			setChanged();
			notifyObservers(this);
		}	
		
		
	}

	/**
	 * Permet de proposer l'échange de cartes à un joueur
	 */
	public void proposerChangerCartes() {
		//On notifie la vue qui va appeler le controleur
		setChanged();
		notifyObservers("echange");
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

	/**
	 * On vérifie que le joueur a gagné
	 * Donc s'il lui reste des cartes ou non
	 * @return
	 */
	public boolean verifierGagner() {
		return (this.main.isEmpty() && this.faceDown.isEmpty() && this.faceUp.isEmpty());
	}
	
	/**
	 * Permet au joueur d'échanger ses cartes en début de partie
	 */
	public void echangerCartes() {
		this.getStrategie().echangerCartes(this);
	}
	
	
	
	
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
	 * Setter de faceUp
	 * @param les cartes à implémenter
	 */
	public void setFaceUp(LinkedList<Carte> faceUp) {
		this.faceUp = faceUp;
	}


	/**
	 * Setter de faceDown
	 * @param faceDown les cartesà implémenter
	 */
	public void setFaceDown(LinkedList<Carte> faceDown) {
		this.faceDown = faceDown;
	}


	/**
	 * Setter de la main
	 * @param main les cartes à implémenter
	 */
	public void setMain(LinkedList<Carte> main) {
		this.main = main;
	}
	
	/**
	 * Permet d'ajouter une carte dans la main du joueur
	 * @param carte Carte à ajouter
	 */
	public void addMain(Carte carte) {
		this.main.add(carte);
	}



	/**
	 * Getter de la stratégie
	 * 
	 * @return la strategie adoptée
	 */
	public StrategieJeu getStrategie() {
		return strategie;
	}
	
	/**
	 * Setter de stratégie
	 * @param strategie à implémenter
	 */
	public void setStrategieJeu(StrategieJeu strategie) {
		if (this.strategie != null)
			this.strategie = strategie;
	}


	/**
	 * Permet d'envoyer le tas à un joueur
	 * @param suivant
	 */
	public void envoyerTas(Joueur suivant) {
		if (!suivant.peutContrerAs()) {
			String message = this.getNom() + " envoie le tas à " + suivant.getNom();
			
			setChanged();
			notifyObservers(message);
			
			//Tant qu'il y a des cartes dans le tas on les ajoutes à la main
			//du joueur
			Iterator<Carte> it = Bataille.getInstance().getTable().iterator();
			while (it.hasNext()) {
				suivant.addMain(it.next());
			}
			//Puis on s'assure que la table est vide
			Bataille.getInstance().clearTable();
			
			setChanged();
			notifyObservers(suivant);
		}
		
	}

	/**
	 * Permet de savoir si un joueur peut contrer un As
	 * Donc de savoir s'il possède un 2 ou un As dans sa main
	 * @return vrai s'il peut contrer, faux sinon
	 */
	public boolean peutContrerAs() {
		
		if (!this.estMainVide()) {
			Iterator<Carte> it = this.getMain().iterator();
			Carte carte = null;
			while (it.hasNext()) {
				carte = it.next();
				if ((carte.getValeur() == 0) || (carte.getValeur() == 12))
					return true;
			}
		}
		return false;
	}



	/**
	 * Permet de savoir si le joueur possède une carte spéciale
	 * dans les cartes face visible
	 * @return true/false s'il possède ou non une carte sépciale
	 */
	public boolean possedeCarteSpeciales() {
		Iterator<Carte> it = this.getFaceUp().iterator();
		while (it.hasNext())
			if (it.next().estSpeciale())
				return true;
		return false;
	}


	/**
	 * Permet de savoir si le joueur peut faire des échanger
	 * afin de former une paire (ou plus) et donc poser ses cartes
	 * le plus vite possible
	 * @return
	 */
	public boolean peutFormerPaire() {
		Iterator<Carte> it = this.getMain().iterator();
		Iterator<Carte> itt = this.getFaceUp().iterator();
		
		while (it.hasNext()) {
			while (itt.hasNext()) 
				if (it.next().getValeur() == itt.next().getValeur())
					return true;
		}
		return false;
	}
	
	
	/**
	 * Permet d'avoir la carte de faceUp à échanger pour avoir une paire
	 * @return carte pour former une paire
	 */
	public Carte getCarteUpPourFormerPaire() {
		Iterator<Carte> it = this.getMain().iterator();
		Iterator<Carte> itt = this.getFaceUp().iterator();
		while (it.hasNext())
			while (itt.hasNext()) {
				Carte carteMain = it.next();
				Carte carteUp = itt.next();
				if (carteMain.getValeur() == carteUp.getValeur())
					return carteUp;					
			}
				
			
		return null;
	}
	
	/**
	 * Permet de sacrifier une carte de la main pour avoir une paire
	 * @param carteDouble carte qu'il possède en faceUp qu'il veut lier
	 * @return carte sacrifice
	 */
	public Carte getCarteMainSacrificePourFormerPaire(Carte carteDouble) {
		Iterator<Carte> it = this.getMain().iterator();
		while (it.hasNext()) {
			Carte carte = it.next();
			if (carte.getValeur() != carteDouble.getValeur())
				return carte;
		}
				
			
		return null;
		
	}
	
	/**
	 * Permet de prendre la première carte normale disponible
	 * dans la main du joueur
	 * @return carte spéciale
	 */
	public Carte getCarteNormalMain() {
		Iterator<Carte> it = this.getMain().iterator();
		Carte carte= null;
		while (it.hasNext())
			carte = it.next();
			if (!(carte.estSpeciale()))
				return carte;
		
		return carte;
	}


	/**
	 * Permet de prendre la première carte spéciale disponible
	 * dans la pioche face visible du joueur
	 * @return carte normale
	 */
	public Carte getCarteSpecialeFaceUp() {
		Iterator<Carte> it = this.getFaceUp().iterator();
		Carte carte = null;
		while (it.hasNext()) {
			carte= it.next();
			if (carte.estSpeciale())
				return carte;

		}
		return carte;
	}


	/**
	 * Permet de savoir s'il reste des cartes normales
	 * dans la main du joueur
	 * @return vrai ou faux
	 */
	public boolean possedeCarteNormale() {
		Iterator<Carte> it = this.main.iterator();
		Carte carte = null;
		while (it.hasNext())
			carte = it.next();
			if (carte.estNormale())
				return true;
				
		return false;
	}


	/**
	 * Choisi la plus petite carte jouable dans la main du joueur
	 * @param derniereCarteJouee
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Carte getPlusPetite(Carte derniereCarteJouee) {
		Carte carte = null;
		
		// On trie par ordre croissant notre main
		List<Carte> mainTriee = new ArrayList<Carte>(this.getMain());
		Collections.sort(mainTriee);
		
		Iterator<Carte> it = mainTriee.iterator();
		//La première carte posable est donc la plus petite
		while (it.hasNext()){
			carte = it.next();
			if (derniereCarteJouee.estRecouvrablePar(carte))
				break;
				
		}
		
		return carte;
	}


	/**
	 * Permet d'avoir la plus petit carte de la main
	 * @return la plus petite carte de la main d'un joueur
	 */
	@SuppressWarnings("unchecked")
	public Carte getPlusPetite() {
		// On trie par ordre croissant la main
		List<Carte> mainTriee = new ArrayList<Carte>(this.getMain());
		//mainTriee.addAll(getMain());
		Collections.sort(mainTriee);
		// On retourne la première carte de la liste triée
		return mainTriee.get(0);
	
	}


	/**
	 * Permet d'avoir la première carte spéciale dans 
	 * la main du joueur
	 * @return carte spéciale
	 */
	public Carte getCarteSpecialeMain() {
		Iterator<Carte> it = this.getMain().iterator();
		Carte carte = null;
		while (it.hasNext()) {
			carte = it.next();
			if (carte.estSpeciale()) {
				break;
			}
		}
		return carte;
	}


	/**
	 * Le joueur ramasse le tas
	 */
	public void ramasserTas() {
		String message = this.getNom() + " ramasse le tas car il ne peut pas jouer";
		//On notifie les logs de la vue
		setChanged();
		notifyObservers(message);
		
		//Le joueur ramasse
		Iterator<Carte> it = Bataille.getInstance().getTable().iterator();
		while (it.hasNext()) {
			this.addMain(it.next());
		}
		
		//On notifie la vue pour màj du tas et du joueur
		setChanged();
		notifyObservers(this);
		
		Bataille.getInstance().clearTable();
		

		
	}


	/**
	 * Permet de vérifier la présence d'une carte dans la main
	 * @param i valeur de la carte que l'on cherche
	 * @return vrai si oui; faux si non
	 */
	public boolean possede(int i) {
		Iterator<Carte> it = this.getMain().iterator();
		while (it.hasNext()) {
			Carte carte = (Carte) it.next();
			if (carte.getValeur() == i) return true;
		}
		return false;
	}
	
	
	/**
	 * Permet de savoir s'il possède une carte dans son tas face visible
	 * @param carte carte que l'on cherche
	 * @return vrai ou faux
	 */
	public boolean possedeFaceUp(Carte carte) {
		Iterator<Carte> it = this.getFaceUp().iterator();
		Carte c = null;
		while (it.hasNext()){
			c = it.next();
			if (c.getCouleur() == carte.getCouleur() && c.getValeur() == carte.getValeur())
				return true;
		}
		
		return false;
	}
	
	
	/**
	 * Permet de savoir s'il possède une carte dans sa main
	 * @param carte carte que l'on cherche
	 * @return vrai ou faux
	 */
	public boolean possedeMain(Carte carte) {
		Iterator<Carte> it = this.getMain().iterator();
		Carte c = null;
		while (it.hasNext()){
			c = it.next();
			if (c.getCouleur() == carte.getCouleur() && c.getValeur() == carte.getValeur())
				return true;
		}
		
		return false;
	}
	
	
	/**
	 * Permet de savoir si la main est vide
	 * @return vrai ou faux
	 */
	public boolean estMainVide () {
		return this.getMain().isEmpty();
	}
	
	/**
	 * Permet de savoir si faceUp est vide
	 * @return vrai ou faux
	 */
	public boolean estFaceUpVide () {
		return this.getFaceUp().isEmpty();
	}
	
	
	/**
	 * Permet de savoir si faceDown est vide
	 * @return vrai ou faux
	 */
	public boolean estFaceDownVide () {
		return this.getFaceDown().isEmpty();
	}


	/**
	 * Remplit la main du joueur
	 * avec les cartes faceUp
	 */
	public void remplirMainAvecFaceUp() {
		Iterator<Carte> it = this.getFaceUp().iterator();
		while (it.hasNext()) {
			Carte carte = (Carte) it.next();
			this.addMain(carte);
		}
		this.getFaceUp().clear();
		//màj de la vue
		setChanged();
		notifyObservers(this);

		
	}


	/**
	 * Permet de connaitre la position du joueur
	 * @return position du joueur
	 */
	public int getPosition() {
		Bataille bataille = Bataille.getInstance();
		Iterator<Joueur> it = bataille.getJoueurs().iterator();
		int i = 0;
		while (it.hasNext()) {
			if (it.next() == this) return i;
			i++;
		}
				
		return 0;
	}


	
	
	

}
