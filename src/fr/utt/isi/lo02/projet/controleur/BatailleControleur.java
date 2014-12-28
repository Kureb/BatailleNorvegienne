package fr.utt.isi.lo02.projet.controleur;

import fr.utt.isi.lo02.projet.modele.Bataille;
import fr.utt.isi.lo02.projet.modele.Carte;
import fr.utt.isi.lo02.projet.vue.VueBataille;

public class BatailleControleur {

	private Bataille bataille;
	
	private VueBataille vueBataille;
	
	public BatailleControleur(Bataille bataille,VueBataille vueBataille) {
		this.bataille = bataille;
		this.vueBataille = vueBataille;
	}
	
	public void changerTas(Carte carte) {
		vueBataille.majTas(carte);
	}
	
	
	

}
