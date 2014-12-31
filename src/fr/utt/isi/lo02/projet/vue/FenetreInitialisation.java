package fr.utt.isi.lo02.projet.vue;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.utt.isi.lo02.projet.modele.Joueur;

public class FenetreInitialisation extends JDialog{
	
	private Joueur joueur;
	
	private JTextArea nom;
	
	private JPanel fenetre;
	
	private JButton ok;
	
	private JTextArea nb;
	
	public FenetreInitialisation(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.init();
	}
	
	private void init() {
		
		fenetre = new JPanel();
		fenetre.setLayout(new GridLayout(0, 2, 10, 10));
		
		JPanel pane = new JPanel();
		
		JLabel n = new JLabel("nom");
		nom = new JTextArea();
		nom.setText("Pseudo");
		nom.setEditable(true);
		
		
		JLabel nombre = new JLabel("Nombre de joueurs virtuels");
		nb = new JTextArea();
		nb.setText("nb");
		nb.setEditable(true);
		
		ok = new JButton("Ok");
		
		ok.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (!nom.getText().equals("Pseudo") && (!nb.getText().equals("nb")))
					setVisible(false);
				else
					ok.setText("!ok");
			
			}
		});
		
		fenetre.add(n);
		fenetre.add(nom);
		fenetre.add(nombre);
		fenetre.add(nb);
		fenetre.add(ok);
		
		pane.add(fenetre);
		
		
		this.getContentPane().add(pane);
		this.pack();
		
		
	}
	
	public Joueur getJoueur() {
		return joueur;
	}
	
	public void montrer() {
		fenetre.setVisible(true);
	}
	
	public String getNom() {
		return nom.getText();
	}
	
	public int getNb() {
		return Integer.parseInt(nb.getText());
	}
	
	
}
