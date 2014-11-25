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
	
	
	public void distribuerPaquet(Bataille bataille) {
		JeuDeCartes jdc = bataille.getJeuDeCartes();
		 ArrayList<Joueur> joueurs = bataille.getJoueurs();
		
		
		
		//tant que le paquet n'est pas vide
		while (!jdc.estVide() || jdc.size() == joueurs.size() * 9) {
			//Iterator de joueur
			Iterator<Joueur> itj = joueurs.iterator();
			while (itj.hasNext()) {		
				((Joueur) itj.next()).recevoirCarte(jdc.tirerCarteDessus());
			}
		}
		
		//Met le reste dans le tas
		if (!jdc.estVide()) {
			ArrayList<Carte> pioche = new ArrayList<>();
			Iterator<Carte> it = jdc.getTas().iterator();
			while(it.hasNext())
				pioche.add(it.next());
			
			bataille.setPioche(pioche);
		}
		
			
		
		
	
		//TODO jdc.setPioche(cequilreste)
	}
	

	@Override
	public String toString() {
		return "Scrambler " + super.toString();
	}
	
	
	

}
