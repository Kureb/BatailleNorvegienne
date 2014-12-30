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
import javax.swing.JTextField;

import fr.utt.isi.lo02.projet.controleur.BatailleControleur;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.modele.Joueur;
import fr.utt.isi.lo02.projet.modele.StrategieRelle;

public class DialogChangerCartes extends JDialog {
	
	
	private ArrayList<Carte> cartesMain;
	private ArrayList<Carte> cartesVisibles;
	private JLabel icon;
	private JTextField nom;
	private Carte carteMain;
	private Carte carteVisible;
	private Joueur joueur;
	private JButton valider;
	
	 public DialogChangerCartes(JFrame parent, String title, boolean modal, Joueur joueur){
		    super(parent, title, modal);
		    this.cartesMain = new ArrayList<>();
		    this.cartesVisibles = new ArrayList<>();
		    this.joueur = joueur;
		    this.setSize(550, 270);
		    this.setLocationRelativeTo(null);
		    this.setResizable(false);
		    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		    this.initComponent();
		  }

	
	 
	 private void initComponent(){
		    //Icône
		    //icon = new JLabel(new ImageIcon("images/icone.jpg"));
		    
		 	
		 
		 	JPanel fenetre = new JPanel();
		    fenetre.setBackground(Color.white);
		    fenetre.setLayout(new GridLayout(0,1));
		    //fenetre.add(icon);
		    
		  //Cartes en main
		    JPanel main = new JPanel();
			FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
			main.setLayout(fl);
		
			//TODO si on sélectionne une carte, déselectionner les autres..
		
			Iterator<Carte> it = joueur.getMain().iterator();
		    while (it.hasNext()) {
		    	final VueCarte vc = new VueCarte(it.next());
		    	//final JLabel carte = vc.getImage();
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
		    
		    //cartes visibles
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
		    final JButton okBouton = new JButton("Ok");
		    
		    okBouton.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e) {  
		    	  if (cartesVisibles.size() != 0 && cartesVisibles.size() == cartesMain.size())
		    		  //BatailleControleur.echange(joueur, carteVisible, carteMain);
		    		  //joueur.echangerCarte(carteMain, carteVisible);
		    	  	  setVisible(false);
		    	  else
		    		  okBouton.setText("invalide");
		        
		    	
		      }
		    });
		    
	

		    JButton cancelBouton = new JButton("Annuler");
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



	public Carte getCarteMain() {
		return carteMain;
	}



	public void setCarteMain(Carte carteMain) {
		this.carteMain = carteMain;
	}



	public Carte getCarteVisible() {
		return carteVisible;
	}



	public void setCarteVisible(Carte carteVisible) {
		this.carteVisible = carteVisible;
	}



	public Joueur getJoueur() {
		return joueur;
	}



	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}  
	
	
	public ArrayList<Carte> getCartesMain() {
		return cartesMain;
	}



	public ArrayList<Carte> getCartesVisibles() {
		return cartesVisibles;
	}



	
  
  
  
  
  
}