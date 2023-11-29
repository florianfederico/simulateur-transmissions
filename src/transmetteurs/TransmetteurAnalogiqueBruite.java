package transmetteurs;

import java.util.ArrayList;

import bruit.ABruit;
import destinations.DestinationInterface;
import information.Information;

public class TransmetteurAnalogiqueBruite extends Transmetteur<Float, Float> {

	private ArrayList<ABruit> bruits;

	public TransmetteurAnalogiqueBruite(ArrayList<ABruit> bruits) {
		super();
		this.bruits = new ArrayList<ABruit>();
		this.bruits = (bruits);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#recevoir(information.Information)
	 */
	@Override
	public void recevoir(Information<Float> information) {
		this.informationRecue = information;
		this.informationEmise = information;
		for (ABruit b : this.bruits) {
			this.informationEmise = Information.sum(this.informationEmise, b.generer(informationRecue));
		}
		this.emettre();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#emettre()
	 */
	@Override
	public void emettre() {

		for (DestinationInterface<Float> d : this.destinationsConnectees)
			d.recevoir(informationEmise);
	}

}
