package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Permet de définir comment va jouer un joueur réel
 * @author daussy
 *
 */
public class StrategieRelle implements StrategieJeu {

	@Override
	public void jouerCarte(Joueur joueur) {
		System.out.println(joueur);
		ArrayList<Carte> cartes = this.choisirCarteAJoueur(joueur);
		int taille = cartes.size(); //nb de cartes à piocher
		Iterator<Carte> it = cartes.iterator();
		while (it.hasNext()) {
			joueur.poserCarteUnique(it.next());
		}
		
		// Pour chq carte posée
		for (int i = 0; i < taille; i++) {
			// On redonne au joueur une carte de la pioche
			joueur.recevoirCarte(Bataille.getInstance().getPioche().get(Bataille.getInstance().getPioche().size()-1));
			Bataille.getInstance().getPioche().remove(Bataille.getInstance().getTable().size()-1);
		}
		
		
		
	}

	/**
	 * Permet au joueur réel de choisir la carte qu'il veut jouer
	 * @param joueur Joueur courant (réel)
	 * @return Carte qu'il souhaite jouer
	 */
	public ArrayList<Carte> choisirCarteAJoueur(Joueur joueur) {
		// L'arraylist qui contient les cartes qu'il veut poser
		ArrayList<Carte> cartes = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int choix = -1;
		
		
		// Si la table n'est pas vide on lui montre la dernière carte jouée
		if (!Bataille.getInstance().getTable().isEmpty()) {
			int indexDernierElement = Bataille.getInstance().getTable().size()-1;
			Carte derniereCarteJouee = Bataille.getInstance().getTable().get(indexDernierElement);
			System.out.println("La derniere carte jouee etait : "
					+ derniereCarteJouee);

		}
		
		// On lui montre ses cartes et on demande ce qu'il veut jouer
		System.out.println(joueur.getMain());

		do {
			System.out.print("Quelle carte veux-tu jouer ? ");
			choix = sc.nextInt();
		} while (choix > joueur.getMain().size());

		choix--;
		//On ajoute son choix à la liste des cartes que retourne la méthode
		cartes.add(joueur.getMain().get(choix));

		
		// Ici on vérifie s'il a dans sa main une autre carte de la même valeur
		// car il peut aussi la jouer
		// Si tel est le cas, on lui demande s'il veut la jouer
		// Si oui, on l'ajoute aussi à la liste de cartes retournée par la méthode
		boolean autre = false;
		Iterator<Carte> it = joueur.getMain().iterator();
		int i = 1;
		int reponse = 0;
		while (it.hasNext()) {
			Carte carteCourrante = it.next();
			if (carteCourrante != cartes.get(0)) {
				autre = (cartes.get(0)).aMemeValeur(carteCourrante);
				if (autre) {
					System.out.println("Tu as une carte de la meme valeur.");
					do {
						System.out.print("Veux-tu aussi poser " + carteCourrante + " (1 (oui)/2 (non)) ? ");
						reponse = sc.nextInt();
					} while (reponse != 1 && reponse != 2);
					
					if (reponse == 1) {
						reponse = -1;
						cartes.add(carteCourrante);
					}
						
				}
			}
			i++;
		}

		return cartes;
	}

	
	
	/**
	 * Permet de demander au joueur physique s'il
	 * veut échanger des cartes ou non
	 */
	@Override
	public void echangerCartes(Joueur joueur) {
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
	}

}
