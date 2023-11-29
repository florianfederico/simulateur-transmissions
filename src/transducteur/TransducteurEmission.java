package transducteur;

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
public class TransducteurEmission extends ATransducteur {

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#recevoir(information.Information)
	 */
	@Override
	public void recevoir(Information<Boolean> information) {
		this.informationRecue = information;
		this.informationEmise = this.codageCanal(informationRecue);
		this.emettre();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#emettre()
	 */
	@Override
	public void emettre() {
		for (DestinationInterface<Boolean> d : this.destinationsConnectees)
			d.recevoir(informationEmise);
	}

	/**
	 * Encode le signal
	 * 
	 * @param information
	 *            Le signal a encoder
	 * @return Le signal decode
	 */
	private Information<Boolean> codageCanal(Information<Boolean> information) {
		Information<Boolean> res = new Information<Boolean>();
		for (Boolean b : information)
			res.add(this.codageSequence(b));
		return res;
	}

	/**
	 * Codage le canal pour un bit
	 * 
	 * @param bit
	 *            Le bit
	 * @return La sequence de bits associees
	 */
	private Information<Boolean> codageSequence(boolean bit) {
		Information<Boolean> res = new Information<Boolean>();

		if (bit) {
			res.add(true);
			res.add(false);
			res.add(true);
		} else {
			res.add(false);
			res.add(true);
			res.add(false);
		}

		return res;
	}

}
