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
import javax.swing.JPanel;

import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.StrategieRelle;

/**
 * JDialog permettant au joueur réel de choisir quel(s) carte(s) il souhaite
 * jouer
 * 
 * @author daussy
 *
 */
public class DialogJouerCarte extends JDialog {

	/**
	 * ID nécessaire pour la sérialisation (JDialog > Component > Serializable)
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Joueur souhaitant jouer
	 */
	private Joueur joueur;
	/**
	 * Dernière carte sélectionnée
	 */
	private Carte carteMain;
	/**
	 * Liste des cartes qu'il souhaite jouer
	 */
	private ArrayList<Carte> cartes;
	
	/**
	 * Construit le JDialog perso
	 * @param parent JFrame qui détient le JDialog
	 * @param title titre
	 * @param modal vrai ou faux
	 * @param joueur qui joue
	 */
	public DialogJouerCarte(JFrame parent, String title, boolean modal, Joueur joueur) {
		super(parent, title, modal);
		this.joueur = joueur;
		this.cartes = new ArrayList<>();
		//this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}
	
	/**
	 * Initialise les composants de la fenêtre
	 */
	public void initComponent() {
		JPanel fenetre = new JPanel();
		fenetre.setLayout(new GridLayout(0, 1));
		
		
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(0, 3));
		
		/*
		 * Pour chaques cartes possédées dans la main du joueur
		 * on crée une VueCarte correspondant, à laquelle un ajoute un listener
		 * afin d'ajouter la carte à liste de cartes lorsque le joueur la sélectionne
		 * (ou supprimer de la liste si le joueur la déselectionne)
		 * Puis on ajoute cette carte au JPanel
		 */
		Iterator<Carte> it = joueur.getMain().iterator();
	    while (it.hasNext()) {
	    	final VueCarte vc = new VueCarte(it.next());
	    	
	    	if (joueur.getStrategie() instanceof StrategieRelle) {
				vc.getImage().addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						if (vc.getImage().getBorder() == null) {
							vc.getImage().setBorder(BorderFactory.createLineBorder(Color.black));
							carteMain = new Carte(vc.getCarte().getValeur(), vc.getCarte().getCouleur());
							cartes.add(carteMain);
						} else {
							carteMain = new Carte(vc.getCarte().getValeur(), vc.getCarte().getCouleur());
							cartes.remove(carteMain);
							vc.getImage().setBorder(null);
							carteMain = null;
						}	
					}				
				});
			}
	    	main.add(vc.getImage());
	    }
	    
	    
	    JPanel control = new JPanel();
	    control.setLayout(new FlowLayout());
	    final JButton okBouton = new JButton("Ok");
	    
	    
	    /*
	     * Bouton ok auquel on ajoute un listener
	     * afin de cacher la fenêtre si la (les) carte(s)
	     * est (sont) jouable(s)
	     * Sinon l'utilisateur et prévenu que quelque chose ne va pas
	     */
	    okBouton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cartes.size() != 0 && this.toutesPareil()) {
					if (!Bataille.getInstance().getTable().isEmpty()) {
						int index = Bataille.getInstance().getTable().size()-1;
						if (Bataille.getInstance().getTable().get(index).estRecouvrablePar(cartes.get(0)))
							setVisible(false);
						else
							okBouton.setText("carte injouable");
					} else {
						setVisible(false);
					}
				} else
					okBouton.setText("sélectionne une carte");
			}

			/**
			 * Permet de savoir si toutes les cartes sélectionnées sont
			 * bin de la même valeur ou non
			 * @return
			 */
			private boolean toutesPareil() {
				Iterator<Carte> it = cartes.iterator();
				Carte c;
				Carte temoin = cartes.get(0);
				while (it.hasNext()) {
					c = it.next();
					if (!c.equals(temoin)) return false;
				}
				
				return true;
			}
		});
	    control.add(okBouton);
	    fenetre.add(main);
	    fenetre.add(control);
	   
	    this.getContentPane().add(fenetre, BorderLayout.SOUTH);
	    this.pack();
		
	}
	
	/**
	 * Getter de la liste des cartes à jouer
	 * @return les cartes à jouer
	 */
	public ArrayList<Carte> getCartes() {
		return cartes;
	}
	
	/**
	 * Getter de la dernire carte sélectionnée
	 * @return la dernière carte sélectionnée
	 */
	public Carte getCarteMain() {
		return carteMain;
	}
	
	/**
	 * Getter du joueur qui souhait poser ses cartes
	 * @return le joueur courant
	 */
	public Joueur getJoueur() {
		return joueur;
	}
	
}
