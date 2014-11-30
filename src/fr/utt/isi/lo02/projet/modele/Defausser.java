package fr.utt.isi.lo02.projet.modele;

public class Defausser implements StrategieJeu {

	@Override
	public void jouerCarte(Joueur joueur) {
		System.out.println("il ne sait pas encore jouer seul(defausser)");
	}

	@Override
	public void echangerCartes(Joueur joueur) {
		System.out.println("il ne sait pas encore echanger ses cartes (defausser)");
	}

}
