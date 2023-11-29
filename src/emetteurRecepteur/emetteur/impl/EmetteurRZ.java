package emetteurRecepteur.emetteur.impl;

import emetteurRecepteur.form.src.RZ;
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
public class EmetteurRZ extends AEmetteur {

	public EmetteurRZ() {
		super(new RZ());
	}

	/**
	 * @param ne
	 *            Le nombre d'echantillons par bit
	 * @param aMin
	 *            L'amplitude minimale
	 * @param aMax
	 *            L'amplitude maximale
	 */
	public EmetteurRZ(int ne, float aMin, float aMax) {
		super(new RZ(ne, aMin, aMax));
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
		AEmetteur emetteur = new EmetteurRZ();
		Sonde<Float> sondeEmetteur = new SondeAnalogique("sonde emetteur");

		source.connecter(emetteur);
		emetteur.connecter(sondeEmetteur);

		source.emettre();
	}

	public static void main(String[] args) {
		AEmetteur rz = new EmetteurRZ();
		rz.afficherSymboleAnalogiqueAleatoire();
	}
}
