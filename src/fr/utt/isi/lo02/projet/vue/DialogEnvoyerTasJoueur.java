package fr.utt.isi.lo02.projet.vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Joueur;

public class DialogEnvoyerTasJoueur extends JDialog{

	/**
	 * Id nécessaire à la Sérialisation (JDialog > Component > Serializable)
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Joueur souhaitant envoyer le tas à quelqu'un
	 */
	private Joueur joueur;
	/**	
	 * Joueur qui va recevoir le tas
	 */
	private Joueur victime;
	/**
	 * Boutons permettant de choisir une vicitme
	 */
	private ArrayList<JRadioButton> mesboutons;
	
	/**
	 * Construit le JDialog perso
	 * @param parent JFrame qui détient le JDialog
	 * @param title titre
	 * @param modal vrai ou faux
	 * @param joueur qui joue
	 */
	public DialogEnvoyerTasJoueur(JFrame parent, String title, boolean modal, Joueur joueur) {
		super(parent, title, modal);
		this.joueur = joueur;
		mesboutons = new ArrayList<>();
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}

	
	/**
	  * Permet de créer tous les composants de la fenêtre
	  */
	public void initComponent() {
		JPanel fenetre = new JPanel();
		fenetre.setLayout(new GridLayout(0,1));
		
		JPanel joueurs = new JPanel();
		joueurs.setLayout(new GridLayout(0, 5));
		
		// On ajoute un JRadioButton
		// Pour tous les joueurs présent dans la partie
		// sans ajouter le joueur courant
		// (afin qu'il ne s'envoie pas le tas à lui même
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
		
		// ActionListener sur le bouton ok
		// qui ferme la fenêtre si une victime
		// est bel et bien sélectionnée
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
	
	/**
	 * Permet de savoir qui est la victime choisie
	 * par le joueur courant
	 * @return le joueur à victimisé
	 */
	public Joueur quiEstLaVictime() {
		Iterator<JRadioButton> it = mesboutons.iterator();
		String nom = "";
		// Parcourt de tous les JRadioButton et on retourne celui sélectionné
		while (it.hasNext()){
			JRadioButton j = it.next();
			if (j.isSelected())
				nom = j.getText();
		}
		return Bataille.getInstance().getJoueurs(nom);
			
	}

	/**
	 * Retourne le joueur à victimiser 
	 * @return la victime
	 */
	public Joueur getVictime() {
		return victime;
	}
	
}
