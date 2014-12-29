package fr.utt.isi.lo02.projet.vue;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.utt.isi.lo02.projet.controleur.BatailleControleur;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.StrategieJeu;

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
	private JPanel main;
	
	
	private LinkedList<VueCarte> carteGraphique = new LinkedList<>();
	
	/**
	 * Label nom du joueur
	 */
	private JLabel nom;
	
	
	private BatailleControleur controleur;
	

	
	
	
	public VueJoueur(Joueur joueur) {
		//this.controleur = controleur;
		this.joueur = joueur;
		this.joueur.addObserver(this);
		this.joueur.getStrategie().addObserver(this); 
		this.nom = new JLabel(joueur.getNom());
		
		main = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		//TODO à cause du nom qui n'est pas de la même taille, ce n'est pas forcément aligné..
		main.setLayout(fl);
		
		dessinerCarteMain();
		
		//JScrollPane jsp = new JScrollPane(main);
		
		
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
		JLabel typeCarte = new JLabel(joueur.getNom());
		//TODO faire en sorte que les cartes soient alignées sur la gauche
		main.add(typeCarte);
	
		
		LinkedList<Carte> cartesJoueur = joueur.getMain();
		Iterator<Carte> it = cartesJoueur.iterator();
		
		while (it.hasNext()) {
			VueCarte vc = new VueCarte(it.next());
			carteGraphique.add(vc);
			JLabel carte = vc.getImage();
			main.add(carte);
		}
		
		//Si pas de carte on ajoute quand même
		//une fausse carte, afin de garder la
		//mise en page
		if (cartesJoueur.size() == 0)
			main.add(new JLabel(new ImageIcon("img/cartevide.png")));
		
	}
	
	
	public void majCartesMain() {
		main.removeAll();
		dessinerCarteMain();
		main.updateUI(); // Sans ça, ça ne mettait pas à jour :(
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
		return main;
	}



	public void setJpanel(JPanel main) {
		this.main = main;
	}
	
	
	


	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Joueur) { //échange de cartes
			this.majCartesMain();
		} else if (arg instanceof StrategieJeu) { //jeu
			this.majCartesMain();
		} else if (arg instanceof String) {
			BatailleControleur.updateJTextArea((String)arg);
		}
	}
	
	

	
	
	
	
}
