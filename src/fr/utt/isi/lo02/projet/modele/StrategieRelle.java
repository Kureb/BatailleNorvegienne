package fr.utt.isi.lo02.projet.modele;

import java.util.Scanner;

/**
 * Permet de définir comment va jouer un joueur réel
 * @author daussy
 *
 */
public class StrategieRelle implements StrategieJeu {

	@Override
	public void jouerCarte(Joueur joueur) {
		//Chercher si y'a déjà un tas !
		System.out.println("il joue");
		if (Bataille.getInstance().getTable().isEmpty()) {
			//si le tas est vide, on est donc le premier joueur à jouer
			//Il peut poser 1, 2 ou 3 cartes sur la table (si elles ont la même valeur)
			
			//TODO lui laisser choisir la carte
			joueur.poserCarteUnique(joueur.getMain().getFirst());
			//joueur.poserCarte(joueur.getMain());
			
		} else {
			//il faut jouer en fonction de la dernière carte posée sur le tas
			System.out.println("Il faut implementer les tours suivant !!");
			joueur.poserCarteUnique(joueur.getMain().getFirst());
			
		}
		
		

	}

	@Override
	public void echangerCartes(Joueur joueur) {
		// TODO trouver qqch pour éviter de passer l'objet Joueur en paramètre
		int rep;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Veux tu echanger des cartes "
					+ joueur.getNom() + "\t(1 (oui)/ 2 (non))");
			rep = sc.nextInt();
			
			if (rep == 1)
				joueur.proposerChangerCartes();
			
		} while (rep != 2);
	}

}
