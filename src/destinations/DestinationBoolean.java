/**
 * 
 */
package destinations;

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
 *         Classe d'un composant destination d'informations dont les éléments
 *         sont de type Boolean.
 */
public class DestinationBoolean extends Destination<Boolean> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see destinations.Destination#recevoir(information.Information)
	 */
	@Override
	public void recevoir(Information<Boolean> information) {
		this.informationRecue = information;
	}

}
