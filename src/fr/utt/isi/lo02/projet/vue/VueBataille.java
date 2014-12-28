package fr.utt.isi.lo02.projet.vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import fr.utt.isi.lo02.projet.controleur.ControleurAbstrait;
import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.StrategieRapide;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.Scrambler;

public class VueBataille implements Observer {

	
	private JFrame fenetre;
	
	private JPanel panelJoueur;
	
	private Bataille modele;
	
	private BatailleControleur controleur;
	
	private ArrayList<Joueur> joueurs;
	
	private ArrayList<VueJoueur> vueJoueurs = new ArrayList<>();
	
	private JLabel tas; 
	
	private JTextArea log;
	
	private JScrollPane scrollPane;
	
	
	
	
	
	public VueBataille(Bataille modele) {
		
		this.modele = modele;
		modele.addObserver(this);
		
		joueurs = modele.getJoueurs();
		
		fenetre = new JFrame("Bataille Norvégienne");
		fenetre.setLayout(new BorderLayout());
		
		panelJoueur = new JPanel();
		panelJoueur.setLayout(new BoxLayout(panelJoueur, BoxLayout.Y_AXIS));
		
		
		JPanel panelTapis = new JPanel();
		JLabel imgTapis = new JLabel(new ImageIcon("img/Tapis.jpg"));
	
		
		tas = new JLabel(new ImageIcon("img/b1fv.png")); //Tas vide de base
		
		
		imgTapis.setLayout(new GridLayout()); 
		imgTapis.add(tas);
		panelTapis.add(imgTapis);
		
		Container reservoir = fenetre.getContentPane();
		
		
		//Joueurs
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
		
		reservoir.add(scrollPane, BorderLayout.SOUTH);
		reservoir.add(panelJoueur, BorderLayout.WEST);
		reservoir.add(panelTapis, BorderLayout.EAST);
		
		
		
		
		
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setVisible(true);
		
		
		
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// controleur.changerTas(((Carte) arg));

		if (arg instanceof Carte) {
			String text = log.getText();
			//log.insert("derniere carte jouée " + ((Carte) arg).toString() + "\n", log.getDoc);
			//log.setText(text + "Dernière carte jouée : "
			//		+ ((Carte) arg).toString() + "\n");
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
			}

		}
		//Ne semble pas fonctionner
		log.setCaretPosition(log.getDocument().getLength());
		

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
			this.setTas(chemin);
		} else {
			String chemin = "img/b1fv.png";
			this.setTas(chemin);
		}
		
	}

}
