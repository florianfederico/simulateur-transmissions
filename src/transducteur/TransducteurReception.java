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
public class TransducteurReception extends ATransducteur {

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#recevoir(information.Information)
	 */
	@Override
	public void recevoir(Information<Boolean> information) {
		this.informationRecue = information;
		this.informationEmise = this.decodageCanal(informationRecue);
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
	 * Decode le signal recu
	 * 
	 * @param information
	 *            Le signal recu
	 * @return Le signal decode
	 */
	private Information<Boolean> decodageCanal(Information<Boolean> information) {
		Information<Boolean> res = new Information<Boolean>();
		Information<Boolean> sequenceCourante = new Information<Boolean>();

		final int tailleSequence = 3;
		final int NB_SEQUENCES = information.nbElements() / tailleSequence;
		int numSequence = 0;
		int indexElement = 0;

		/**
		 * On traite les bits 3 par 3 pour decoder le signal
		 */
		for (numSequence = 1; numSequence <= NB_SEQUENCES; numSequence++) {
			sequenceCourante = new Information<Boolean>();

			while (indexElement < numSequence * tailleSequence) {
				sequenceCourante.add(information.iemeElement(indexElement));
				indexElement++;
			}

			res.add(this.convertSequence(sequenceCourante));
		}

		return res;
	}

	/**
	 * Decodage de canal
	 * 
	 * @param sequence
	 *            La sequence de bits
	 * @return Le bit associe a la sequence
	 */
	private boolean convertSequence(Information<Boolean> sequence) {
		boolean res = false;

		boolean a = sequence.iemeElement(0);
		boolean b = sequence.iemeElement(1);
		boolean c = sequence.iemeElement(2);

		if (a == false && b == false && c == false)
			res = false;
		else if (a == false && b == false && c == true)
			res = true;
		else if (a == false && b == true && c == false)
			res = false;
		else if (a == false && b == true && c == true)
			res = false;
		else if (a == true && b == false && c == false)
			res = true;
		else if (a == true && b == false && c == true)
			res = true;
		else if (a == true && b == true && c == false)
			res = false;
		else if (a == true && b == true && c == true)
			res = true;

		return res;
	}
	
}
