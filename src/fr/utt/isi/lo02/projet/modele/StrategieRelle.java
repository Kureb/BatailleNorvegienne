package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import fr.utt.isi.lo02.projet.controleur.BatailleControleur;


/**
 * Permet de définir comment va jouer un joueur réel
 * @author daussy
 *
 */
public class StrategieRelle extends StrategieJeu {

		
	

	/**
	 * Permet au joueur réel de choisir la carte qu'il veut jouer
	 * @param joueur Joueur courant (réel)
	 * @return Carte qu'il souhaite jouer
	 */
	public ArrayList<Carte> choisirCarteAJouer(Joueur joueur) {
		// L'arraylist qui contient les cartes qu'il veut poser
		
		//BatailleControleur.fenetreChoixCarteJouer(joueur);
		
		//return new ArrayList<Carte>();
		
		//Carte carte = null;
		ArrayList<Carte> cartes = new ArrayList<>();
		//Carte carte = BatailleControleur.fenetreChoixCarteJouer(joueur);
		//cartes.add(carte);
		//return cartes;
		
		//Scanner sc = new Scanner(System.in);
		//Carte derniereCarteJouee = null;
		//int choix = -1;
		
		
		// Si la table n'est pas vide on lui montre la dernière carte jouée
		//if (!Bataille.getInstance().getTable().isEmpty()) {
			//int indexDernierElement = Bataille.getInstance().getTable().size()-1;
			//derniereCarteJouee = Bataille.getInstance().getTable().get(indexDernierElement);
			//System.out.println("La derniere carte jouee etait : "
			//		+ derniereCarteJouee);

		//}
		//boolean choix_ok = false;
		
		// On lui montre ses cartes et on demande ce qu'il veut jouer
		//System.out.println(joueur.getNom() + " : " + joueur.getMain());

		//do {
		//	do {
				//System.out.print("Quelle carte veux-tu jouer ? ");
		cartes = BatailleControleur.fenetreChoixCarteJouer(joueur);
				//Si y'a une carte sur la table on vérifie que
				//la carte choisie peut être posée dessus
				//if (derniereCarteJouee != null)
					//choix_ok = derniereCarteJouee.estRecouvrablePar(carte);
				//else
				//	choix_ok = true;
		//	} while (choix > joueur.getMain().size());
			//if (!choix_ok)
				//System.out.println("Tu ne peux pas poser cette carte.");
		//} while (!choix_ok);
		//On ajoute son choix à la liste des cartes que retourne la méthode
		//cartes.add(carte);

		
		// Ici on vérifie s'il a dans sa main une autre carte de la même valeur
		// car il peut aussi la jouer
		// Si tel est le cas, on lui demande s'il veut la jouer
		// Si oui, on l'ajoute aussi à la liste de cartes retournée par la méthode
		
		
		//On crée une copie de la main auquelle on enlève la carte choisie

		//LinkedList<Carte> copyOfMain = new LinkedList<>(joueur.getMain());
		//copyOfMain.remove(cartes.get(0));
		
		//Iterator<Carte> it = copyOfMain.iterator();
		//while(it.hasNext()) {
			//int reponse = 0;
			//boolean autre = false;
			//Carte carteCourante = it.next();
			//autre = (cartes.get(0)).aMemeValeur(carteCourante);
			//if (autre) {
				/*System.out.println("Tu as une carte de la meme valeur.");
				do {
					System.out.print("Poser " + carteCourante + " (1 (oui)/2 (non)) ");
					reponse = sc.nextInt();
				} while (reponse != 1 && reponse != 2);
				
				
				if (reponse == 1)*/
				//	cartes.add(carteCourante);
				
				
			//}
		//}
		
		String message = joueur.getNom() + " joue ";
		//System.out.print(joueur.getNom() + " joue ");
		Iterator<Carte> itt = cartes.iterator();
		while (itt.hasNext()) {
			message += itt.next() + " ";
			//System.out.print(itt.next() + " ");
		}
		//System.out.println(message);
		
		setChanged();
		notifyObservers(message);
		
		
		return cartes;
		
		
	}

	
	
	/**
	 * Permet de demander au joueur physique s'il
	 * veut échanger des cartes ou non
	 */
	@Override
	public void echangerCartes(Joueur joueur) {
		/*
		System.out.println(joueur);
		int rep;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print("Veux tu echanger des cartes "
					+ joueur.getNom() + "\t(1 (oui)/ 2 (non)) ? ");
			rep = sc.nextInt();
			
			if (rep == 1)
				joueur.proposerChangerCartes();
			
		} while (rep != 2);
	*/
		joueur.proposerChangerCartes();
	}

	@Override
	public Joueur choisirQuiRalentir(Joueur joueurActuel) {
		/*
		System.out.println("A qui veux-tu envoyer le tas ? (numero)");
		System.out.println(Bataille.getInstance().getJoueurs().toString());
		int rep = -1;
		Scanner sc = new Scanner(System.in);
		do {
			rep = sc.nextInt();
		} while (rep > Bataille.getInstance().getJoueurs().size()-1 && rep < 0);
		rep--;
		
		return Bataille.getInstance().getJoueurs().get(rep);
		
		*/
		
		Joueur joueur = BatailleControleur.fenetreChoixVictimeAs(joueurActuel);
		return joueur;
		
	}



	@Override
	// Donc il a un As ou un 2 ou les 2
	//Va choisir l'As en priorité et l'envoyer
	//au joueur possédant le moins de cartes en main
	public Carte choisirCarteContre(Joueur joueur) {
		//On appelle la fenetre de choix de carte
		//Car de toute façon une vérif est faite sur la faisabilité du mouvement
		
		ArrayList<Carte> cartes = BatailleControleur.fenetreChoixCarteJouer(joueur);
		return cartes.get(0);
		
		//TODO faire pareil que la méthode du dessus
		/*
		boolean deux = joueur.possede(0);
		boolean as = joueur.possede(12);
		int val = 0, rep = 0;
		Scanner sc = new Scanner(System.in);
		
		if (deux && !as) val = 0;
		if (!deux && as) val = 12;
		if (deux && as) {
			System.out.println("Que veux-tu poser, le Deux ou l'As ?");
			do {
				rep = sc.nextInt();
			} while (rep != 1 && rep != 2);
		if (rep == 1) val = 0; else val = 12;
		}
		
		Iterator<Carte> it = joueur.getMain().iterator();
		while (it.hasNext()) {
			Carte carte = (Carte) it.next();
			if (carte.getValeur() == val) return carte;
		}
		
		return null;*/
	}

}
