import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.Scrambler;


public class JeuConsole {
	
	
	public static void main(String[] args) {

		Joueur Benjamin = new Joueur("Benjamin");
		Joueur Diane = new Joueur("Diane");
		Joueur Maxime = new Joueur("Maxime");
		Joueur Thomas = new Joueur("Thomas");
		Joueur Lou = new Joueur("Lou");
		Scrambler Alexandre = new Scrambler("Alexandre");
		
		Bataille bataille = new Bataille();
		
		bataille.addJoueur(Benjamin);
		bataille.addJoueur(Diane);
		bataille.addJoueur(Maxime);
		bataille.addJoueur(Thomas);
		bataille.addJoueur(Lou);
		bataille.addJoueur(Alexandre);
		
		Alexandre.distribuerPaquet(bataille);
		
		System.out.println(bataille.toString());
		//System.out.println(Joueur.NB_JOUEURS);
		
	
	}
	
	
	
}
