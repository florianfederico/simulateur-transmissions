/**
 * 
 */
package transmetteurs;

import destinations.DestinationInterface;
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
 *         Classe d'un composant transmetteur d'informations dont les éléments
 *         sont de type Boolean en entrée et de type Boolean en sortie. Cette
 *         classe hérite de la classe asbtraite Transmetteur.
 */
public class TransmetteurLogiqueParfait extends Transmetteur<Boolean, Boolean> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#recevoir(information.Information)
	 */
	@Override
	public void recevoir(Information<Boolean> information) {
		this.informationRecue = information;
		this.informationEmise = new Information<Boolean>();
		this.emettre();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#emettre()
	 */
	@Override
	public void emettre() {
		for (DestinationInterface<Boolean> d : this.destinationsConnectees) {
			d.recevoir(this.informationRecue);
		}
		this.informationEmise = this.informationRecue;
	}

}
