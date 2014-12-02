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
		
		/*
		Joueur Maxime = new Joueur("Maxime");
		Joueur Thomas = new Joueur("Thomas");
		Joueur Lou = new Joueur("Lou");
		Joueur Robin = new Joueur("Robin");
		Joueur test1 = new Joueur("test1");
		Joueur test2 = new Joueur("test2");
		Joueur test3 = new Joueur("test3");
		Joueur test4 = new Joueur("test4");
		Joueur test5 = new Joueur("test5");
		Joueur test6 = new Joueur("test6");
		*/
		Scrambler Alexandre = new Scrambler("Alexandre");
		
		Bataille bataille = Bataille.getInstance();
		
		
		
		/*
		bataille.addJoueur(Maxime);
		bataille.addJoueur(Thomas);
		bataille.addJoueur(Lou);
		bataille.addJoueur(Robin);
		bataille.addJoueur(test1);
		bataille.addJoueur(test2);
		bataille.addJoueur(test3);
		bataille.addJoueur(test4);
		bataille.addJoueur(test5);
		bataille.addJoueur(test6);
		*/
		bataille.addJoueur(Alexandre);
		bataille.addJoueur(Diane);
		bataille.addJoueur(Benjamin);
	
		Alexandre.distribuerPaquet(bataille);
		
		//Affiche les cartes de chaque joueurs
		//System.out.println(bataille.toString());
		
		
	
		
		// première phase de la bataille
		 bataille.echangerCartes();
		
		
		// seconde phase de la bataille
		 bataille.lancerPartie();
		
	
		
		
		
	
	}
	
	
	
}
