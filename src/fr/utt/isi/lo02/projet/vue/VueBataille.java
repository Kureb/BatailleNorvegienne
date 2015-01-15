package fr.utt.isi.lo02.projet.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fr.utt.isi.lo02.projet.controleur.BatailleControleur;
import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;

/**
 * Vue représentant la bataille
 * @author daussy
 *
 */
public class VueBataille implements Observer {

	/**
	 * Fenêtre graphique de la bataille
	 */
	private JFrame fenetre;
	/**
	 * Panel qui contient les joueurs
	 */
	private JPanel panelJoueur;
	/**
	 * Modèle à observer
	 */
	private Bataille modele;
	/**
	 * Controleur avec qui intéragir
	 */
	private BatailleControleur controleur;
	/**
	 * Liste des joueurs de la bataille
	 */
	private ArrayList<Joueur> joueurs;
	/**
	 * Liste des vues graphiques des joueurs
	 */
	private ArrayList<VueJoueur> vueJoueurs = new ArrayList<>();
	/**
	 * Représente le tas
	 */
	private JLabel tas; 
	/**
	 * Représente la pile
	 */
	private JLabel pile;
	/**
	 * TextArea qui contient les logs de la bataille
	 */
	private JTextArea log;
	/**
	 * JScrollPane qui englobe le JTextArea des logs
	 */
	private JScrollPane scrollPane;
	
	
	
	
	/**
	 * Constructeur de la Vue Bataille
	 * @param modele modèle de la Bataille à observer
	 */
	public VueBataille(final Bataille modele) {
		
		//On lui dit d'observer son modèle
		this.setModele(modele);
		modele.addObserver(this);
		
		
		joueurs = modele.getJoueurs();
		
		//Création de la fenêtre
		fenetre = new JFrame("Bataille Norvégienne");
		fenetre.setLayout(new BorderLayout());
		fenetre.setResizable(true);
		
		//Panel qui contient les joueurs
		panelJoueur = new JPanel();
		panelJoueur.setLayout(new BoxLayout(panelJoueur, BoxLayout.Y_AXIS));
		//On lui ajoute un JScrollPane (car si + de 5 joueurs, ça dépasse)
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(panelJoueur);
		
		
		//Image d'un tapis
		JPanel panelTapis = new JPanel();
		JLabel imgTapis = new JLabel(new ImageIcon("img/Tapis.jpg"));
	
		
		tas = new JLabel(new ImageIcon("img/tasvide.png"));
		pile = new JLabel(new ImageIcon("img/b2fv.png")); 
		
		imgTapis.setLayout(new GridLayout()); 
		imgTapis.add(tas);
		imgTapis.add(pile);
		panelTapis.add(imgTapis);
		
		
		
		// Pour tous les joueurs on ajoute leur vue respective
		Iterator<Joueur> it = joueurs.iterator();
		while (it.hasNext()){
			VueJoueur vueJoueur = new VueJoueur(it.next());
			vueJoueurs.add(vueJoueur);
			panelJoueur.add(vueJoueur.getJpanel());
			
		}
		
		//TextArea des logs
		setLog(new JTextArea());
		getLog().setEditable(false);
		getLog().setRows(5);
		scrollPane = new JScrollPane(getLog());
		
		JButton continuer = new JButton("continuer");
		// Permet de jouer au fur et à mesure
		continuer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				modele.continuer();
			}
		});
		
		
		fenetre.add(scrollPane, BorderLayout.NORTH);
		fenetre.add(scroll, BorderLayout.WEST);
		fenetre.add(continuer, BorderLayout.SOUTH);
		fenetre.add(panelTapis, BorderLayout.EAST);
		
		
		
		
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.setLocationRelativeTo(null);;
		
		
		
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Carte) {
			this.majTas(((Carte) arg));
		} else if (arg instanceof String) {
			String lolz = ((String) arg.toString());
			if (lolz.equals("Le tas est vide")) {
				this.majTas(null);
			} else if (lolz.equals("pioche")) {
				BatailleControleur.updateJTextArea("La pioche est maintenant vide"); 
				this.effacePioche();
			} else if (lolz.contains("gagné")){
				BatailleControleur.updateJTextArea(lolz.toString());
			} else {
				BatailleControleur.updateJTextArea(lolz.toString());
			}

		} 
		
		
		

	}
	
	/**
	 * Quand le tas est vide, on enlève l'image des cartes
	 * pour mettre l'image correspondant au tas vide
	 */
	public void effacePioche() {
		this.pile.setIcon(new ImageIcon("img/tasvide.png"));
		
	}

	/**
	 * GEtter du tas
	 * @return le JLabel tas
	 */
	public JLabel getTas() {
		return tas;
	}

	/**
	 * Setter du tas
	 * @param chemin Chemin de l'image à appliquer
	 */
	public void setTas(String chemin) {
		this.tas.setIcon(new ImageIcon(chemin));
	}

	/**
	 * Met à jour le tas avec la fernière carte posée
	 * @param carte dernière carte posée
	 */
	public void majTas(Carte carte) {
		if (carte != null) {
			String chemin = "img/" + carte.getValeurs()[carte.getValeur()] + "_" + carte.getCouleurs()[carte.getCouleur()] + ".png";
			this.tas.setIcon(new ImageIcon(chemin));
		} else {
			String chemin = "img/tasvide.png";
			this.tas.setIcon(new ImageIcon(chemin));
		}
		
	}

	/**
	 * Getter des logs
	 * @return JTextArea contenant les logs
	 */
	public JTextArea getLog() {
		return log;
	}

	/**
	 * Setter des logs
	 * @param log log à appliquer
	 */
	public void setLog(JTextArea log) {
		this.log = log;
	}

	/**
	 * Getter du modèle
	 * @return la bataille à observer
	 */
	public Bataille getModele() {
		return modele;
	}

	/**
	 * Setter du modèle
	 * @param modele modèle à observer
	 */
	public void setModele(Bataille modele) {
		this.modele = modele;
	}

	/**
	 * Getter du controleur
	 * @return le controleur qui fait le lien entre la vue et le modèle
	 */
	public BatailleControleur getControleur() {
		return controleur;
	}

	/**
	 * Setter du controleur
	 * @param controleur Contrôleur qui fera le lien entre la vue et le modèle
	 */
	public void setControleur(BatailleControleur controleur) {
		this.controleur = controleur;
	}

}
