import fr.utt.isi.lo02.projet.controleur.BatailleControleur;
import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.vue.VueBataille;

/**
 * Classe permettant de lancer le jeu en mode graphique
 * Implémente Runnable afin de lancer dans un nouveau Thread
 * @author daussy
 *
 */

public class JeuGraphique implements Runnable {
	
	
 
	/**
	 * Constructeur du jeu
	 * On fait le lien entre le Modèle, la Vue et le Controleur
	 */
	public JeuGraphique() {
		Bataille bataille = Bataille.getInstance();
		bataille.init();
		VueBataille vueBataille = new VueBataille(bataille);
		@SuppressWarnings("unused")
		BatailleControleur batailleControleur = new BatailleControleur(bataille, vueBataille);
		bataille.addObserver(vueBataille);
		bataille.echangerCartes();
		bataille.lancerPartie();
	}

	
	@Override
	public void run() {
		new JeuGraphique();
	}
	
	public static void main(String[] args) {
		
		Thread partie = new Thread(new JeuGraphique());
		partie.start();
		javax.swing.SwingUtilities.invokeLater (new Runnable () {
			public void run () {
				new JeuGraphique();
			}
		});

	}


}
