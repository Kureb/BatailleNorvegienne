package fr.utt.isi.lo02.projet.modele;

public class Bluffer implements StrategieJeu {

	@Override
	public void jouerCarte(Joueur joueur) {
		System.out.println("il ne sait pas encore jouer seul (bluffer)");
		
	}

	@Override
	public void echangerCartes(Joueur joueur) {
		System.out.println("il ne sait pas encore echanger ses cartes (bluffer)");
		
	}

}
