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

public class DialogJouerCarte extends JDialog {

	private Joueur joueur;
	
	private Carte carteMain;
	
	private ArrayList<Carte> cartes;
	
	public DialogJouerCarte(JFrame parent, String title, boolean modal, Joueur joueur) {
		super(parent, title, modal);
		this.joueur = joueur;
		this.cartes = new ArrayList<>();
		//this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}
	
	
	public void initComponent() {
		JPanel fenetre = new JPanel();
		fenetre.setLayout(new GridLayout(0, 1));
		
		
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(0, 3));
		
		//JScrollPane jsp = new JScrollPane(main);
		
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
							//if (!cartes.contains(carteMain))
							cartes.add(carteMain);
							//String msg = vc.getCarte().getValeur() + " " + vc.getCarte().getCouleur();
							//valider.setText(msg);
						} else {
							carteMain = new Carte(vc.getCarte().getValeur(), vc.getCarte().getCouleur());
							//if (cartes.contains(carteMain))
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
	    //okBouton.setMaximumSize(new Dimension(10,10));
	    control.add(okBouton);
	    fenetre.add(main);
	    fenetre.add(control);
	   
	    this.getContentPane().add(fenetre, BorderLayout.SOUTH);
	    this.pack();
		
	}
	
	public ArrayList<Carte> getCartes() {
		return cartes;
	}
	
	
	
	public Carte getCarteMain() {
		return carteMain;
	}
	
	public Joueur getJoueur() {
		return joueur;
	}
	
}
