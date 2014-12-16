package fr.utt.isi.lo02.projet.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Permet de définir comment va jouer un joueur réel
 * @author daussy
 *
 */
public class StrategieRelle implements StrategieJeu {

	@Override
	public int jouerCarte(Joueur joueur) {
		//System.out.println(joueur);
		ArrayList<Carte> cartes = this.choisirCarteAJoueur(joueur);
		int taille = cartes.size(); //nb de cartes à piocher
		Iterator<Carte> it = cartes.iterator();
		while (it.hasNext()) {
			joueur.poserCarteUnique(it.next());
		}
		
		
		
		boolean vide = Bataille.getInstance().getPioche().isEmpty();
		//Si la pioche n'est pas vide
		if (!vide) {
			// Pour chq carte posée
			for (int i = 0; i < taille; i++) {
				// On redonne au joueur une carte de la pioche
				if (!vide) {
					joueur.recevoirCarte(Bataille.getInstance().getPioche()
							.get(Bataille.getInstance().getPioche().size() - 1));
					Bataille.getInstance()
							.getPioche()
							.remove(Bataille.getInstance().getPioche().size() - 1);
				}
			}
		}
		
		return cartes.size();
		
		
		
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
		Carte derniereCarteJouee = null;
		int choix = -1;
		
		
		// Si la table n'est pas vide on lui montre la dernière carte jouée
		if (!Bataille.getInstance().getTable().isEmpty()) {
			int indexDernierElement = Bataille.getInstance().getTable().size()-1;
			derniereCarteJouee = Bataille.getInstance().getTable().get(indexDernierElement);
			System.out.println("La derniere carte jouee etait : "
					+ derniereCarteJouee);

		}
		boolean choix_ok = false;
		
		// On lui montre ses cartes et on demande ce qu'il veut jouer
		System.out.println(joueur.getNom() + " : " + joueur.getMain());

		do {
			do {
				System.out.print("Quelle carte veux-tu jouer ? ");
				choix = sc.nextInt();
				//Si y'a une carte sur la table on vérifie que
				//la carte choisie peut être posée dessus
				if (derniereCarteJouee != null)
					choix_ok = derniereCarteJouee.estRecouvrablePar(joueur
							.getMain().get(choix - 1));
				else
					choix_ok = true;
			} while (choix > joueur.getMain().size());
			if (!choix_ok)
				System.out.println("Tu ne peux pas poser cette carte.");
		} while (!choix_ok);
		//On ajoute son choix à la liste des cartes que retourne la méthode
		cartes.add(joueur.getMain().get(choix-1));

		
		// Ici on vérifie s'il a dans sa main une autre carte de la même valeur
		// car il peut aussi la jouer
		// Si tel est le cas, on lui demande s'il veut la jouer
		// Si oui, on l'ajoute aussi à la liste de cartes retournée par la méthode
		
		
		//On crée une copie de la main auquelle on enlève la carte choisie
		LinkedList<Carte> copyOfMain = new LinkedList<>(joueur.getMain());
		copyOfMain.remove(cartes.get(0));
		
		Iterator<Carte> it = copyOfMain.iterator();
		while(it.hasNext()) {
			int reponse = 0;
			boolean autre = false;
			Carte carteCourante = it.next();
			autre = (cartes.get(0)).aMemeValeur(carteCourante);
			if (autre) {
				System.out.println("Tu as une carte de la meme valeur.");
				do {
					System.out.print("Poser " + carteCourante + " (1 (oui)/2 (non)) ");
					reponse = sc.nextInt();
				} while (reponse != 1 && reponse != 2);
				
				
				if (reponse == 1)
					cartes.add(carteCourante);
				
				
			}
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

	@Override
	//TODO seulement si le tas n'est pas vide
	public Joueur choisirQuiRalentir() {
		System.out.println("A qui veux-tu envoyer le tas ? (numero)");
		System.out.println(Bataille.getInstance().getJoueurs().toString());
		int rep = -1;
		Scanner sc = new Scanner(System.in);
		do {
			rep = sc.nextInt();
		} while (rep > Bataille.getInstance().getJoueurs().size()-1 && rep < 0);
		rep--;
		
		return Bataille.getInstance().getJoueurs().get(rep);
	}

}
