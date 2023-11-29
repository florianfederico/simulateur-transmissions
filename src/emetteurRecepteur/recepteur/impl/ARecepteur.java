package emetteurRecepteur.recepteur.impl;

import destinations.DestinationInterface;
import emetteurRecepteur.recepteur.detecteur.impl.ADetecteur;
import information.Information;
import transmetteurs.Transmetteur;

/**
 * @author FEDERICO Florian - florian.federico@telecom-bretagne.eu
 * @author SANCHEZ Bastien - bastien.sanchez@telecom-bretagne.eu
 * @author MOULY Thibaud - thibaud.mouly@telecom-bretagne.eu
 * @author PONCELET Nathan - nathan.poncelet@telecom-bretagne.eu
 *
 *         TELECOM Bretagne - FIP 2018 - 2016-2017 Projet SIT213 - Atelier
 *         systèmes de transmission
 * 
 *         ARecepteur est une classe abstraite qui detinit les types
 *         d'informations recus et envoyes par les recepteurs
 */
public abstract class ARecepteur extends Transmetteur<Float, Boolean> {

	/* Definit la methode de detection du symbole */
	private final ADetecteur detecteur;

	public ARecepteur(ADetecteur detecteur) {
		this.detecteur = detecteur;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#recevoir(information.Information)
	 */
	@Override
	public void recevoir(Information<Float> information) {
		this.informationRecue = information;
		this.informationEmise = this.CAN(information);
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
			d.recevoir(this.informationEmise);
	}

	/**
	 * Renvoie le symbole parfait
	 * 
	 * @param etat
	 *            L'etat du symbole
	 * @return Le symbole
	 */
	public Information<Float> genererSymboleParfait(boolean etat) {
		return this.detecteur.getSymboleParfait(etat);
	}

	/**
	 * Convertisseur Analogique Numerique Convertit l'information analogique en
	 * numerique
	 * 
	 * @param infoAnalogique
	 *            L'information analogique
	 * @return L'information numerique
	 */
	private Information<Boolean> CAN(Information<Float> infoAnalogique) {

		Information<Boolean> infoNumerique = new Information<Boolean>();
		Information<Float> infoAnalogiqueTemp = new Information<Float>();

		int ne = this.getNe();
		int nbTours = infoAnalogique.nbElements() / ne;
		int i = 0;

		/**
		 * On analyse chaque ne echantillons pour les convoluer avec un signal
		 * parfait et detecter si un symbole bas ou haut est present
		 */
		for (int tour = 1; tour <= nbTours; tour++) {
			/**
			 * On recupere le paquet de ne echantillons a traiter
			 */
			infoAnalogiqueTemp = new Information<Float>();
			while (i < tour * ne) {
				infoAnalogiqueTemp.add(infoAnalogique.iemeElement(i));
				i++;
			}

			/**
			 * On detecte si un symbole haut est present
			 */
			if (this.detecteur.detecterSymbole(infoAnalogiqueTemp))
				infoNumerique.add(true);
			else
				infoNumerique.add(false);
		}

		return infoNumerique;
	}

	public int getNe() {
		return this.detecteur.getNe();
	}

	public float getaMin() {
		return this.detecteur.getaMin();
	}

	public float getaMax() {
		return this.detecteur.getaMax();
	}
}
