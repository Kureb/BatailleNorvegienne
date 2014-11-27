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
		jdc.melanger();
		ArrayList<Joueur> joueurs = bataille.getJoueurs();
		int nbCartesDistribuees = 0;
		
		
		// tant que le paquet n'est pas vide
		while (!jdc.estVide()) {
			//Iterator de joueur
			Iterator<Joueur> itj = joueurs.iterator();
			while (itj.hasNext()) {		
				((Joueur) itj.next()).recevoirCarte(jdc.tirerCarteDessus());
				nbCartesDistribuees++;
			}
			//Si on a distribué 9 cartes à chaque joueur on peut sortir de la boucle
			//et mettre tout le reste des cartes dans la pioche
			if (nbCartesDistribuees == (joueurs.size() * 9))
				//Met le reste dans le tas s'il reste des cartes
				if (!jdc.estVide()) {
					ArrayList<Carte> pioche = new ArrayList<>();
					Iterator<Carte> it = jdc.getTas().iterator();
					while(it.hasNext())
						pioche.add(it.next());
					
					bataille.setPioche(pioche);
				}
				break;
		}
		
		
		
		
	}
	

	@Override
	public String toString() {
		return "Scrambler " + super.toString();
	}
	
	
	

}
