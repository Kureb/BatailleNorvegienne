package fr.utt.isi.lo02.projet.modele;

public class Defausser implements StrategieJeu {

	@Override
	public int jouerCarte(Joueur joueur) {
		System.out.println(joueur.getNom() + " ne sait pas encore jouer seul(defausser)");
		return 0;
	}

	@Override
	public void echangerCartes(Joueur joueur) {
		System.out.println(joueur.getNom() + " ne sait pas encore echanger ses cartes (defausser)");
	}

	@Override
	public Joueur choisirQuiRalentir() {
		// TODO Auto-generated method stub
		return null;
	}

}
