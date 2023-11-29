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
 *         Définit les methodes communes aux signaux de la forme NRZ.
 * 
 */
public class NRZ extends AForm {

	private final Information<Float> symboleParfaitHaut;

	private final Information<Float> symboleParfaitBas;

	public NRZ() {
		super();
		this.symboleParfaitBas = this.genererSymboleParfaitBas();
		this.symboleParfaitHaut = this.genererSymboleParfaitHaut();
	}

	public NRZ(int ne, float aMin, float aMax) {
		super(ne, aMin, aMax);
		this.symboleParfaitBas = this.genererSymboleParfaitBas();
		this.symboleParfaitHaut = this.genererSymboleParfaitHaut();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see emetteurRecepteur.form.src.AForm#CAN(information.Information)
	 */
	@Override
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

	@Override
	public Information<Float> getSymboleParfait(boolean bit) {
		if (bit)
			return this.symboleParfaitHaut;
		else
			return this.symboleParfaitBas;
	}

	/**
	 * Cet emetteur n'a pas d'exigences sur le nombre d'echantillons
	 * 
	 * @param ne
	 *            Le nombre d'échantillons
	 */
	@Override
	protected int corrigerNbEchantillons(int ne) {
		return ne;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see emetteurRecepteur.form.src.AForm#calculerPas(float, float, int, int)
	 */
	@Override
	protected float calculerPas() {
		return this.getaMax() - this.getaMin();
	}

	/**
	 * @return Un symbole parfait a l'etat haut
	 */
	private Information<Float> genererSymboleParfaitHaut() {
		Information<Float> infoAnalogique = new Information<Float>();
		for (int i = 0; i < this.getNe(); i++)
			infoAnalogique.add(new Float(this.getaMax()));
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