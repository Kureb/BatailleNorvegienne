package fr.utt.isi.lo02.projet.vue;

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

/**
 * Fenêtre permettant à l'utilisateur d'indiquer son nom en début de partie
 * et de choisir le nombre d'adversaires qu'il souhaite affronter
 * @author daussy
 *
 */
public class FenetreInitialisation extends JDialog {
	
	/**
	 * ID nécessaire pour la sérialisation (JDialog > Component > Serializable)
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Joueur qui sera le joueur réel
	 */
	private Joueur joueur;
	/**
	 * TextArea contenant le nom/pseudo du joueur courant
	 */
	private JTextArea nom;
	/**
	 * JPanel de la fenêtre à afficher
	 */
	private JPanel fenetre;
	/**
	 * Bouton de validation
	 */
	private JButton ok;
	/**
	 * TextArea contenant le nombre de joueurs à ajouter à la partie
	 */
	private JTextArea nb;
	
	
	/**
	 * Construit le JDialog perso
	 * @param parent JFrame qui détient le JDialog
	 * @param title titre
	 * @param modal vrai ou faux
	 */
	public FenetreInitialisation(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.init();
	}
	
	/**
	 * Initialisation des composants de la fenêtre
	 */
	private void init() {
		
		fenetre = new JPanel();
		fenetre.setLayout(new GridLayout(0, 2, 10, 10));
		
		JPanel pane = new JPanel();
		
		//Nom du joueur
		JLabel n = new JLabel("nom");
		nom = new JTextArea();
		nom.setText("Pseudo");
		nom.setEditable(true);
		
		//nombre de joueurs virtuels
		JLabel nombre = new JLabel("Nombre de joueurs virtuels");
		nb = new JTextArea();
		nb.setText("nb");
		nb.setEditable(true);
		
		ok = new JButton("Ok");
		
		// MouseListener qui vérifie que les champs sont remplis
		// avant de cacher la fenêtre
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
	
	/**
	 * Getter du joueur réel
	 * @return le joueur réel
	 */
	public Joueur getJoueur() {
		return joueur;
	}
	
	/**
	 * Getter du nom du joueur
	 * @return le nom de l'utilisateur
	 */
	public String getNom() {
		return nom.getText();
	}
	
	/**
	 * Getter du nombre de joueurs fictifs
	 * @return le nombre de joueurs fictifs
	 */
	public int getNb() {
		return Integer.parseInt(nb.getText());
	}
	
	
}
