package fr.utt.isi.lo02.projet.modele;

public class Bluffer implements StrategieJeu {

	@Override
	public int jouerCarte(Joueur joueur) {
		System.out.println(joueur.getNom() + " ne sait pas encore jouer seul (bluffer)");
		return 0;
	}

	@Override
	public void echangerCartes(Joueur joueur) {
		System.out.println(joueur.getNom() + " ne sait pas encore echanger ses cartes (bluffer)");
		
	}

	@Override
	public Joueur choisirQuiRalentir() {
		// TODO Auto-generated method stub
		return null;
	}

}
