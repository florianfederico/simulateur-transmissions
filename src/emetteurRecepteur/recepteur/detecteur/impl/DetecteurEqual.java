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
public class DetecteurEqual extends ADetecteur {

	public DetecteurEqual(AForm form) {
		super(form);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * emetteurRecepteur.detecteur.impl.ADetecteur#detecterSymbole(information.
	 * Information)
	 */
	@Override
	public boolean detecterSymbole(Information<Float> signal) {
		return signal.equals(this.getSymboleParfait(true));
	}

}
