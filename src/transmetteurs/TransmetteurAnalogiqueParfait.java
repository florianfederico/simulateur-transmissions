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
 */
public class TransmetteurAnalogiqueParfait extends Transmetteur<Float, Float> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#recevoir(information.Information)
	 */
	@Override
	public void recevoir(Information<Float> information) {
		this.informationRecue = information;
		this.informationEmise = new Information<Float>();
		this.emettre();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#emettre()
	 */
	@Override
	public void emettre() {
		this.informationEmise = informationRecue;
		for (DestinationInterface<Float> d : this.destinationsConnectees)
			d.recevoir(informationEmise);

	}

}
