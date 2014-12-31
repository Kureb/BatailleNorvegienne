import fr.utt.isi.lo02.projet.controleur.BatailleControleur;
import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.StrategieRapide;
import fr.utt.isi.lo02.projet.modele.StrategieSpeciale;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.Scrambler;
import fr.utt.isi.lo02.projet.vue.VueBataille;


public class JeuGraphique implements Runnable {
	
	
 
	
	public JeuGraphique() {
		Bataille bataille = Bataille.getInstance(); //pas sûr..
		bataille.init();
		VueBataille vueBataille = new VueBataille(bataille);
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
		/*
		Thread partie = new Thread(new JeuGraphique());
		partie.start();
		javax.swing.SwingUtilities.invokeLater (new Runnable () {
			public void run () {
				*/new JeuGraphique();/*
			}
		});
*/		
	}


}
