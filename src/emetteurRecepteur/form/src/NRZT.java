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
 *         Définit les methodes communes aux signaux de la forme NRZT.
 *
 */
public class NRZT extends AForm {

	private final Information<Float> tiersMontee;
	private final Information<Float> tiersHaut;
	private final Information<Float> tiersDescente;
	private final Information<Float> tiersBas;

	public NRZT() {
		super();
		this.tiersMontee = this.genererTierMontee();
		this.tiersHaut = this.genererTierHaut();
		this.tiersDescente = this.genererTiersDescente();
		this.tiersBas = this.genererTierBas();

	}

	public NRZT(int ne, float aMin, float aMax) {
		super(ne, aMin, aMax);
		this.tiersMontee = this.genererTierMontee();
		this.tiersHaut = this.genererTierHaut();
		this.tiersDescente = this.genererTiersDescente();
		this.tiersBas = this.genererTierBas();
	}

	@Override
	public Information<Float> CNA(Information<Boolean> infoNumerique) {
		Information<Float> info = new Information<Float>();
		Information<Float> premierTier = this.tiersMontee;

		for (int i = 0; i < infoNumerique.nbElements(); i++) {

			// Si le l'element est un 1
			if (infoNumerique.iemeElement(i) == true) {
				info.add(premierTier);
				info.add(this.tiersHaut);

				// On regarde s'il y a un prochain element et s'il est a 1
				if (i + 1 < infoNumerique.nbElements()) {
					if (infoNumerique.iemeElement(i + 1) == true) {
						info.add(this.tiersHaut);
						premierTier = this.tiersHaut;
					} else {
						info.add(this.tiersDescente);
						premierTier = this.tiersMontee;
					}
				} else
					info.add(this.tiersDescente);
			} else {
				info.add(this.tiersBas);
				info.add(this.tiersBas);
				info.add(this.tiersBas);
			}
		}

		return info;
	}

	@Override
	public Information<Float> getSymboleParfait(boolean bit) {
		Information<Float> info = new Information<Float>();
		if (bit) {
			info.add(this.tiersMontee);
			info.add(this.tiersHaut);
			info.add(this.tiersDescente);
		} else {
			info.add(this.tiersBas);
			info.add(this.tiersBas);
			info.add(this.tiersBas);
		}

		return info;
	}

	/**
	 * Incremente le nombre d'echantillons tant qu'il n'est pas divisible par 3
	 * 
	 * @param ne
	 *            Le nombre d'échantillons
	 */
	@Override
	protected int corrigerNbEchantillons(int ne) {
		int resultat = ne;
		while (resultat % 3 != 0)
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
		return (this.getaMax() - this.getaMin()) / (this.getNe() / 3);
	}

	private Information<Float> genererTierMontee() {
		Information<Float> info = new Information<Float>();
		float aCourant = this.getaMin();
		float pas = this.calculerPas();
		/**
		 * Temps de montée
		 */

		for (int i = 0; i < this.getNe() / 3; i++) {
			info.add(aCourant);
			aCourant += pas;
		}

		return info;
	}

	private Information<Float> genererTierHaut() {
		Information<Float> info = new Information<Float>();

		for (int i = 0; i < this.getNe() / 3; i++)
			info.add(this.getaMax());

		return info;
	}

	private Information<Float> genererTiersDescente() {
		Information<Float> info = new Information<Float>();
		float aCourant = this.getaMax();
		float pas = this.calculerPas();
		/**
		 * Temps de descente
		 */
		for (int i = 0; i < this.getNe() / 3 - 1; i++) {
			aCourant -= pas;
			info.add(aCourant);
		}
		info.add(this.getaMin());

		return info;
	}

	private Information<Float> genererTierBas() {
		Information<Float> info = new Information<Float>();

		for (int i = 0; i < this.getNe() / 3; i++)
			info.add(this.getaMin());

		return info;
	}

}
