package fr.utt.isi.lo02.projet.modele;

public class Defausser implements StrategieJeu {

	@Override
	public void jouerCarte(Joueur joueur) {
		System.out.println("Il ne sait pas encore jouer seul");
	}

	@Override
	public void echangerCartes(Joueur joueur) {
		System.out.println("La methode n'est pas encore implementee");
	}

}
