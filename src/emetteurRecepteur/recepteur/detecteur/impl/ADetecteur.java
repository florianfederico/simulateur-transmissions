package emetteurRecepteur.recepteur.detecteur.impl;

import emetteurRecepteur.form.src.AForm;
import information.Information;

/**
 * @author FEDERICO Florian - florian.federico@telecom-bretagne.eu
 * @author SANCHEZ Bastien - bastien.sanchez@telecom-bretagne.eu
 * @author MOULY Thibaud - thibaud.mouly@telecom-bretagne.eu
 * @author PONCELET Nathan - nathan.poncelet@telecom-bretagne.eu
 *
 *         TELECOM Bretagne - FIP 2018 - 2016-2017 Projet SIT213 - Atelier
 *         systèmes de transmission
 * 
 */
public abstract class ADetecteur {

	/** Definit la forme du signal analogique */
	private final AForm form;

	public ADetecteur(AForm form) {
		super();
		this.form = form;
	}

	/**
	 * 
	 * Detecte si un symbole d'etat haut est present dans le signal
	 * 
	 * @param signal
	 *            Le signal
	 * @return Vrai si un symbole est detecté, Faux sinon
	 */
	public abstract boolean detecterSymbole(Information<Float> signal);

	/**
	 * Renvoie le symbole parfait
	 * 
	 * @param bit
	 *            L'etat du symbole
	 * @return Le symbole
	 */
	public Information<Float> getSymboleParfait(boolean bit) {
		return this.form.getSymboleParfait(bit);
	}

	public int getNe() {
		return this.form.getNe();
	}

	public float getaMin() {
		return this.form.getaMin();
	}

	public float getaMax() {
		return this.form.getaMax();
	}

}
