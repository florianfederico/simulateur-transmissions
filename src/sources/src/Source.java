package sources.src;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;

/**
 * Classe Abstraite d'un composant source d'informations dont les éléments sont
 * de type T
 * 
 * @author prou
 */
public abstract class Source<T> implements SourceInterface<T> {

	/**
	 * la liste des composants destination connectés
	 */
	protected LinkedList<DestinationInterface<T>> destinationsConnectees;

	/**
	 * l'information générée par la source
	 */
	protected Information<T> informationGeneree;

	/**
	 * l'information émise par la source
	 */
	protected Information<T> informationEmise;

	/**
	 * un constructeur factorisant les initialisations communes aux réalisations
	 * de la classe abstraite Source
	 */
	public Source() {
		destinationsConnectees = new LinkedList<DestinationInterface<T>>();
		informationGeneree = null;
		informationEmise = null;
	}

	/**
	 * retourne la dernière information generee par la source
	 * 
	 * @return une information
	 */
	public Information<T> getInformationGeneree() {
		return this.informationGeneree;
	}
	
	/**
	 * retourne la dernière information émise par la source
	 * 
	 * @return une information
	 */
	public Information<T> getInformationEmise() {
		return this.informationEmise;
	}

	/**
	 * connecte une destination à la source
	 * 
	 * @param destination
	 *            la destination à connecter
	 */
	public void connecter(DestinationInterface<T> destination) {
		destinationsConnectees.add(destination);
	}

	/**
	 * déconnecte une destination de la source
	 * 
	 * @param destination
	 *            la destination à déconnecter
	 */
	public void deconnecter(DestinationInterface<T> destination) {
		destinationsConnectees.remove(destination);
	}

	/**
	 * émet l'information générée
	 */
	public void emettre() {
		// émission vers les composants connectés
		this.informationEmise = informationGeneree;
		for (DestinationInterface<T> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
	}
}
