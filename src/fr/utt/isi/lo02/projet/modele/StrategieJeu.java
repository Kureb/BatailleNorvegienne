package fr.utt.isi.lo02.projet.modele;

/**
 * Interface Stratégie
 * 3 stratégies différentes
 * @author daussy
 *
 */
public interface StrategieJeu {
	
	public abstract void echangerCartes(Joueur joueur);

	public abstract void jouerCarte(Joueur joueur);

	
	
}
