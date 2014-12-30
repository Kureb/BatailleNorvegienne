package fr.utt.isi.lo02.projet.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Joueur;

public class DialogEnvoyerTasJoueur extends JDialog{

	private Joueur joueur;
	
	private Joueur victime;
	
	private ArrayList<JRadioButton> mesboutons;
	
	public DialogEnvoyerTasJoueur(JFrame parent, String title, boolean modal, Joueur joueur) {
		super(parent, title, modal);
		this.joueur = joueur;
		mesboutons = new ArrayList<>();
		//this.setSize(550, 270);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}

	public void initComponent() {
		JPanel fenetre = new JPanel();
		fenetre.setLayout(new GridLayout(0,1));
		
		JPanel joueurs = new JPanel();
		joueurs.setLayout(new GridLayout(0, 5));
		
		Iterator<Joueur> it =  Bataille.getInstance().getJoueurs().iterator();
		while (it.hasNext()) {
			final Joueur j = it.next();
			if (j != this.joueur) {
				final JRadioButton jj = new JRadioButton(j.getNom());
				mesboutons.add(jj);
				joueurs.add(jj);
			}
		}
		
		JPanel control = new JPanel();
		control.setLayout(new FlowLayout());
		final JButton okBouton = new JButton("Ok");
		
		okBouton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				victime = quiEstLaVictime();
				if (victime != null)
					setVisible(false);
				else
					okBouton.setText("choisir qqn");
			}
		});
		
		
		control.add(okBouton);
		fenetre.add(joueurs);
		fenetre.add(control);
		
		this.getContentPane().add(fenetre, BorderLayout.SOUTH);
		this.pack();
	}
	
	protected Joueur quiEstLaVictime() {
		Iterator<JRadioButton> it = mesboutons.iterator();
		String nom = "";
		while (it.hasNext()){
			JRadioButton j = it.next();
			if (j.isSelected())
				nom = j.getText();
		}
		return Bataille.getInstance().getJoueurs(nom);
		//return "";
			
	}

	public Joueur getVictime() {
		return victime;
	}
	
}
