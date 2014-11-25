package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Représente le joueur capable de mélanger
 * @author daussy - obeidat
 *
 */
public class Scrambler extends Joueur {

	public Scrambler(String nom) {
		super(nom);
	}
	
	
	public void distribuerPaquet(JeuDeCartes jdc, ArrayList<Joueur> joueurs) {
		//tant que le paquet n'est pas vide
		while (!jdc.estVide()) {
			//Iterator de joueur
			Iterator<Joueur> itj = joueurs.iterator();
			while (itj.hasNext()) {		
				((Joueur) itj.next()).recevoirCarte(jdc.tirerCarteDessus());
			}
		}
	}
	

	@Override
	public String toString() {
		return "Scrambler " + super.toString();
	}
	
	
	

}
