package fr.utt.isi.lo02.projet.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

public class VueBataille implements Observer {

	
	private JFrame fenetre;
	
	private JPanel panelJoueur;
	
	private Bataille modele;
	
	private BatailleControleur controleur;
	
	private ArrayList<Joueur> joueurs;
	
	private ArrayList<VueJoueur> vueJoueurs = new ArrayList<>();
	
	private JLabel tas; 
	
	private JLabel pile;
	
	private JTextArea log;
	
	private JScrollPane scrollPane;
	
	
	
	
	
	public VueBataille(final Bataille modele) {
		
		this.modele = modele;
		modele.addObserver(this);
		
		
		joueurs = modele.getJoueurs();
		
		fenetre = new JFrame("Bataille Norvégienne");
		fenetre.setLayout(new BorderLayout());
		//fenetre.setMaximumSize(new Dimension(1000, 1000));
		fenetre.setResizable(false);
		
		//Container reservoir = fenetre.getContentPane();
		
		panelJoueur = new JPanel();
		panelJoueur.setLayout(new BoxLayout(panelJoueur, BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(panelJoueur);
		
		JPanel panelTapis = new JPanel();
		JLabel imgTapis = new JLabel(new ImageIcon("img/Tapis.jpg"));
	
		
		tas = new JLabel(new ImageIcon("img/tasvide.png"));
		pile = new JLabel(new ImageIcon("img/b2fv.png")); 
		
		imgTapis.setLayout(new GridLayout()); 
		imgTapis.add(tas);
		imgTapis.add(pile);
		panelTapis.add(imgTapis);
		
		
		
		
		Iterator<Joueur> it = joueurs.iterator();
		while (it.hasNext()){
			VueJoueur vueJoueur = new VueJoueur(it.next());
			vueJoueurs.add(vueJoueur);
			panelJoueur.add(vueJoueur.getJpanel());
			
		}
		
		setLog(new JTextArea());
		getLog().setEditable(false);
		getLog().setRows(5);
		/*
		// Redirection de la sortie vers notre jtextareaperso
		JTextAreaPerso jtap = new JTextAreaPerso(log);
		PrintStream printStream = new PrintStream(jtap);
		System.setOut(printStream);
		System.setErr(printStream);
		// Fin redirection de la sortie vers notre jtextareaperso
		*/
		scrollPane = new JScrollPane(getLog());
		
		JButton continuer = new JButton("continuer");
		
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
		// controleur.changerTas(((Carte) arg));

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
	
	/*
	public void updateJTextArea(String message) {
		this.getLog().append(message + "\n");
		getLog().setCaretPosition(getLog().getDocument().getLength()); //Pour scroller automatiquement
	}*/
	

	public void effacePioche() {
		this.pile.setIcon(new ImageIcon("img/tasvide.png"));
		
	}

	public JLabel getTas() {
		return tas;
	}

	public void setTas(String chemin) {
		this.tas.setIcon(new ImageIcon(chemin));
	}

	public void majTas(Carte carte) {
		if (carte != null) {
			String chemin = "img/" + carte.VALEURS[carte.getValeur()] + "_" + carte.COULEURS[carte.getCouleur()] + ".png";
			this.tas.setIcon(new ImageIcon(chemin));
		} else {
			String chemin = "img/tasvide.png";
			this.tas.setIcon(new ImageIcon(chemin));
		}
		
	}

	public JTextArea getLog() {
		return log;
	}

	public void setLog(JTextArea log) {
		this.log = log;
	}

}
