package fr.utt.isi.lo02.projet.vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
	
	
	
	
	
	public VueBataille(Bataille modele) {
		
		this.modele = modele;
		modele.addObserver(this);
		
		
		joueurs = modele.getJoueurs();
		
		fenetre = new JFrame("Bataille Norvégienne");
		fenetre.setLayout(new BorderLayout());
		fenetre.setMaximumSize(new Dimension(1000, 800));
		
		
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
		
		log = new JTextArea();
		log.setEditable(false);
		log.setRows(5);
		scrollPane = new JScrollPane(log);
		
		fenetre.add(scrollPane, BorderLayout.SOUTH);
		fenetre.add(scroll, BorderLayout.WEST);
		fenetre.add(panelTapis, BorderLayout.EAST);
		
		
		
		
		
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setVisible(true);
		
		
		
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// controleur.changerTas(((Carte) arg));

		if (arg instanceof Carte) {
			String text = log.getText();
			log.append("Dernière carte jouée : "
					+ ((Carte) arg).toString() + "\n");

			this.majTas(((Carte) arg));
		} else if (arg instanceof String) {
			String lolz = ((String) arg.toString());
			if (lolz.equals("Le tas est vide")) {
				String text = log.getText();
				//log.setText(text + lolz.toString() + "\n");
				log.append(lolz.toString()+ "\n");
				this.majTas(null);
			} else if (lolz.equals("pioche")) {
				log.append("ramasse le tas\n");
				this.effacePioche();
			}

		}
		
		log.setCaretPosition(log.getDocument().getLength());
		

	}
	
	

	private void effacePioche() {
		this.pile.setIcon(null);
		
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

}
