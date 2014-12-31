package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Représente le joueur capable de mélanger
 * @author daussy
 *
 */
public class Scrambler extends Joueur {

	/**
	 * Constructeur
	 * @param nom nom du joueur/scrambler
	 */
	public Scrambler(String nom) {
		super(nom);
	}
	
	/**
	 * Constructeur
	 * @param nom du joueur
	 * @param strategie à adopter
	 */
	public Scrambler(String nom, StrategieJeu strategie){
		super(nom, strategie);
	}
	
	/**
	 * Mélange le paquet de carte et fait
	 * la disitribution à tous les joueurs
	 * @param bataille
	 */
	public void distribuerPaquet() {
		Bataille bataille = Bataille.getInstance();
		//Construction du jeu en fonction du nombre de joueurs
		JeuDeCartes jdc = bataille.construireJeuDeCartes();
		//On mélange les cartes
		jdc.melanger();
		ArrayList<Joueur> joueurs = bataille.getJoueurs();
		int nbCartesDistribuees = 0;
		
		
		// tant que le paquet n'est pas vide
		while (!jdc.estVide()) {
			Iterator<Joueur> itj = joueurs.iterator();
			while (itj.hasNext()) {		
				((Joueur) itj.next()).recevoirCarte(jdc.tirerCarteDessus());
				nbCartesDistribuees++;
			}
			//Si on a distribué 9 cartes à chaque joueur on peut sortir de la boucle
			//et mettre tout le reste des cartes dans la pioche
			if (nbCartesDistribuees == (joueurs.size() * 9))
				break;
		}
		
		//Met le reste dans le tas s'il reste des cartes
		if (!jdc.estVide()) {
			ArrayList<Carte> pioche = new ArrayList<>();
			Iterator<Carte> it = jdc.getTas().iterator();
			//Pour toutes les cartes restantes
			while(it.hasNext())
				//on les ajoute dans la pioche (liste temporaire)
				pioche.add(it.next());
			
			//On place cette liste temporaire dans l'attribut pioche de la Bataille
			bataille.setPioche(pioche);
		}
		
		
		
		
	}
	

	@Override
	public String toString() {
		return "Scrambler " + super.toString();
	}
	
	
	

}
