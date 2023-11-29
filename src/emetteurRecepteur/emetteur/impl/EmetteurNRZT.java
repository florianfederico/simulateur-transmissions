package emetteurRecepteur.emetteur.impl;

import emetteurRecepteur.form.src.NRZT;
import sources.src.SourceAleatoire;
import visualisations.Sonde;
import visualisations.SondeAnalogique;

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
public class EmetteurNRZT extends AEmetteur {

	public EmetteurNRZT() {
		super(new NRZT());
	}

	/**
	 * @param ne
	 *            Le nombre d'echantillons par bit
	 * @param aMin
	 *            L'amplitude minimale
	 * @param aMax
	 *            L'amplitude maximale
	 */
	public EmetteurNRZT(int ne, float aMin, float aMax) {
		super(new NRZT(ne, aMin, aMax));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see emetteurRecepteur.emetteur.impl.AEmetteur#
	 * afficherSymboleAnalogiqueAleatoire()
	 */
	@Override
	public void afficherSymboleAnalogiqueAleatoire() {
		SourceAleatoire source = new SourceAleatoire(10);
		AEmetteur emetteur = new EmetteurNRZT();
		Sonde<Float> sondeEmetteur = new SondeAnalogique("sonde emetteur");

		source.connecter(emetteur);
		emetteur.connecter(sondeEmetteur);

		source.emettre();
	}

	public static void main(String[] args) {
		AEmetteur nrzt = new EmetteurNRZT();
		nrzt.afficherSymboleAnalogiqueAleatoire();

	}
}
