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

import fr.utt.isi.lo02.projet.controleur.BatailleControleur;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.StrategieRelle;

/**
 * JDialog perso permettant au joueur réel d'échanger ses cartes
 * @author daussy
 *
 */
public class DialogChangerCartes extends JDialog {
	
	/**
	 * ID nécessaire à la sérialisation (provient de JDialog > Component > Serializable)
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Liste des cartes en main du joueur qu'il  souhaite échanger 
	 */
	private ArrayList<Carte> cartesMain;
	/**
	 * Liste des cartes position face visible que le joueur souhaite posséder en main
	 */
	private ArrayList<Carte> cartesVisibles;
	/**
	 * Dernière carte en main sélectionnée
	 */
	private Carte carteMain;
	/**
	 * Dernière carte visible sélectionnée
	 */
	private Carte carteVisible;
	/**
	 * Joueur souhaitant faire l'échange de ses cartes
	 */
	private Joueur joueur;
	/**
	 * Bouton de validation
	 */
	private JButton okBouton;
	/**
	 * Bouton d'annulation
	 */
	private JButton cancelBouton;
	
	
	/**
	 * Construit le JDialog perso
	 * @param parent JFrame qui détient le JDialog
	 * @param title titre
	 * @param modal vrai ou faux
	 * @param joueur qui joue
	 */
	public DialogChangerCartes(JFrame parent, String title, boolean modal, Joueur joueur){
		    super(parent, title, modal);
		    this.cartesMain = new ArrayList<>();
		    this.cartesVisibles = new ArrayList<>();
		    this.joueur = joueur;
		    this.setSize(550, 270);
		    this.setLocationRelativeTo(null);
		    this.setResizable(false);
		    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		    this.initComponent(); //initialisation
		  }

	
	 /**
	  * Permet de créer tous les composants de la fenêtre
	  */
	 private void initComponent(){
		    JPanel fenetre = new JPanel();
		    fenetre.setBackground(Color.white);
		    fenetre.setLayout(new GridLayout(0,1));
		    
		    JPanel main = new JPanel();
			FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
			main.setLayout(fl);
		
			
			// Pour toutes les cartes en main du joueur
			// On crée une VueCarte correspondante
			// Sur cette VueCarte, on ajoute un MouseListener
			// Qui permet de sélectionner ou déselectionner une carte
			// On ajoute enfin cette carte au Panel
			Iterator<Carte> it = joueur.getMain().iterator();
		    while (it.hasNext()) {
		    	final VueCarte vc = new VueCarte(it.next());
		    	if (joueur.getStrategie() instanceof StrategieRelle) {
					vc.getImage().addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent me) {
							if (vc.getImage().getBorder() == null) {
								vc.getImage().setBorder(BorderFactory.createLineBorder(Color.black));
								carteMain = new Carte(vc.getCarte().getValeur(), vc.getCarte().getCouleur());
								if (!cartesMain.contains(carteMain))
									cartesMain.add(carteMain);
							} else {
								carteMain = new Carte(vc.getCarte().getValeur(), vc.getCarte().getCouleur());
								if (cartesMain.contains(carteMain))
									cartesMain.remove(carteMain);
								vc.getImage().setBorder(null);
								carteMain = null;
								
							}	
						}				
					});
				}
		    	main.add(vc.getImage());
		    }
		    //Fin cartes en main
		    
		    // Même principe pour les cartes visibles
		    JPanel visibles = new JPanel();
			FlowLayout fl2 = new FlowLayout(FlowLayout.LEFT);
			visibles.setLayout(fl2);
			
			Iterator<Carte> it2 = joueur.getFaceUp().iterator();
		    while (it2.hasNext()) {
		    	final VueCarte vc = new VueCarte(it2.next());
		    	//final JLabel carte = vc.getImage();
		    	if (joueur.getStrategie() instanceof StrategieRelle) {
					vc.getImage().addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent me) {
							if (vc.getImage().getBorder() == null) {
								vc.getImage().setBorder(BorderFactory.createLineBorder(Color.black));
								carteVisible = new Carte(vc.getCarte().getValeur(), vc.getCarte().getCouleur());
								if (!cartesVisibles.contains(carteVisible))
									cartesVisibles.add(carteVisible);
							} else {
								carteVisible = new Carte(vc.getCarte().getValeur(), vc.getCarte().getCouleur());
								if (cartesVisibles.contains(carteVisible))
									cartesVisibles.remove(carteVisible);
								vc.getImage().setBorder(null);
								carteVisible = null;
							}
						}				
					});
				}
		    	visibles.add(vc.getImage());
		    }
		    //fin cartes visibles

		    
		    JPanel control = new JPanel();
		    okBouton = new JButton("Ok");
		    
		    
		    // On ajoute un actionListener sur le bouton Ok
		    // Si toutes les conditions nécessaires au changement de carte
		    // sont remplies, on ferme la fenêtre
		    // Sinon la fenêtre reste active et l'utilisateur est notifié
		    // que l'opération ne peut être réalisée
		    okBouton.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e) {  
		    	  if (cartesVisibles.size() != 0 && cartesVisibles.size() == cartesMain.size())
		    		  setVisible(false);
		    	  else
		    		  okBouton.setText("invalide");
		        
		    	
		      }
		    });
		    
	
		    // ActionListener sur le bouton annuler 
		    // pour mettre à jour les ligs de la vue graphique
		    cancelBouton = new JButton("Annuler");
		    cancelBouton.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent arg0) {
		    	  BatailleControleur.updateJTextArea("Echange annulé pour " + joueur.getNom());
		        setVisible(false);
		      }      
		    });
		    
		    fenetre.add(main);
		    fenetre.add(visibles);
		    
		    control.add(fenetre);
		    control.add(okBouton);
		    control.add(cancelBouton);

		    this.getContentPane().add(control, BorderLayout.SOUTH);
		  }


	 /**
	  * getter de la dernière Carte Main
	  * @return dernière carte sélectionneé
	  */
	public Carte getCarteMain() {
		return carteMain;
	}


	/**
	 * Setter de carte main
	 * @param carteMain 
	 */
	public void setCarteMain(Carte carteMain) {
		this.carteMain = carteMain;
	}


	/**
	 * Getter de la dernière carte visible
	 * @return dernière carte visible
	 */
	public Carte getCarteVisible() {
		return carteVisible;
	}


	/**
	 * Setter de carte visible
	 * @param carteVisible
	 */
	public void setCarteVisible(Carte carteVisible) {
		this.carteVisible = carteVisible;
	}


	/**
	 * Getter de joueur
	 * @return le joueur échangeant ses cartes
	 */
	public Joueur getJoueur() {
		return joueur;
	}


	/**
	 * Setter de joueur
	 * @param joueur
	 */
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}  
	
	
	/**
	 * Getter de la liste des cartes en main
	 * @return les cartes en main à échanger
	 */
	public ArrayList<Carte> getCartesMain() {
		return cartesMain;
	}


	/**
	 * Getter de la liste des cartes visibles
	 * @return les cartes face visible à échanger
	 */
	public ArrayList<Carte> getCartesVisibles() {
		return cartesVisibles;
	}



	
  
  
  
  
  
}