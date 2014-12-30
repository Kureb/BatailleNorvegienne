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
		//VueBataille vueBataille = new VueBataille();
		
		
		
		
		
		
		Joueur Benjamin = new Joueur("Benjamin", new StrategieRapide());
		Joueur Diane = new Joueur("Diane", new StrategieSpeciale());
		
		// TODO : ajouter une exception si plus d'un joueur physique ? Ou si plusieurs Scrambler ?
		// ou si Pas de joueur physique
		
		Joueur Maxime = new Joueur("Maxime", new StrategieRapide());
		Scrambler Alexandre = new Scrambler("Alexandre");
		Joueur Robin = new Joueur("Robin", new StrategieRapide());
		
		
		
		bataille.addJoueur(Alexandre);
		bataille.addJoueur(Diane);
		bataille.addJoueur(Benjamin);
		bataille.addJoueur(Maxime);
		bataille.addJoueur(Robin);
	
		Alexandre.distribuerPaquet((Bataille) bataille);
		
		
		VueBataille vueBataille = new VueBataille(bataille);
		BatailleControleur batailleControleur = new BatailleControleur(bataille, vueBataille);
		bataille.addObserver(vueBataille);
		
		
		// première phase de la bataille
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
