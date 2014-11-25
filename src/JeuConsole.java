import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.Scrambler;


public class JeuConsole {
	
	
	public static void main(String[] args) {
		
	
		Bataille bataille = new Bataille();

		Joueur Benjamin = new Joueur("Benjamin");
		Joueur Diane = new Joueur("Diane");
		Joueur Maxime = new Joueur("Maxime");
		Scrambler Alexandre = new Scrambler("Alexandre");
		
		
		bataille.addJoueur(Benjamin);
		bataille.addJoueur(Diane);
		bataille.addJoueur(Maxime);
		bataille.addJoueur(Alexandre);
		
		Alexandre.distribuerPaquet(bataille.getJeuDeCartes(), bataille.getJoueurs());
		
		System.out.println(bataille.toString());
	
	}
	
	
	
}
