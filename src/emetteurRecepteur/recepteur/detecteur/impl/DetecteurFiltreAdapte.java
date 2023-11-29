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
public class DetecteurFiltreAdapte extends ADetecteur {

	private final float convBas;
	private final float convHaut;
	private final float convHautBas;

	public DetecteurFiltreAdapte(AForm form) {
		super(form);

		this.convHaut = this.convolutionMax(this.getSymboleParfait(true), this.getSymboleParfait(true));
		this.convBas = this.convolutionMax(this.getSymboleParfait(false), this.getSymboleParfait(false));
		this.convHautBas = this.convolutionMax(this.getSymboleParfait(true), this.getSymboleParfait(false));
	}

	/**
	 * 
	 * Effectue une operation de convolution entre les signaux passés en
	 * parametres. Les deux signaux doivent avoir la meme taille, renvoie 0
	 * sinon.
	 * 
	 * @param sig1
	 *            Le signal 1
	 * @param sig2
	 *            Le signal 2
	 * @return La valeur maximale de la convolution.
	 */
	private float convolutionMax(Information<Float> sig1, Information<Float> sig2) {
		float sum = 0f;
		if (sig1.nbElements() != sig2.nbElements())
			return 0f;

		for (int i = 0; i < sig1.nbElements(); i++) {
			sum += sig1.iemeElement(i) * sig2.iemeElement(i);
		}
		return sum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * emetteurRecepteur.recepteur.detecteur.impl.ADetecteur#detecterSymbole(
	 * information.Information)
	 */
	@Override
	public boolean detecterSymbole(Information<Float> signal) {

		final float diffConv;
		final float diffConvHautBas;
		final float convSignal;
		boolean trouve = false;

		/**
		 * Si aMax est != de 0, on recupere la valeur maximale de la convolution
		 * avec le symbole parfait haut.
		 */
		if (this.getaMax() != 0) {
			trouve = false;
			convSignal = this.convolutionMax(signal, this.getSymboleParfait(true));

			/**
			 * On mesure l'ecart entre convSignal et la convolution max d'un
			 * symbole haut avec haut entre convSignal et la convolution max
			 * d'un symbole haut avec bas
			 */
			diffConv = Math.abs(convHaut - convSignal);
			diffConvHautBas = Math.abs(convHautBas - convSignal);

			/**
			 * Si convSignal est plus proche de la convolution max d'un symbole
			 * haut avec haut Alors le signal represente un symbole haut
			 */
			if (diffConv < diffConvHautBas)
				trouve = true;
		} else {
			/**
			 * Si aMax est == 0, on suit la meme demarche mais avec le symbole
			 * parfait bas.
			 */
			trouve = true;
			convSignal = this.convolutionMax(signal, this.getSymboleParfait(false));

			diffConv = Math.abs(convBas - convSignal);
			diffConvHautBas = Math.abs(convHautBas - convSignal);

			if (diffConv < diffConvHautBas)
				trouve = false;
		}

		return trouve;
	}

}
