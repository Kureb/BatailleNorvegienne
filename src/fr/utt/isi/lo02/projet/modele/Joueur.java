package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Représente le Joueur
 * 
 * @author daussy - obeidat
 * 
 */
public class Joueur extends BatailleAbstraite{

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
		boolean result = false;
		Carte carte = null;
		
		Iterator<Carte> it = this.getMain().iterator();
		while (it.hasNext())
			carte = it.next();
			if (derniereCarte.estRecouvrablePar(carte))
				return true;
		
		return result;
	}
	
	
	public boolean peutJouer() {
		if (Bataille.getInstance().getTable().isEmpty()) return true;
		
		int index = Bataille.getInstance().getTable().size()-1;
		Carte derniere = Bataille.getInstance().getTable().get(index);
		return peutJouer(derniere);
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
		// On vérifie que les cartes sont bien à l'endroit indiqué TODO essayer
		if (this.faceUp.contains(carteVisible) && this.main.contains(carteMain)) {
			this.faceUp.add(carteMain);
			this.main.remove(carteMain);
			this.main.add(carteVisible);
			this.faceUp.remove(carteVisible);
			
			setChanged();
			notifyObservers(this);
			
		}
		
		
	}

	/**
	 * Permet de proposer l'échange de cartes à un joueur
	 */
	public void proposerChangerCartes() {
		
		//new proposerChangerCartesVue(this);
		
		
		//System.out.println(this.toString());
		System.out.println(this);
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

	
	public boolean verifierGagner() {
		return (this.main.isEmpty() && this.faceDown.isEmpty() && this.faceUp.isEmpty());
	}
	
	
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

	public void setFaceUp(LinkedList<Carte> faceUp) {
		this.faceUp = faceUp;
	}



	public void setFaceDown(LinkedList<Carte> faceDown) {
		this.faceDown = faceDown;
	}



	public void setMain(LinkedList<Carte> main) {
		this.main = main;
	}
	
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
	

	public void setStrategieJeu(StrategieJeu strategie) {
		if (this.strategie != null)
			this.strategie = strategie;
		// TODO : else en faire une exception ? Un joueur réel ne peut devenir
		// un joueur virtuel
		// et vérifier aussi le != null InstanceOf ?
	}



	public void envoyerTas(Joueur suivant) {
		if (!suivant.peutContrerAs()) {
			String message = this.getNom() + " envoie le tas à " + suivant.getNom();
			System.out.println(message.replace("à", "a"));
			
			setChanged();
			notifyObservers(message);
			
			Iterator<Carte> it = Bataille.getInstance().getTable().iterator();
			while (it.hasNext()) {
				suivant.addMain(it.next());
			}
			Bataille.getInstance().clearTable();
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
	public Carte getPlusPetite() {
		// On trie par ordre croissant la main
		List<Carte> mainTriee = new ArrayList<Carte>(this.getMain());
		//mainTriee.addAll(getMain());
		Collections.sort(mainTriee);
		// On retourne la première carte de la liste triée
		return mainTriee.get(0);
	
	}



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



	public void ramasserTas() {
		String message = this.getNom() + " ramasse le tas car il ne peut pas jouer";
		System.out.println(message);
		
		setChanged();
		notifyObservers(message);
		
		Iterator<Carte> it = Bataille.getInstance().getTable().iterator();
		while (it.hasNext()) {
			this.addMain(it.next());
		}
		
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
	
	
	
	public boolean estMainVide () {
		return this.getMain().isEmpty();
	}
	
	
	public boolean estFaceUpVide () {
		return this.getFaceUp().isEmpty();
	}
	
	public boolean estFaceDownVide () {
		return this.getFaceDown().isEmpty();
	}



	public void remplirMainAvecFaceUp() {
		Iterator<Carte> it = this.getFaceUp().iterator();
		while (it.hasNext()) {
			Carte carte = (Carte) it.next();
			this.addMain(carte);
		}
		this.getFaceUp().clear();

		
	}



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
