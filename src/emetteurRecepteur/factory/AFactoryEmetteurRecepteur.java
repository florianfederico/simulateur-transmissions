package emetteurRecepteur.factory;

import emetteurRecepteur.emetteur.impl.AEmetteur;
import emetteurRecepteur.form.src.AForm;
import emetteurRecepteur.recepteur.detecteur.impl.ADetecteur;
import emetteurRecepteur.recepteur.detecteur.impl.DetecteurEqual;
import emetteurRecepteur.recepteur.detecteur.impl.DetecteurFiltreAdapte;
import emetteurRecepteur.recepteur.detecteur.impl.EnumDetecteur;
import emetteurRecepteur.recepteur.impl.ARecepteur;

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
public abstract class AFactoryEmetteurRecepteur {

	/**
	 * Instancie un AEmetteur
	 * 
	 * @return L'instance de AEmetteur
	 */
	public abstract AEmetteur creerEmetteur();

	/**
	 * Instancie un AEmetteur
	 * 
	 * @param ne
	 *            Le nombre d'echantillon par bit
	 * @param aMin
	 *            L'amplitude minimale
	 * @param aMax
	 *            L'amplitude maximale
	 * @return L'instance de AEmetteur
	 */
	public abstract AEmetteur creerEmetteur(int ne, float aMin, float aMax);

	/**
	 * @param detecteurType
	 *            Le detecteur souhaite
	 * @return L'instance de ARecepteur
	 */
	public abstract ARecepteur creerRecepteur(EnumDetecteur detecteurType);

	/**
	 * Instancie un ARecepteur
	 * 
	 * @param detecteurType
	 *            Le detecteur souhaite
	 * @param ne
	 *            Le nombre d'echantillon par bit
	 * @param aMin
	 *            L'amplitude minimale
	 * @param aMax
	 *            L'amplitude maximale
	 * @return L'instance de ARecepteur
	 */
	public abstract ARecepteur creerRecepteur(EnumDetecteur detecteurType, int ne, float aMin, float aMax);

	/**
	 * Creer un detecteur
	 * 
	 * @param detecteurType
	 *            Le type du detecteur
	 * @param form
	 *            La forme du signal a detecter
	 * @return Le detecteur
	 */
	protected ADetecteur creerDetecteur(EnumDetecteur detecteurType, AForm form) {
		ADetecteur detecteur = null;

		switch (detecteurType) {
		case EQUAL:
			detecteur = new DetecteurEqual(form);
			break;
		case FILTRE_ADAPTE:
			detecteur = new DetecteurFiltreAdapte(form);
			break;
		default:
			detecteur = new DetecteurEqual(form);
		}

		return detecteur;
	}
}
