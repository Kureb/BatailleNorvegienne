package fr.utt.isi.lo02.projet.vue;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utt.isi.lo02.projet.controleur.BatailleControleur;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.StrategieJeu;
import fr.utt.isi.lo02.projet.modele.StrategieRelle;

/**
 * Représente graphiquement un joueur
 * @author daussy
 *
 */
public class VueJoueur implements Observer {
	
	/**
	 * Le joueur a représenter 
	 */
	private Joueur joueur; //modele
	
	/**
	 * Panel du joueur, pour placer ses cartes !
	 */
	private JPanel main;
	
	/**
	 * Liste des cartes graphiques du joueur
	 */
	private LinkedList<VueCarte> carteGraphique = new LinkedList<>();
	
	/**
	 * Label nom du joueur
	 */
	private JLabel nom;
	
	/**
	 * Controleur qui fait le lien entre la vue et le modèle
	 */
	private BatailleControleur controleur;
	

	
	
	/**
	 * Constructeur de la vue graphique d'un joueur
	 * @param joueur Joueur à représenter graphiquement
	 */
	public VueJoueur(Joueur joueur) {
		this.joueur = joueur;
		this.joueur.addObserver(this); // On doit observer le joueur
		this.joueur.getStrategie().addObserver(this);  // et sa stratégie
		this.nom = new JLabel(joueur.getNom());
		
		main = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		main.setLayout(fl);
		
		dessinerCarteMain(joueur);
	}
	
	
	/**
	 * Dessine les cartes du joueur
	 * @param j Joueur doit on doit dessiner les cartes
	 */
	public void dessinerCarteMain(Joueur j) {
		JLabel typeCarte = new JLabel(j.getNom());
		main.add(typeCarte);
	
		
		LinkedList<Carte> cartesJoueur = j.getMain();
		Iterator<Carte> it = cartesJoueur.iterator();
		/*
		 * Pour toutes les cartes du joueur on instancie
		 * la vue graphique correspondante
		 * On ajoute un MouseListener sur chaque carte
		 * afin de pouvoir les sélectionner/déselectionner
		 * graphiquement
		 * Puis on les ajoute au panel
		 */
		while (it.hasNext()) {
			VueCarte vc = new VueCarte(it.next());
			carteGraphique.add(vc);
			final JLabel carte = vc.getImage();
			if (j.getStrategie() instanceof StrategieRelle) {
				carte.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						if (carte.getBorder() == null) 
							carte.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
						else
							carte.setBorder(null);
					}				
				});
			}
			main.add(carte);
		}
		
		//Si pas de carte on ajoute quand même
		//une fausse carte, afin de garder la
		//mise en page
		if (cartesJoueur.size() == 0)
			main.add(new JLabel(new ImageIcon("img/cartevide.png")));
		
	}
	
	/**
	 * Méthode à appeler quand on veut mettre à jour les cartes de la main d'un joueur
	 * @param j Joueur à mettre à joueur graphiquement
	 */
	public void majCartesMain(Joueur j) {
		main.removeAll();
		dessinerCarteMain(j);
		main.updateUI(); 
	}

	
	/**
	 * Getter du nom du joueur
	 * @return le nom (JLabel) du joueur
	 */
	public JLabel getNom() {
		return nom;
	}

	/**
	 * Getter du joueur à représenter
	 * @return le joueur que l'on représente graphiquement
	 */
	public Joueur getJoueur() {
		return joueur;
	}


	/**
	 * Getter de la main du joueur
	 * @return le JPanel où sont toutes les cartes
	 */
	public JPanel getJpanel() {
		return main;
	}


	


	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Joueur) { //échange de cartes ou se prend la pioche
			this.majCartesMain((Joueur) arg);
		} else if (arg instanceof StrategieJeu) { //jeu
			this.majCartesMain(this.getJoueur());
		} else if (arg instanceof String) {
			if (((String) arg.toString()).equals("echange"))
				BatailleControleur.fenetreChoixEchangeJoueurReel(this.getJoueur());
			else
				BatailleControleur.updateJTextArea((String)arg);
		}
	}


	/**
	 * Getter de controleur
	 * @return le controleur qui fait les lien entre la vue et les modèles
	 */
	public BatailleControleur getControleur() {
		return controleur;
	}	
	
}
