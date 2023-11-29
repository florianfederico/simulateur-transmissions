package emetteurRecepteur.factory;

import emetteurRecepteur.emetteur.impl.AEmetteur;
import emetteurRecepteur.emetteur.impl.EmetteurNRZT;
import emetteurRecepteur.form.src.AForm;
import emetteurRecepteur.form.src.NRZT;
import emetteurRecepteur.recepteur.detecteur.impl.ADetecteur;
import emetteurRecepteur.recepteur.detecteur.impl.EnumDetecteur;
import emetteurRecepteur.recepteur.impl.ARecepteur;
import emetteurRecepteur.recepteur.impl.RecepteurNRZT;

/**
 * @author FEDERICO Florian - florian.federico@telecom-bretagne.eu
 * @author SANCHEZ Bastien - bastien.sanchez@telecom-bretagne.eu
 * @author MOULY Thibaud - thibaud.mouly@telecom-bretagne.eu
 * @author PONCELET Nathan - nathan.poncelet@telecom-bretagne.eu
 *
 *         TELECOM Bretagne - FIP 2018 - 2016-2017 Projet SIT213 - Atelier
 *         systèmes de transmission
 * 
 *         Cette classe implemente le pattern Factory visant a centraliser la
 *         creation des emetteurs et des recepteurs
 */
public class FactoryNRZT extends AFactoryEmetteurRecepteur {

	/* (non-Javadoc)
	 * @see emetteurRecepteur.factory.AFactoryEmetteurRecepteur#creerEmetteur()
	 */
	@Override
	public AEmetteur creerEmetteur() {
		return new EmetteurNRZT();
	}

	/* (non-Javadoc)
	 * @see emetteurRecepteur.factory.AFactoryEmetteurRecepteur#creerEmetteur(int, float, float)
	 */
	@Override
	public AEmetteur creerEmetteur(int ne, float aMin, float aMax) {
		return new EmetteurNRZT(ne, aMin, aMax);
	}

	/* (non-Javadoc)
	 * @see emetteurRecepteur.recepteur.factory.AFactoryRecepteur#creerRecepteur(emetteurRecepteur.recepteur.detecteur.impl.EnumDetecteur, int, float, float)
	 */
	@Override
	public ARecepteur creerRecepteur(EnumDetecteur detecteurType, int ne, float aMin, float aMax) {
		AForm form = new NRZT(ne, aMin, aMax);
		ADetecteur detecteur = this.creerDetecteur(detecteurType, form);
		return new RecepteurNRZT(detecteur);
	}

	/* (non-Javadoc)
	 * @see emetteurRecepteur.recepteur.factory.AFactoryRecepteur#creerRecepteur(emetteurRecepteur.recepteur.detecteur.impl.EnumDetecteur)
	 */
	@Override
	public ARecepteur creerRecepteur(EnumDetecteur detecteurType) {
		AForm form = new NRZT();
		ADetecteur detecteur = this.creerDetecteur(detecteurType, form);
		return new RecepteurNRZT(detecteur);
	}

}
