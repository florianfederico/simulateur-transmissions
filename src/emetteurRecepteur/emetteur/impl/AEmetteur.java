package emetteurRecepteur.emetteur.impl;

import destinations.DestinationInterface;
import emetteurRecepteur.form.src.AForm;
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
 *         AEmetteur est une classe abstraite qui detinit les types
 *         d'informations recus et envoyes par les emetteurs
 * 
 */
public abstract class AEmetteur extends Transmetteur<Boolean, Float> {

	/**
	 * Definit la forme du signal analogique
	 */
	private final AForm form;

	public AEmetteur(AForm form) {
		super();
		this.form = form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transmetteurs.Transmetteur#recevoir(information.Information)
	 */
	@Override
	public void recevoir(Information<Boolean> information) {
		this.informationRecue = information;
		this.informationEmise = this.CNA(this.informationRecue);
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
		return this.form.getSymboleParfait(etat);
	}
	
	/**
	 * Affiche un signal numerique aleatoire converti en signal analogique
	 * 
	 */
	public abstract void afficherSymboleAnalogiqueAleatoire();

	/**
	 * Convertisseur Numerique Analogique Convertit l'information recue affectée
	 * dans la methode recevoir
	 * 
	 * @param infoNumerique
	 *            L'information numerique a convertir
	 * @return L'information emise convertie
	 */
	private Information<Float> CNA(Information<Boolean> infoNumerique) {
		Information<Float> infoAnalogique = this.form.CNA(infoNumerique);
		return infoAnalogique;
	}

	public int getNe() {
		return this.form.getNe();
	}

	public float getaMin() {
		return this.form.getaMin();
	}

	public float getaMax() {
		return this.form.getaMax();
	}
}
