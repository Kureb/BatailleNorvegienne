package fr.utt.isi.lo02.projet.vue;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.StrategieRapide;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.Scrambler;
import fr.utt.isi.lo02.projet.modele.StrategieRelle;

/**
 * Représente graphiquement un joueur
 * @author daussy
 *
 */
public class VueJoueur implements Observer{
	
	/**
	 * Le joueur a représenter 
	 */
	private Joueur joueur; //modele
	
	/**
	 * Panel du joueur, pour placer ses cartes !
	 */
	private JPanel jpanel;
	
	
	private LinkedList<VueCarte> carteGraphique = new LinkedList<>();
	
	/**
	 * Label nom du joueur
	 */
	private JLabel nom;
	

	
	
	
	public VueJoueur(Joueur joueur) {
		this.joueur = joueur;
		this.joueur.addObserver(this);
		this.nom = new JLabel(joueur.getNom());
		
		
		jpanel = new JPanel();
		jpanel.setLayout(new FlowLayout());
		
		
		
		
		
		//LinkedList<Carte> cartes = joueur.getMain();
		//Iterator<Carte> it = cartes.iterator();
		
		dessinerCarteMain();
		
//		while (it.hasNext()) {
//			VueCarte vc = new VueCarte(it.next());
//			carteGraphique.add(vc);
//			final JLabel carte = vc.getImage(); //pour le setBorder
//			if (joueur.getStrategie() instanceof StrategieRelle) {
//				carte.addMouseListener(new MouseAdapter() {
//					//TODO remplir
//					public void mouseClicked(MouseEvent me) {
//						if (carte.getBorder() == null) 
//							carte.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
//						else
//							carte.setBorder(null);
//					}
//					
//					
//					
//				});
//			}
//			jpanel.add(carte);
		
			//jpanel.add(nom);
			
			
//		}
		
	}
	
	
	public void dessinerCarteMain() {
		jpanel.removeAll();

		JLabel typeCarte = new JLabel("Main " + joueur.getNom());
		jpanel.add(typeCarte);
		
		LinkedList<Carte> cartesJoueur = joueur.getMain();
		Iterator<Carte> it = cartesJoueur.iterator();
		
		int index = 0;
		while (it.hasNext()) {
			VueCarte vc = new VueCarte(it.next());
			JLabel carte = vc.getImage();
			//if (vc != this.carteGraphique.get(index)) {
			//	jpanel.add(vc.getImage());
			//}
			//this.carteGraphique.get(index).setImage(vc.getImage());
			jpanel.add(carte);
			index++;
			
		}
		
			
			
	}

	


	public JLabel getNom() {
		return nom;
	}




	public void setNom(JLabel nom) {
		this.nom = nom;
	}




	public Joueur getJoueur() {
		return joueur;
	}



	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}



	public JPanel getJpanel() {
		return jpanel;
	}



	public void setJpanel(JPanel jpanel) {
		this.jpanel = jpanel;
	}
	
	
	public static void main(String[] args) {
		Bataille bataille = Bataille.getInstance();
		
		Scrambler Alex = new Scrambler("Alex");
		bataille.addJoueur(new Joueur("Lol", new StrategieRapide()));
		bataille.addJoueur(new Joueur("Lol2", new StrategieRapide()));
		bataille.addJoueur(Alex);
		
		Alex.distribuerPaquet(bataille);
		
		//bataille.lancerPartie();
		
		JFrame fenetre = new JFrame("Test Joueur");
		fenetre.setLayout(new FlowLayout());
		Container reservoir = fenetre.getContentPane();

		VueJoueur vj = new VueJoueur(Alex);
		reservoir.add(vj.getJpanel());
		
		//vj.carteGraphique.get(0).setImage(new VueCarte(new Carte(0, 0)).getImage());
		
		

		//this.carteGraphique.get(index).setImage(vc.getImage());
		//reservoir.add(carteGraphique2.getImage()); 
		
		
		
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setVisible(true);
		
		
	}


	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Joueur) {
			int position = ((Joueur) arg).getPosition();
			//texte.setText("echange de carte en cours");
			this.dessinerCarteMain();
		}
	}
	
	
	
	
	
}
