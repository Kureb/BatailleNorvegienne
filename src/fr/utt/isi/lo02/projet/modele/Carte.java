package fr.utt.isi.lo02.projet.modele;

/**
 * Classe modélisant une carte
 * @author daussy
 * source : utt moodle
 */

public class Carte {

	public final static int PIC = 0;
	public final static int COEUR = 1;
	public final static int CARREAU = 2;
	public final static int TREFLE = 3;

	public final static int DEUX = 0;
	public final static int TROIS = 1;
	public final static int QUATRE = 2;
	public final static int CINQ = 3;
	public final static int SIX = 4;
	public final static int SEPT = 5;
	public final static int HUIT = 6;
	public final static int NEUF = 7;
	public final static int DIX = 8;
	public final static int VALET = 9;
	public final static int DAME = 10;
	public final static int ROI = 11;
	public final static int AS = 12;

	public final static String[] COULEURS = {"Pic", "Coeur", "Carreau", "Trefle"};
	public final static String[] VALEURS = {"Deux", "Trois", "Quatre", "Cinq", "Six", "Sept", "Huit", "Neuf", "Dix", "Valet", "Dame", "Roi", "As"};

	private int couleur;
	private int valeur;

	/**
	 * Constructeur d'une carte à partir de deux entiers
	 * @param valeur - valeur de la carte
	 * @param couleur - couleur de la carte
	 */
	public Carte (int valeur, int couleur) {
		this.setCouleur(couleur);
		this.setValeur(valeur);
	}
	
	
	/**
	 * Constructeur d'une carte à partir de chaînes de caractères
	 * @param valeur - valeur de la carte
	 * @param couleur - couleur de la carte
	 */
	public Carte (String valeur, String couleur) {

		int i = 0;
		int c = 0, v = 0;
		boolean trouve = false;

		while (i < Carte.COULEURS.length && trouve == false) {
			if (couleur.equals(Carte.COULEURS[i])) {
				trouve = true;
				c = i;
			} else {
				i++;
			}
		}

		i = 0;
		trouve = false;

		while (i < Carte.VALEURS.length && trouve == false) {
			if (valeur.equals(Carte.VALEURS[i])) {
				trouve = true;
				v = i;
			} else {
				i++;
			}
		}

		this.setCouleur(c);
		this.setValeur(v);
	}

	
	/**
	 * Getter de la couleur
	 * @return int- la couleur de la carte
	 */
	public int getCouleur() {
		return couleur;
	}

	
	/**
	 * Fixe la couleur de la carte
	 * @param couleur - couleur à appliquer à la carte
	 */
	public void setCouleur(int couleur) {
		if (couleur >= Carte.PIC && couleur <= Carte.TREFLE) {
			this.couleur = couleur;
		}
	}

	
	/**
	 * Getter de la valeur
	 * @return int - la valeur de la carte
	 */
	public int getValeur() {
		return valeur;
	}

	
	/**
	 * Fixe la valeur de la carte
	 * @param valeur - valeurà appliquer à la carte
	 */
	public void setValeur(int valeur) {
		if (valeur >= Carte.DEUX && valeur <= Carte.AS) {
			this.valeur = valeur;
		}
	}


	@Override
	public String toString() {
		return VALEURS[valeur] + " de " + COULEURS[couleur];
	}
	
	public boolean aMemeValeur(Carte carte) {
		return (this.getValeur() == carte.getValeur());
	}
	
	
	public boolean estSpeciale() {
		boolean bool = false;
		if (this.valeur == 0) bool = true;
		if (this.valeur == 5) bool = true;
		if (this.valeur == 6) bool =  true;
		if (this.valeur == 8) bool = true;
		if (this.valeur == 12) bool = true;
		return bool;
	}
	
	/*
	 * On pose carte sur this
	 */
	public boolean estRecouvrablePar(Carte carte) {
		/*
		boolean bool = false;
		boolean estSpeciale = false;
		if (this.estSpeciale())
			estSpeciale = true;
		if (estSpeciale) {
			if (this.valeur == 5)
				if (carte.getValeur() > this.getValeur())
					bool = false;
				else if (carte.getValeur() <= this.getValeur())
					bool = true;

		} else if (carte.getValeur() >= this.getValeur())
			bool = true;
		
		*/
		
		if (carte.valeur == 0) return true; //si c'est un 2
		else if (this.valeur == 5 && carte.valeur <= 5 ) return true; //si y'avait un 7 avant
		else if (this.valeur == 5 && carte.valeur > 7) return false;
		else if (this.valeur == 12 && carte.getValeur() == 12) return true;
		else if (this.valeur == 12 && carte.getValeur() == 0) return true;
		else if (this.getValeur() <= carte.getValeur()) return true;
		else return false;

	}



}
