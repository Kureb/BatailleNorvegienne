import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Bluffer;
import fr.utt.isi.lo02.projet.modele.Defausser;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.Scrambler;


public class JeuConsole {
	
	
	public static void main(String[] args) {

		Joueur Benjamin = new Joueur("Benjamin", new Bluffer());
		Joueur Diane = new Joueur("Diane", new Defausser());
		
		// TODO : ajouter une exception si plus d'un joueur physique ? Ou si plusieurs Scrambler ?
		// ou si Pas de joueur physique
		
		Joueur Maxime = new Joueur("Maxime", new Bluffer());
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
