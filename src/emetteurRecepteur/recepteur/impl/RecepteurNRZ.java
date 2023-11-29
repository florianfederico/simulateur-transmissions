package emetteurRecepteur.recepteur.impl;

import emetteurRecepteur.recepteur.detecteur.impl.ADetecteur;

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
public class RecepteurNRZ extends ARecepteur {

	public RecepteurNRZ(ADetecteur detecteur) {
		super(detecteur);
	}
}
