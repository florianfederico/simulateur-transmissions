package emetteurRecepteur.form.src;

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
 *         Définit les methodes communes aux signaux de la forme RZ.
 * 
 */
public class RZ extends AForm {

	private final Information<Float> symboleParfaitHaut;

	private final Information<Float> symboleParfaitBas;

	public RZ() {
		super();
		this.symboleParfaitBas = this.genererSymboleParfaitBas();
		this.symboleParfaitHaut = this.genererSymboleParfaitHaut();
	}

	public RZ(int ne, float aMin, float aMax) {
		super(ne, aMin, aMax);

		this.symboleParfaitBas = this.genererSymboleParfaitBas();
		this.symboleParfaitHaut = this.genererSymboleParfaitHaut();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see emetteurRecepteur.form.src.AForm#CAN(information.Information)
	 */
	public Information<Float> CNA(Information<Boolean> infoNumerique) {
		Information<Float> info = new Information<Float>();
		for (Boolean b : infoNumerique) {
			if (b)
				info.add(symboleParfaitHaut);
			else
				info.add(symboleParfaitBas);
		}
		return info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see emetteurRecepteur.form.src.AForm#getSymboleParfait(boolean)
	 */
	@Override
	public Information<Float> getSymboleParfait(boolean bit) {
		if (bit)
			return this.symboleParfaitHaut;
		else
			return this.symboleParfaitBas;
	}

	/**
	 * Incremente le nombre d'echantillons tant qu'il n'est pas divisible par 3
	 * et n'est pas impair
	 * 
	 * @param ne
	 *            Le nombre d'échantillons
	 */
	@Override
	protected int corrigerNbEchantillons(int ne) {
		int resultat = ne;
		while (resultat % 3 != 0 || resultat % 2 != 1)
			resultat++;
		return resultat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see emetteurRecepteur.form.src.AForm#calculerPas(float, float, int, int)
	 */
	@Override
	protected float calculerPas() {
		return (this.getaMax() - this.getaMin()) / ((this.getNe() / 6) + 1);
	}

	/**
	 * @return Un symbole parfait a l'etat haut
	 */
	private Information<Float> genererSymboleParfaitHaut() {
		Information<Float> infoAnalogique = new Information<Float>();

		float aCourant = this.getaMin(); // Amplitude courante
		int fracNe = 6;
		float pas = this.calculerPas();

		for (int i = 0; i < this.getNe() / 3; i++)
			infoAnalogique.add(this.getaMin());

		/**
		 * Temps de montée
		 */
		for (int i = 0; i < this.getNe() / fracNe; i++) {
			aCourant += pas;
			infoAnalogique.add(new Float(aCourant));
		}

		aCourant = this.getaMax();
		infoAnalogique.add(new Float(aCourant));

		/**
		 * Temps de descente
		 */
		for (int i = 0; i < this.getNe() / fracNe; i++) {
			aCourant -= pas;
			infoAnalogique.add(new Float(aCourant));
		}

		for (int i = 0; i < this.getNe() / 3; i++)
			infoAnalogique.add(this.getaMin());

		return infoAnalogique;
	}

	/**
	 * @return Un symbole parfait a l'etat bas
	 */
	private Information<Float> genererSymboleParfaitBas() {
		Information<Float> infoAnalogique = new Information<Float>();

		for (int i = 0; i < this.getNe(); i++)
			infoAnalogique.add(new Float(this.getaMin()));

		return infoAnalogique;
	}

}
