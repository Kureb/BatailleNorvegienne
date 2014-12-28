import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.StrategieRapide;
import fr.utt.isi.lo02.projet.modele.StrategieSpeciale;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.Scrambler;


public class JeuConsole {
	
	
	public static void main(String[] args) {

		
		Joueur Benjamin = new Joueur("Benjamin", new StrategieRapide());
		Joueur Diane = new Joueur("Diane", new StrategieSpeciale());
		
		// TODO : ajouter une exception si plus d'un joueur physique ? Ou si plusieurs Scrambler ?
		// ou si Pas de joueur physique
		
		Joueur Maxime = new Joueur("Maxime", new StrategieSpeciale());
		Scrambler Alexandre = new Scrambler("Alexandre");
		
		Bataille bataille = Bataille.getInstance();
		
		
		
		bataille.addJoueur(Alexandre);
		bataille.addJoueur(Diane);
		bataille.addJoueur(Benjamin);
		bataille.addJoueur(Maxime);
	
		Alexandre.distribuerPaquet(bataille);
		
		// première phase de la bataille
		 bataille.echangerCartes();
		
		
		// seconde phase de la bataille
		 bataille.lancerPartie();
		
		
		
		
		
	
	}
	
	
	
}
